package com.example.bunonbasket.ui.component.home.products

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bunonbasket.R
import com.example.bunonbasket.databinding.ActivityProductListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
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

                    // getting the error
                    val errorState = when {
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.refresh is LoadState.Error -> {
                            binding.btnRetry.visibility = View.VISIBLE
                            loadState.refresh as LoadState.Error
                        }
                        else -> null
                    }
                    errorState?.let {
                        Toast.makeText(
                            this@ProductListActivity,
                            it.error.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
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