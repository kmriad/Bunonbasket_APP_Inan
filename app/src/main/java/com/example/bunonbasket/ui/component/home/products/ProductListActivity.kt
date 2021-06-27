package com.example.bunonbasket.ui.component.home.products

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.databinding.ActivityProductListBinding
import com.example.bunonbasket.ui.component.details.ProductDetailsActivity
import com.example.bunonbasket.ui.component.home.products.adapters.PaginatedProductListAdapter
import com.example.bunonbasket.ui.component.home.products.adapters.ProductLoadStateAdapter
import com.example.bunonbasket.utils.Constants.PRODUCT_DETAILS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductListActivity : AppCompatActivity(), PaginatedProductListAdapter.OnItemClickListener {
    lateinit var binding: ActivityProductListBinding
    private lateinit var productAdapter: PaginatedProductListAdapter
    private val viewModel: ProductsViewModel by viewModels()
    private val args: ProductListActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BunonBasket)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            productAdapter = PaginatedProductListAdapter(this@ProductListActivity)
            binding.productListGridView.apply {
                adapter = productAdapter
                layoutManager = GridLayoutManager(
                    this@ProductListActivity,
                    2,
                    GridLayoutManager.VERTICAL,
                    false
                )
                adapter = productAdapter.withLoadStateFooter(
                    footer = ProductLoadStateAdapter({ productAdapter.retry() })
                )
            }

            binding.btnRetry.setOnClickListener {
                productAdapter.retry()
            }

            viewModel.fetchEvent(args.subCategory.id.toString())

            lifecycleScope.launch {
                viewModel.products.collectLatest {
                    productAdapter.submitData(it)
                }
            }
            productAdapter.addLoadStateListener { loadState ->

                if (loadState.refresh is LoadState.Loading) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnRetry.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
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

    override fun onItemClick(product: Product) {
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra(PRODUCT_DETAILS, product)
        startActivity(intent)
    }
}