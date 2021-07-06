package com.example.bunonbasket.ui.component.home.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.cart.ShippingInfo
import com.example.bunonbasket.databinding.FragmentShippingAddressBinding
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ShippingAddressFragment : Fragment() {

    lateinit var binding: FragmentShippingAddressBinding
    private val viewModel: ShippingAddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_shipping_address, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            subscribeObservers()

            binding.newShippingAddressSection.setOnClickListener {
                viewModel.onShippingAddressClicked()
            }

            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.shippingStateEvent.collect { event ->
                    when (event) {
                        is ShippingAddressStateEvent.NavigateToShippingAddress -> {
                            val action =
                                ShippingAddressFragmentDirections.actionShippingAddressFragmentToShippingInfoActivity()
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
    }

    private fun subscribeObservers() {
        try {
            viewModel.token.observe(viewLifecycleOwner, { dataState ->
                when (dataState) {
                    is String -> {
                        if (dataState.isNotEmpty()) {
                            viewModel.setStateEvent(ShippingAddressStateEvent.LoadShippingAddress)
                        }
                    }
                }
            })
        } catch (exception: Exception) {
            Toast.makeText(activity, "Please Login", Toast.LENGTH_SHORT).show()
        }
        viewModel.shippingDataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<BaseDetailsModel<ShippingInfo>> -> {
                    binding.data = dataState.data.results
                }
            }
        })
    }

}