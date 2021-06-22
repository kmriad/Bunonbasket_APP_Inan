package com.example.bunonbasket.ui.component.home.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.cart.CartListModel
import com.example.bunonbasket.databinding.FragmentCartBinding
import com.example.bunonbasket.ui.component.home.adapters.CartAdapter
import com.example.bunonbasket.utils.Resource
import com.example.bunonbasket.utils.widgets.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CartFragment : Fragment() {

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

        cartAdapter = CartAdapter()
        binding.cartItemsRV.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }

        dialog = activity?.let { LoadingDialog(it) }!!
        dialog.showLoadingDialog()

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
                        if (dataModel.results.size > 0) {
                            dialog.closeLoadingDialog()
                            binding.bodySection.visibility = View.VISIBLE
                            binding.emptyCart.visibility = View.GONE
                            cartAdapter.submitList(dataModel.results)
                            var price: Int = 0
                            for (result in dataModel.results) {
                                price += result.price
                            }
                            cartViewModel.setcounter(price)
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
        cartViewModel.price.observe(viewLifecycleOwner, { counter ->
            when (counter) {
                is Int -> {
                    binding.totalPrice.text = counter.toString()
                }
            }
        })
    }

}