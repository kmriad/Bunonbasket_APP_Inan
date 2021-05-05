package com.example.bunonbasket.ui.component

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bunonbasket.R
import com.example.bunonbasket.databinding.ActivityHomeBinding
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BunonBasket)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
       // setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        var childFragment = supportFragmentManager.findFragmentByTag("fragment_sheet_home")

        if (childFragment != null) {
            binding.bottomNavigationView.setupWithNavController(childFragment.findNavController())
        }
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
                            .setTarget(binding.btnCamera)
                            .setDismissText("Okay, Got It")
                            .setContentText("Upload your shopping list through camera using this button")
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