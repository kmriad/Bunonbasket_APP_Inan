package com.example.bunonbasket.ui.component.orderhistory.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.orders.OrderHistoryModel
import com.example.bunonbasket.databinding.FragmentDeliveryBinding
import com.example.bunonbasket.ui.component.deliverystatus.DeliveryStatusActivity
import com.example.bunonbasket.ui.component.orderhistory.OrderStateEvent
import com.example.bunonbasket.ui.component.orderhistory.OrdersViewModel
import com.example.bunonbasket.ui.component.orderhistory.adapters.OrderAdapter
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DeliveryFragment : Fragment(), OrderAdapter.OnItemClickListener {

    private val orderViewModel: OrdersViewModel by viewModels()
    lateinit var binding: FragmentDeliveryBinding
    lateinit var orderAdapter: OrderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_delivery, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            orderAdapter = OrderAdapter(this@DeliveryFragment)
            binding.orderRv.apply {
                adapter = orderAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
            orderViewModel.setStateEvent(OrderStateEvent.FetchDeliveries)

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                orderViewModel.homeEvent.collect { event ->
                    when (event) {
                        is OrderStateEvent.NavigateToDeliveryStatus -> {
                            val intent = Intent(activity, DeliveryStatusActivity::class.java)
                            intent.putExtra("cartId", event.cartId)
                            startActivity(intent)
                        }
                    }
                }
            }
            subscribeObservers()
        }
    }

    private fun subscribeObservers() {
        orderViewModel.deliveryState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<BaseModel<OrderHistoryModel>> -> {
                    dataState.data.let { data ->
                        val itemList: List<OrderHistoryModel> = data.results
                        binding.progressBar.visibility = View.GONE
                        if (itemList.size > 0) {
                            orderAdapter.submitList(itemList)
                            binding.orderRv.visibility = View.VISIBLE
                            binding.animationView.visibility = View.GONE
                        } else {
                            binding.animationView.visibility = View.VISIBLE
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

    override fun onItemClick(order: OrderHistoryModel?) {
        orderViewModel.onCheckOutClicked(order!!.id)
    }

}