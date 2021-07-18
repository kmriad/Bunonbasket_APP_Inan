package com.example.bunonbasket.ui.component.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.bunonbasket.R
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.models.LoginModel
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.databinding.ActivityLoginBinding
import com.example.bunonbasket.ui.component.HomeActivity
import com.example.bunonbasket.ui.component.signup.SignUpActivity
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    lateinit var dataStore: DataStoreManager

    lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BunonBasket)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        binding.lifecycleOwner = this

        dataStore = DataStoreManager(this)

        viewModel.mPhonePasswordValidator.observe(this, { validator ->
            binding.loginButton.isEnabled = validator
        })

        binding.apply {
            binding.data = viewModel
            subscribeObservers()

            binding.signUpButton.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.loginState.observe(this@LoginActivity, { dataState ->
            when (dataState) {
                is Resource.Success<BaseDetailsModel<LoginModel>> -> {
                    dataState.data.let { loginModel ->
                        viewModel.saveUserProfile(loginModel.results)
                        lifecycleScope.launch {
                            dataStore.saveAuthToken(loginModel.results.token)
                        }
                        Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }
                }
                is Resource.Error -> {
                    dataState.exception.let { e ->
                        Log.d("LoginActivity", e.message.toString())
                    }
                }
            }
        })

        viewModel.tokenState.observe(this@LoginActivity, { dataState ->
            when (dataState) {
                is Resource.Success<String> -> {
                    dataState.data.let { data ->
                        Log.d("LoginActivity", data)
                        viewModel.saveToken(data)
                    }
                }
                is Resource.Error -> {
                    dataState.exception.let { e ->
                        Log.d("LoginActivity", e.message.toString())
                    }
                }
            }
        })

        viewModel.saveUserState.observe(this, { dataState ->
            when (dataState) {
                is Resource.Success<Long> -> {
                    val parentIntent = NavUtils.getParentActivityIntent(this)
                    parentIntent!!.flags =
                        Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or
                                Intent.FLAG_ACTIVITY_SINGLE_TOP or
                                Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(parentIntent)
                    finish()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val parentIntent = NavUtils.getParentActivityIntent(this)
                parentIntent!!.flags =
                    Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP or
                            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(parentIntent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}