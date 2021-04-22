package com.example.bunonbasket.ui.component.home.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.banner.BannerModel
import com.example.bunonbasket.databinding.FragmentHomeBinding
import com.example.bunonbasket.ui.component.home.HomeStateEvent
import com.example.bunonbasket.ui.component.home.HomeViewModel
import com.example.bunonbasket.ui.component.home.adapters.BannerAdapter
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {


    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var bannerAdapter: BannerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        binding.apply {
            bannerAdapter = BannerAdapter()
            binding.bannerViewPager.apply {
                adapter = bannerAdapter
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            }
        }

        homeViewModel.fetchRemoteEvents(HomeStateEvent.FetchBanners)
        subscribeObservers()
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
    }

}