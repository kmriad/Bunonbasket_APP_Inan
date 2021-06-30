package com.example.bunonbasket.ui.component.orderhistory.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.orders.OrderHistoryModel
import com.example.bunonbasket.databinding.FragmentDeliveryBinding
import com.example.bunonbasket.databinding.FragmentOrdersBinding
import com.example.bunonbasket.ui.component.orderhistory.OrderStateEvent
import com.example.bunonbasket.ui.component.orderhistory.OrdersViewModel
import com.example.bunonbasket.ui.component.orderhistory.adapters.OrderAdapter
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

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
            subscribeObservers()
        }
    }

    private fun subscribeObservers() {
        orderViewModel.deliveryState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<List<OrderHistoryModel>> -> {
                    dataState.data.let { data ->
                        val itemList: List<OrderHistoryModel> = data
                        orderAdapter.submitList(itemList)
                    }
                }
            }
        })

    }

    override fun onItemClick(order: OrderHistoryModel?) {
        TODO("Not yet implemented")
    }

}