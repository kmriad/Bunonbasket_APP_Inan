package com.example.bunonbasket

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment


class AppIntroActivity : AppIntro() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide();
        // Make sure you don't call setContentView!

        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
        addSlide(
            AppIntroFragment.newInstance(
                backgroundDrawable = R.drawable.ic_on_boarding_1
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                backgroundDrawable = R.drawable.ic_on_boarding_2
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                backgroundDrawable = R.drawable.ic_on_boarding_3
            )
        )
        addSlide(
            AppIntroFragment.newInstance(
                backgroundDrawable = R.drawable.ic_on_boarding_4
            )
        )
        // Toggle Indicator Visibility
        isIndicatorEnabled = true

        // Change Indicator Color
        setIndicatorColor(
            selectedIndicatorColor = getColor(R.color.orange),
            unselectedIndicatorColor = getColor(R.color.gray)
        )
        // Control the status bar color
        setColorDoneText(R.color.black)
        setDoneText("SHOP NOW")
        setColorSkipButton(R.color.black)
        showSeparator(false)
        getDrawable(R.drawable.ic_next)?.let { setImageNextButton(it) }
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        val intent = Intent(this,ActivityHome::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        val intent = Intent(this,ActivityHome::class.java)
        startActivity(intent)
        finish()
    }
}