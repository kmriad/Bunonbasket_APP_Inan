package com.example.bunonbasket.ui.component

import android.os.Bundle
import android.view.Menu
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
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        var childFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        val navHostController =
            childFragment as NavHostFragment
        navController = navHostController.findNavController()
        // setupActionBarWithNavController(navController)


        if (childFragment != null) {
            binding.bottomNavigationView.setupWithNavController(navController)

        }
        subscribeObservers()

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