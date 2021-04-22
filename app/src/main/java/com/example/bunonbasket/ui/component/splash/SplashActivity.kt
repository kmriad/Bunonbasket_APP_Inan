package com.example.bunonbasket.ui.component.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bunonbasket.databinding.ActivitySplashBinding
import com.example.bunonbasket.ui.component.AppIntroActivity
import com.example.bunonbasket.ui.component.HomeActivity
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object {
        const val TIME_OUT = 3000L
    }

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        supportActionBar?.hide();
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.setStateEvent(SplashStateEvent.LoadAppIntro)
        subscribeObservers()

    }

    private fun goToBoardingScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AppIntroActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME_OUT)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is Resource.Success<Boolean> -> {
                    Log.d("AppDebug", dataState.data.toString());
                    if (dataState.data == true) {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        goToBoardingScreen()
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
