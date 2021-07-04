package com.example.bunonbasket.ui.component.home.fragments.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.cart.CartListModel
import com.example.bunonbasket.data.models.cart.QuantityUpdateModel
import com.example.bunonbasket.data.models.cart.ShippingInfo
import com.example.bunonbasket.databinding.FragmentCartBinding
import com.example.bunonbasket.ui.component.home.adapters.CartAdapter
import com.example.bunonbasket.utils.Resource
import com.example.bunonbasket.utils.widgets.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CartFragment : Fragment(), CartAdapter.OnCartUpdateListener {

    private val cartViewModel: CartViewModel by viewModels()
    lateinit var binding: FragmentCartBinding
    lateinit var dialog: LoadingDialog
    lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)

        subscribeObservers()

        binding.data = cartViewModel
        cartViewModel.loadToken()

        cartAdapter = CartAdapter(this)
        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.cartItemsRV.apply {
            adapter = cartAdapter
            layoutManager = mLayoutManager
        }
        DividerItemDecoration(
            context, // context
            mLayoutManager.orientation
        ).apply {
            binding.cartItemsRV.addItemDecoration(this)
        }

        dialog = activity?.let { LoadingDialog(it) }!!
        dialog.showLoadingDialog()

        binding.checkOutBtn.setOnClickListener {
            dialog.showLoadingDialog()
            cartViewModel.fetchRemoteEvents(CartStateEvent.FetchShippingInfo)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            cartViewModel.homeEvent.collect { event ->
                when (event) {
                    is CartStateEvent.NavigateToShippingInfo -> {
                        val action =
                            CartFragmentDirections.actionCartFragmentToShippingInfoActivity()
                        findNavController().navigate(action)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            cartViewModel.shippingEvent.collect { event ->
                when (event) {
                    is CartStateEvent.NavigateToCheckout -> {
                        val action =
                            CartFragmentDirections.actionCartFragmentToCheckoutActivity()
                        findNavController().navigate(action)
                    }
                }
            }
        }
        return binding.root
    }

    private fun subscribeObservers() {
        cartViewModel.token.observe(viewLifecycleOwner, { token ->
            when (token) {
                is String -> {
                    if (token.isNotEmpty()) {
                        cartViewModel.fetchCarts()
                    }
                }
            }
        })

        cartViewModel.cartState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<BaseModel<CartListModel>> -> {
                    dataState.data.let { dataModel ->
                        dialog.closeLoadingDialog()
                        if (dataModel.results.size > 0) {
                            binding.bodySection.visibility = View.VISIBLE
                            binding.emptyCart.visibility = View.GONE
                            cartAdapter.submitList(dataModel.results)

                            var price = 0
                            for (result in dataModel.results) {
                                price += result.price
                            }
                            cartViewModel.setcounter(price)
                        } else {
                            binding.bodySection.visibility = View.GONE
                            binding.emptyCart.visibility = View.VISIBLE
                        }
                    }
                }
                is Resource.Error -> {
                    dialog.closeLoadingDialog()
                    dataState.exception.let { message ->
                        Toast.makeText(
                            activity,
                            "An error occured banner: ${message.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        })

        cartViewModel.quantityDataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<BaseDetailsModel<QuantityUpdateModel>> -> {
                    dataState.data.let { dataModel ->
                        dialog.closeLoadingDialog()
                        cartViewModel.fetchCarts()
                    }
                }
                is Resource.Loading -> {
                    dialog.showLoadingDialog()
                }
                is Resource.Error -> {
                    dialog.closeLoadingDialog()
                }
            }
        })
        cartViewModel.price.observe(viewLifecycleOwner, { counter ->
            when (counter) {
                is Int -> {
                    binding.totalPrice.text = counter.toString()
                }
            }
        })
        cartViewModel.deleteDataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<BaseDetailsModel<Any?>> -> {
                    dataState.data.let { dataModel ->
                        cartViewModel.fetchCarts()
                    }
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {

                }
            }
        })
        cartViewModel.shippingDataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is Resource.Success<BaseDetailsModel<ShippingInfo>> -> {
                    dataState.data.let { dataModel ->
                        if (dataModel.results.user_id != null) {
                            dialog.closeLoadingDialog()
                            cartViewModel.onShippingInfoReceived()
                        } else {
                            cartViewModel.onCheckOutClicked()
                        }
                    }
                }
            }
        })
    }

    override fun onAddButtonClick(quantity: Int, id: Int) {
        Log.d("CartFragment", quantity.toString())
        cartViewModel.incrementCounter(quantity, id)
    }

    override fun onRemoveButtonClick(quantity: Int, id: Int) {
        cartViewModel.decrementCounter(quantity, id)
    }

    override fun onItemSelected(cart: CartListModel) {
        binding.deleteButton.isClickable = true

        binding.deleteButton.setOnClickListener { it ->
            if (it.isClickable) {
                cartViewModel.deleteCarts(cart.id)
            } else {
                Toast.makeText(activity, "You must select an item", Toast.LENGTH_SHORT).show()
            }
        }
    }

}