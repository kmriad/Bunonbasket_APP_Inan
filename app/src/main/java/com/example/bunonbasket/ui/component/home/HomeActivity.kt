package com.example.bunonbasket.ui.component

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bunonbasket.R
import com.example.bunonbasket.databinding.ActivityHomeBinding
import com.example.bunonbasket.ui.component.bazar.UploadBazarActivity
import com.example.bunonbasket.ui.component.home.HomeStateEvent
import com.example.bunonbasket.ui.component.home.HomeViewModel
import com.example.bunonbasket.utils.Constants.SHOWCASE_ID
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BunonBasket)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        //setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        var childFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        val navHostController =
            childFragment as NavHostFragment
        navController = navHostController.findNavController()
        binding.toolbar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.accountFragment -> {
                    supportActionBar?.hide()
                    binding.toolbar.visibility = View.GONE
                }
                R.id.cartFragment -> {
                    supportActionBar?.hide()
                    binding.toolbar.visibility = View.GONE
                }
                else -> {
                    supportActionBar?.show()
                    binding.toolbar.visibility = View.VISIBLE
                }
            }
        }

        binding.bottomNavigationView.setupWithNavController(navController)
        subscribeObservers()

        binding.btnCamera.setOnClickListener {
            val intent = Intent(this, UploadBazarActivity::class.java)
            startActivity(intent)
        }

        viewModel.setStateEvent(HomeStateEvent.LoadShowCase)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_app_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun subscribeObservers() {
        viewModel.loadDataState.observe(this, Observer { dataState ->
            when (dataState) {
                is Resource.Success<Boolean> -> {
                    if (dataState.data == false) {
                        MaterialShowcaseView.Builder(this)
                            .setTarget(binding.btnCamera)
                            .setDismissText(getString(R.string.got_it))
                            .setContentText(getString(R.string.upload_image_using_button))
                            .singleUse(SHOWCASE_ID)
                            .setDelay(500)
                            .show()

                        viewModel.setStateEvent(HomeStateEvent.SaveShowCase)
                    }

                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        })
    }

}