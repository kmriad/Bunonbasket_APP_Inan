package com.example.bunonbasket.ui.component.home.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.banner.Banner
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.cart.CartListModel
import com.example.bunonbasket.data.models.category.Category
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.data.models.partners.PartnerModel
import com.example.bunonbasket.databinding.FragmentHomeBinding
import com.example.bunonbasket.ui.component.home.HomeStateEvent
import com.example.bunonbasket.ui.component.home.HomeViewModel
import com.example.bunonbasket.ui.component.home.adapters.BannerAdapter
import com.example.bunonbasket.ui.component.home.adapters.BrandAdapter
import com.example.bunonbasket.ui.component.home.adapters.CategoryAdapter
import com.example.bunonbasket.ui.component.home.adapters.ProductAdapter
import com.example.bunonbasket.utils.Resource
import com.example.bunonbasket.utils.widgets.PageIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), CategoryAdapter.OnItemClickListener,
    ProductAdapter.OnItemClickListener {


    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var bannerAdapter: BannerAdapter
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var bestSellingProductAdapter: ProductAdapter
    lateinit var featuredProductAdapter: ProductAdapter
    lateinit var brandAdapter: BrandAdapter
    lateinit var binding: FragmentHomeBinding
    lateinit var progressDialog: Dialog

    val TAG = "HomeFragment"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            progressDialog = Dialog(requireContext())
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialog.setContentView(R.layout.custom_dialog_progress)
            val progressTv = progressDialog.findViewById(R.id.progress_tv) as TextView
            progressTv.text = resources.getString(R.string.loading)
            progressTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            progressTv.textSize = 16F
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog.setCancelable(false)
            progressDialog.show()

            bannerAdapter = BannerAdapter()
            brandAdapter = BrandAdapter()
            binding.bannerViewPager.apply {
                adapter = bannerAdapter
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            }
            categoryAdapter = CategoryAdapter(this@HomeFragment)
            binding.categoryGridView.apply {
                adapter = categoryAdapter
                layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
            }
            binding.categoryGridView.addItemDecoration(PageIndicator())
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(binding.categoryGridView)


            binding.brandListView.apply {
                adapter = brandAdapter
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            }

            featuredProductAdapter = ProductAdapter(this@HomeFragment)
            binding.featuredRecyclerView.apply {
                adapter = featuredProductAdapter
                layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
            }
            bestSellingProductAdapter = ProductAdapter(this@HomeFragment)
            binding.bestSellingRecyclerView.apply {
                adapter = bestSellingProductAdapter
                layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
            }
        }


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            homeViewModel.homeEvent.collect { event ->
                when (event) {
                    is HomeStateEvent.NavigateToCategory -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToCategoryFragment(
                            event.category
                        )
                        findNavController().navigate(action)
                    }

                    is HomeStateEvent.NavigateToProductDetails -> {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToProductDetailsActivity2(
                                event.product
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        }

        fetchRemoteEvents()
        subscribeObservers()
    }

    private fun fetchRemoteEvents() {
        homeViewModel.setStateEvent(HomeStateEvent.LoadToken)
        homeViewModel.fetchRemoteEvents(HomeStateEvent.FetchBanners)
        homeViewModel.fetchRemoteEvents(HomeStateEvent.FetchCategories)
        homeViewModel.fetchRemoteEvents(HomeStateEvent.FetchBrands)
        homeViewModel.fetchRemoteEvents(HomeStateEvent.FetchFeaturedProducts)
        homeViewModel.fetchRemoteEvents(HomeStateEvent.FetchBestSellingProducts)
    }

    private fun subscribeObservers() {

        homeViewModel.token.observe(viewLifecycleOwner, { token ->
            if (token == "") {
                Log.d("HomeActivityResult", "Token")
            } else {
                Log.d("HomeActivityResult", token)
                homeViewModel.fetchCarts()
            }
        })

        homeViewModel.cartState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<BaseModel<CartListModel>> -> {
                    dataState.data.let { data ->
                        val number = data.results.size
                        Log.d("HomeActivity", number.toString())
                        if (number > 0) {
                            homeViewModel.saveCounter(number)
                        }
                    }
                }
            }
        })

        homeViewModel.bannerState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is Resource.Success<BaseModel<Banner>> -> {
                    dataState.data.let { bannerModel ->
                        bannerAdapter.differ.submitList(bannerModel.results)
                    }
                }
                is Resource.Error -> {
                    dataState.exception.let { message ->
                        Toast.makeText(
                            activity,
                            "An error occured banner: ${message.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                is Resource.Loading -> {

                }
            }
        })
        homeViewModel.categoryState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is Resource.Success<BaseModel<Category>> -> {
                    dataState.data.let { categoryModel ->
                        progressDialog.dismiss()
                        categoryAdapter.submitList(categoryModel.results)
                    }
                }
                is Resource.Error -> {
                    dataState.exception.let { message ->
                        Toast.makeText(
                            activity,
                            "An error occured category: ${message.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                is Resource.Loading -> {

                }
            }
        })
        homeViewModel.brandState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is Resource.Success<BaseModel<PartnerModel>> -> {
                    dataState.data.let { brandModel ->
                        brandAdapter.submitList(brandModel.results)
                    }
                }
                is Resource.Error -> {
                    dataState.exception.let { message ->
                        Toast.makeText(
                            activity,
                            "An error occured brand: ${message.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                is Resource.Loading -> {

                }
            }
        })
        homeViewModel.featuredProductState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is Resource.Success<BaseModel<Product>> -> {
                    dataState.data.let { productModel ->
                        if (productModel.results.isNotEmpty()) binding.featuredProductsTitle.visibility =
                            View.VISIBLE
                        featuredProductAdapter.submitList(productModel.results)
                    }
                }
                is Resource.Error -> {
                    dataState.exception.let { message ->
                        Toast.makeText(
                            activity,
                            "An error occured featured: ${message.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                is Resource.Loading -> {

                }
            }
        })

        homeViewModel.bestSellingProductState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is Resource.Success<BaseModel<Product>> -> {
                    dataState.data.let { productModel ->
                        if (productModel.results.isNotEmpty()) binding.bestSellingProductsTitle.visibility =
                            View.VISIBLE
                        bestSellingProductAdapter.submitList(productModel.results)
                    }
                }
                is Resource.Error -> {
                    dataState.exception.let { message ->
                        Toast.makeText(
                            activity,
                            "An error occured best selling: ${message.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                is Resource.Loading -> {

                }
            }
        })
    }

    override fun onItemClick(category: Category) {
        homeViewModel.onCategoryClicked(category)
    }

    override fun onItemClick(product: Product?) {
        homeViewModel.onProductClicked(product!!)
    }

}