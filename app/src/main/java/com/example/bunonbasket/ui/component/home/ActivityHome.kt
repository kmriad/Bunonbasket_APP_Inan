package com.example.bunonbasket.ui.component

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bunonbasket.R
import com.example.bunonbasket.ui.component.home.HomeStateEvent
import com.example.bunonbasket.ui.component.home.HomeViewModel
import com.example.bunonbasket.utils.Constants.Companion.SHOWCASE_ID
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView

@AndroidEntryPoint
class ActivityHome : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BunonBasket)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        subscribeObservers()

        viewModel.setStateEvent(HomeStateEvent.LoadShowCase)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_app_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun subscribeObservers() {
        viewModel.loadDataState.observe(this, Observer { dataState ->
            when (dataState) {
                is Resource.Success<Boolean> -> {
                    if (dataState.data == false) {
                        MaterialShowcaseView.Builder(this)
                            .setTarget(btnCamera)
                            .setDismissText("Okay, Got It")
                            .setContentText("Upload your shopping list through camera using this button")
                            .singleUse(SHOWCASE_ID)
                            .setDelay(500)
                            .show()

                        viewModel.setStateEvent(HomeStateEvent.SaveShowCase)
                    }

                }
                is Resource.DataError -> {

                }
                is Resource.Loading -> {

                }
            }
        })
    }

}