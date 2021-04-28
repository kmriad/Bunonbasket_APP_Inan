package com.example.bunonbasket.ui.component.home.fragments

import PageIndicator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.bunonbasket.data.models.banner.BannerModel
import com.example.bunonbasket.data.models.brands.BrandModel
import com.example.bunonbasket.data.models.category.Category
import com.example.bunonbasket.data.models.category.CategoryModel
import com.example.bunonbasket.databinding.FragmentHomeBinding
import com.example.bunonbasket.ui.component.home.HomeStateEvent
import com.example.bunonbasket.ui.component.home.HomeViewModel
import com.example.bunonbasket.ui.component.home.adapters.BannerAdapter
import com.example.bunonbasket.ui.component.home.adapters.BrandAdapter
import com.example.bunonbasket.ui.component.home.adapters.CategoryAdapter
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), CategoryAdapter.OnItemClickListener {


    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var bannerAdapter: BannerAdapter
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var brandAdapter: BrandAdapter
    lateinit var binding: FragmentHomeBinding

    val TAG = "HomeFragment"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
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
                layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
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
                }
            }
        }

        fetchRemoteEvents()
        subscribeObservers()
    }

    private fun fetchRemoteEvents() {
        homeViewModel.fetchRemoteEvents(HomeStateEvent.FetchBanners)
        homeViewModel.fetchRemoteEvents(HomeStateEvent.FetchCategories)
        homeViewModel.fetchRemoteEvents(HomeStateEvent.FetchBrands)
    }

    private fun subscribeObservers() {
        homeViewModel.bannerState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is Resource.Success<BannerModel> -> {
                    dataState.data.let { bannerModel ->
                        bannerAdapter.differ.submitList(bannerModel.banners.toList())
                    }
                }
                is Resource.Error -> {
                    dataState.exception.let { message ->
                        Toast.makeText(
                            activity,
                            "An error occured: ${message.message}",
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
                is Resource.Success<CategoryModel> -> {
                    dataState.data.let { categoryModel ->
                        categoryAdapter.submitList(categoryModel.categories)
                    }
                }
                is Resource.Error -> {
                    dataState.exception.let { message ->
                        Toast.makeText(
                            activity,
                            "An error occured: ${message.message}",
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
                is Resource.Success<BrandModel> -> {
                    dataState.data.let { brandModel ->
                        brandAdapter.submitList(brandModel.brands)
                    }
                }
                is Resource.Error -> {
                    dataState.exception.let { message ->
                        Toast.makeText(
                            activity,
                            "An error occured: ${message.message}",
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

}