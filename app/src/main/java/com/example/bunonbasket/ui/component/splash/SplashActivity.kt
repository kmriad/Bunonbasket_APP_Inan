package com.example.bunonbasket.ui.component.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bunonbasket.R
import com.example.bunonbasket.ui.component.AppIntroActivity

class SplashActivity : AppCompatActivity() {

    companion object {
        const val TIME_OUT = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        supportActionBar?.hide();
        setContentView(R.layout.activity_splash)
        goToBoardingScreen()
    }

    private fun goToBoardingScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            // Your Code
            val intent = Intent(this, AppIntroActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME_OUT)
    }
}
