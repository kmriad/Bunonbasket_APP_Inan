package com.example.bunonbasket.ui.component.home.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.wishlist.WishListModel
import com.example.bunonbasket.databinding.FragmentWishListBinding
import com.example.bunonbasket.ui.component.home.WishListAdapter
import com.example.bunonbasket.ui.component.home.WishListStateEvent
import com.example.bunonbasket.ui.component.home.WishListViewModel
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class WishListFragment : Fragment(), WishListAdapter.OnItemClickListener {


    lateinit var binding: FragmentWishListBinding
    private val viewModel: WishListViewModel by viewModels()
    lateinit var wishListAdapter: WishListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wish_list, container, false)

        wishListAdapter = WishListAdapter(this)
        binding.wishListRv.apply {
            adapter = wishListAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }

        subscribeObservers()

        return binding.root
    }

    private fun subscribeObservers() {
        viewModel.token.observe(viewLifecycleOwner, { dataState ->
            if (dataState == "") {
                binding.progressBar.visibility = View.GONE
            }
            if (dataState.isNotEmpty()) {
                viewModel.fetchWishList(WishListStateEvent.FetchWishList)
            }


        })
        viewModel.wishlistDataState.observe(viewLifecycleOwner,
            { dataState ->
                when (dataState) {
                    is Resource.Success<BaseModel<WishListModel>> -> {
                        dataState.data.let { data ->
                            if (data.results.size > 0) {
                                wishListAdapter.submitList(data.results)
                                binding.animationView.visibility = View.GONE
                                binding.progressBar.visibility = View.GONE
                            } else {
                                binding.animationView.visibility = View.VISIBLE
                                binding.progressBar.visibility = View.GONE
                            }
                        }
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.animationView.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.animationView.visibility = View.VISIBLE
                    }
                }
            })
    }

    override fun onItemClick(product: WishListModel?) {
        TODO("Not yet implemented")
        Log.d("WishListFragment", "on clicked")
    }

}