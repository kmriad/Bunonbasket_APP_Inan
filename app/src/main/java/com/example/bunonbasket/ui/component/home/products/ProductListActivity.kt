package com.example.bunonbasket.ui.component.home.products

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bunonbasket.R
import com.example.bunonbasket.databinding.ActivityProductListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductListActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductListBinding
    lateinit var productAdapter: PaginatedProductListAdapter
    private val viewModel: ProductsViewModel by viewModels()
    private val args: ProductListActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BunonBasket)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            productAdapter = PaginatedProductListAdapter()
            binding.productListGridView.apply {
                adapter = productAdapter
                layoutManager = GridLayoutManager(
                    this@ProductListActivity,
                    2,
                    GridLayoutManager.VERTICAL,
                    false
                )
            }

            viewModel.fetchEvent(args.subCategory.id.toString())
            viewModel.productLiveData.observe(this@ProductListActivity, { dataState ->
                lifecycleScope.launch {
                    Log.d("ProductListActivity",dataState.toString())
                    productAdapter.submitData(dataState)
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val parentIntent = NavUtils.getParentActivityIntent(this)
                parentIntent!!.flags =
                    Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(parentIntent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}