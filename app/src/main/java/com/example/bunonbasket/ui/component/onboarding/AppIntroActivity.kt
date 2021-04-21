package com.example.bunonbasket.ui.component

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.bunonbasket.R
import com.example.bunonbasket.ui.component.onboarding.AppIntroStateEvent
import com.example.bunonbasket.ui.component.onboarding.AppIntroViewModel
import com.example.bunonbasket.utils.Resource
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AppIntroActivity : AppIntro() {

    private val viewModel: AppIntroViewModel by viewModels()

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


        subscribeObservers()

    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is Resource.Success<Boolean> -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is Resource.DataError -> {

                }
                is Resource.Loading -> {

                }
            }
        })
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        viewModel.setStateEvent(AppIntroStateEvent.SaveIntro)
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        viewModel.setStateEvent(AppIntroStateEvent.SaveIntro)

    }
}