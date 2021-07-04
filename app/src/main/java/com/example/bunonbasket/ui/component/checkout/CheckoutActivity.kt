package com.example.bunonbasket.ui.component.checkout

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.LoginModel
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.cart.CartListModel
import com.example.bunonbasket.data.models.cart.ShippingInfo
import com.example.bunonbasket.data.models.checkout.CheckoutModel
import com.example.bunonbasket.databinding.ActivityCheckoutBinding
import com.example.bunonbasket.ui.component.CheckoutCompleteActivity
import com.example.bunonbasket.ui.component.checkout.adapter.CheckoutAdapter
import com.example.bunonbasket.ui.component.shipping.ShippingInfoActivity
import com.example.bunonbasket.utils.Resource
import com.example.bunonbasket.utils.widgets.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity() {

    lateinit var binding: ActivityCheckoutBinding
    lateinit var shippingInfo: ShippingInfo
    private val checkoutViewModel: CheckoutViewModel by viewModels()
    lateinit var userModel: LoginModel
    lateinit var totalPrice: String
    lateinit var checkoutAdapter: CheckoutAdapter
    lateinit var dialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BunonBasket)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout)
        setSupportActionBar(binding.checkOutToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dialog = LoadingDialog(this)
        dialog.showLoadingDialog()

        checkoutAdapter = CheckoutAdapter()
        binding.itemRv.apply {
            layoutManager =
                LinearLayoutManager(this@CheckoutActivity, LinearLayoutManager.VERTICAL, false)
            adapter = checkoutAdapter
        }
        binding.checkOutBtn.setOnClickListener {
            checkoutViewModel.setStateEvent(CheckoutStateEvent.DoCheckout)
        }

        binding.shippingAddressButton.setOnClickListener {
            val intent = Intent(this, ShippingInfoActivity::class.java)
            startActivity(intent)
        }
        subscribeObservers()
    }

    private fun subscribeObservers() {
        checkoutViewModel.dataState.observe(this, { dataState ->
            when (dataState) {
                is Resource.Success<List<LoginModel>> -> {
                    dataState.data.let { data ->
                        userModel = data[0]
                        binding.user = userModel
                        checkoutViewModel.fetchCarts()
                    }
                }
            }
        })

        checkoutViewModel.cartState.observe(this, { dataState ->
            when (dataState) {
                is Resource.Success<BaseModel<CartListModel>> -> {
                    dataState.data.let { data ->
                        val itemList: List<CartListModel> = data.results
                        checkoutAdapter.submitList(itemList)
                        var price = 0
                        for (item in itemList) {
                            price += item.price
                        }
                        checkoutViewModel.setcounter(price)
                    }
                }
            }
        })
        checkoutViewModel.price.observe(this, { counter ->
            when (counter) {
                is Int -> {
                    binding.totalPrice.text = counter.toString()
                }
            }
        })

        checkoutViewModel.chekoutState.observe(this, { dataState ->
            when (dataState) {
                is Resource.Success<BaseDetailsModel<CheckoutModel>> -> {
                    dataState.data.let { data ->
                        val intent = Intent(this, CheckoutCompleteActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

        })

        checkoutViewModel.shippingDataState.observe(this, { dataState ->
            when (dataState) {
                is Resource.Success<BaseDetailsModel<ShippingInfo>> -> {
                    dataState.data.let { data ->
                        dialog.closeLoadingDialog()
                        shippingInfo = data.results
                        binding.data = shippingInfo
                    }
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val parentIntent = NavUtils.getParentActivityIntent(this)
                parentIntent!!.flags =
                    Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP or
                            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(parentIntent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}