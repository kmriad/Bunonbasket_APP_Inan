package com.example.bunonbasket.ui.component.details

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.cart.CartModel
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.data.models.product.ProductDetails
import com.example.bunonbasket.databinding.ActivityProductDetailsBinding
import com.example.bunonbasket.ui.component.details.adapters.ChoiceOptionsAdapter
import com.example.bunonbasket.ui.component.details.adapters.ColorsAdapter
import com.example.bunonbasket.ui.component.details.adapters.ProductImageViewPagerAdapter
import com.example.bunonbasket.utils.Constants.PRODUCT_DETAILS
import com.example.bunonbasket.utils.Resource
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailsBinding
    private val args: ProductDetailsActivityArgs by navArgs()
    private val viewModel: ProductDetailsViewModel by viewModels()
    lateinit var viewPagerAdapter: ProductImageViewPagerAdapter
    lateinit var choiceOptionsAdapter: ChoiceOptionsAdapter
    lateinit var colorAdapter: ColorsAdapter
    private var isSidePanelShown: Boolean = false
    lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BunonBasket)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {

            binding.model = viewModel
            binding.lifecycleOwner = this@ProductDetailsActivity

            if (intent.getSerializableExtra(PRODUCT_DETAILS) != null) {
                product = intent.getSerializableExtra(PRODUCT_DETAILS) as Product
                viewModel.fetchProductDetails(ProductDetailsEvent.FetchProductDetails(product.id.toString()))
            } else {
                product = args.product
                viewModel.fetchProductDetails(ProductDetailsEvent.FetchProductDetails(product.id.toString()))
            }
            viewPagerAdapter = ProductImageViewPagerAdapter()
            choiceOptionsAdapter = ChoiceOptionsAdapter()
            colorAdapter = ColorsAdapter()
            binding.imagePager.apply {
                adapter = viewPagerAdapter
            }
            TabLayoutMediator(binding.indicatorTab, binding.imagePager) { tab, position ->
            }.attach()

            binding.choiceOptions.apply {
                adapter = choiceOptionsAdapter
                layoutManager = LinearLayoutManager(
                    this@ProductDetailsActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }

            binding.colorsRecyclerView.apply {
                adapter = colorAdapter
                layoutManager = LinearLayoutManager(
                    this@ProductDetailsActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
            binding.openDescriptionBtn.setOnClickListener {
                toggle(isSidePanelShown)
            }

            subscribeObservers()

            binding.addToCartBtn.setOnClickListener {
                if (viewModel._counter.value == 0) {
                    Toast.makeText(
                        this@ProductDetailsActivity,
                        "One product must be added atleast",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    viewModel.fetchToken()
                }
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.productDataState.observe(this, { dataState ->
            when (dataState) {
                is Resource.Success<BaseDetailsModel<ProductDetails>> -> {
                    dataState.data.let { productModel ->
                        binding.data = productModel.results
                        viewPagerAdapter.submitList(productModel.results.photos)
                        if (productModel.results.choice_options.size > 0) {
                            choiceOptionsAdapter.submitList(productModel.results.choice_options)
                        }
                        if (productModel.results.colors.size > 0) {
                            binding.colorSection.visibility = View.VISIBLE
                            colorAdapter.submitList(productModel.results.colors)
                        } else {
                            binding.colorSection.visibility = View.GONE
                        }
                    }
                }
                is Resource.Error -> {
                    dataState.exception.let { message ->
                        Toast.makeText(
                            this,
                            "An error occured product: ${message.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })

        viewModel.cartDataState.observe(this, { dataState ->
            when (dataState) {
                is Resource.Success<BaseDetailsModel<CartModel>> -> {
                    Toast.makeText(this, "Product Added Successfully", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    dataState.exception.let { message ->
                        Toast.makeText(
                            this,
                            "An error occured: ${message.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })

        viewModel.token.observe(this, { dataState ->
            when (dataState) {
                is String -> {
                    if (dataState.isNotEmpty()) {
                        viewModel.addToCart(product.id.toString())
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

    private fun toggle(show: Boolean) {
        val transition: Transition = Slide(Gravity.BOTTOM)
        transition.duration = 600
        transition.addTarget(binding.descriptionBody)
        TransitionManager.beginDelayedTransition(binding.descriptionBody, transition)
        binding.descriptionBody.visibility = if (!show) View.VISIBLE else View.GONE
        if (!isSidePanelShown) {
            binding.priceSection.visibility = View.GONE
            binding.quantitySection.visibility = View.GONE
        } else {
            binding.priceSection.visibility = View.VISIBLE
            binding.quantitySection.visibility = View.VISIBLE
        }
        binding.descriptionBody.bringToFront()
        isSidePanelShown = !isSidePanelShown
    }
}