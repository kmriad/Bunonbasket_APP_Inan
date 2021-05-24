package com.example.bunonbasket.ui.component.details

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.product.ProductDetails
import com.example.bunonbasket.databinding.ActivityProductDetailsBinding
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailsBinding
    private val args: ProductDetailsActivityArgs by navArgs()
    private val viewModel: ProductDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            viewModel.fetchProductDetails(ProductDetailsEvent.FetchProductDetails(args.product.id.toString()))
            subscribeObservers()
        }
    }

    private fun subscribeObservers() {
        viewModel.productDataState.observe(this, { dataState ->
            when (dataState) {
                is Resource.Success<BaseDetailsModel<ProductDetails>> -> {
                    dataState.data.let { productModel ->
                        binding.data = productModel.results
                    }
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