package com.example.bunonbasket.ui.component.shipping

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import com.example.bunonbasket.R
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.cart.AreaModel
import com.example.bunonbasket.data.models.cart.CityModel
import com.example.bunonbasket.data.models.cart.ShippingInfo
import com.example.bunonbasket.databinding.ActivityShippingInfoBinding
import com.example.bunonbasket.ui.component.checkout.CheckoutActivity
import com.example.bunonbasket.utils.Constants.SHIPPING_INFO
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ShippingInfoActivity : AppCompatActivity() {

    lateinit var binding: ActivityShippingInfoBinding
    private val shippingInfoViewModel: ShippingInfoViewModel by viewModels()
    var cityList: MutableList<CityModel> = mutableListOf()
    var areaList: MutableList<AreaModel> = mutableListOf()
    var cityNameList: MutableList<String> = mutableListOf()
    var areaNameList: MutableList<String> = mutableListOf()

    var cityName: String = ""
    var areaName: String = ""
    var name: String = ""
    var phone: String = ""
    var address: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BunonBasket)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shipping_info)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            binding.citySpinner.onItemSelectedListener = CitySpinnerClass()
            binding.areaSpinner.onItemSelectedListener = AreaSpinnerClass()
            shippingInfoViewModel.fetchRemoteEvents(ShippingInfoStateEvent.FetchCityList)
            subscribeObservers()

            name = binding.nameInputField.text.toString()
            phone = binding.phoneNumberInputField.text.toString()
            address = binding.addressEditText.text.toString()


            binding.proceedToCheckoutButton.setOnClickListener {
                if (areaName != "" && cityName != "" && name != "" && phone != "") {
                    shippingInfoViewModel.fetchRemoteEvents(
                        ShippingInfoStateEvent.InsertShippingInfo(
                            name = name,
                            phone = phone,
                            country = "Bangladesh",
                            address = address,
                            area = areaName,
                            city = cityName
                        )
                    )
                } else {
                    if (address == "") {
                        binding.addressEditText.setError("You need to enter address")
                    }
                    if (name == "") {
                        binding.nameInputField.setError("You need to enter a name")
                    }
                    if (phone == "") {
                        binding.phoneNumberInputField.setError("You need to enter a phone number")
                    }
                }
            }
        }
    }


    fun subscribeObservers() {
        shippingInfoViewModel.cityState.observe(this, { dataState ->
            when (dataState) {
                is Resource.Success<BaseModel<CityModel>> -> {
                    dataState.data.let { cityModel ->
                        if (cityList.isNotEmpty()) {
                            cityList.clear()
                            cityNameList.clear()
                        }
                        cityList.addAll(cityModel.results)
                        for (city in cityList) {
                            cityNameList.add(city.name)
                        }
                        val ad = ArrayAdapter(
                            this,
                            android.R.layout.simple_spinner_item,
                            cityNameList
                        )
                        ad.setDropDownViewResource(
                            android.R.layout
                                .simple_spinner_dropdown_item
                        )

                        binding.citySpinner.apply {
                            adapter = ad
                        }
                    }
                }
            }
        })

        shippingInfoViewModel.areaState.observe(this, { dataState ->
            when (dataState) {
                is Resource.Success<BaseModel<AreaModel>> -> {
                    dataState.data.let { areaModel ->
                        if (areaList.isNotEmpty()) {
                            areaName = ""
                            areaList.clear()
                            areaNameList.clear()
                        }
                        areaList.addAll(areaModel.results)
                        for (city in areaList) {
                            areaNameList.add(city.name)
                        }
                        Log.d("ShippingInfoActivity", areaList.toString())
                        val ad = ArrayAdapter(
                            this,
                            android.R.layout.simple_spinner_item,
                            areaNameList
                        )
                        ad.setDropDownViewResource(
                            android.R.layout
                                .simple_spinner_dropdown_item
                        )

                        binding.areaSpinner.apply {
                            adapter = ad
                        }
                        ad.notifyDataSetChanged()
                    }
                }
            }
        })

        shippingInfoViewModel.shippingInfoState.observe(this, { dataState ->
            when (dataState) {
                is Resource.Success<BaseDetailsModel<ShippingInfo>> -> {
                    dataState.data.let { data ->
                        Toast.makeText(this, "Shipping Info Added", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, CheckoutActivity::class.java)
                        intent.putExtra(SHIPPING_INFO, data.results)
                        startActivity(intent)
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

    inner class CitySpinnerClass : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            cityName = cityNameList[p2]
            shippingInfoViewModel.fetchRemoteEvents(ShippingInfoStateEvent.FetchAreaByCity(cityId = cityList[p2].id))
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

    }

    inner class AreaSpinnerClass : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            if (areaNameList.isNotEmpty()) {
                areaName = areaNameList[p2]
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

    }
}