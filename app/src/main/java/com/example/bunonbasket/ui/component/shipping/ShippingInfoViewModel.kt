package com.example.bunonbasket.ui.component.shipping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.cart.AreaModel
import com.example.bunonbasket.data.models.cart.CityModel
import com.example.bunonbasket.data.models.cart.ShippingInfo
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by inan on 24/6/21
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class ShippingInfoViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    private val _token: MutableLiveData<String> = MutableLiveData()

    private val _cityState: MutableLiveData<Resource<BaseModel<CityModel>>> =
        MutableLiveData()

    private val _shippingInfoState: MutableLiveData<Resource<BaseDetailsModel<ShippingInfo>>> =
        MutableLiveData()


    private val _areaState: MutableLiveData<Resource<BaseModel<AreaModel>>> =
        MutableLiveData()

    val token: LiveData<String>
        get() = _token

    val cityState: LiveData<Resource<BaseModel<CityModel>>>
        get() = _cityState

    val areaState: LiveData<Resource<BaseModel<AreaModel>>>
        get() = _areaState

    val shippingInfoState: LiveData<Resource<BaseDetailsModel<ShippingInfo>>>
        get() = _shippingInfoState

    init {
        fetchRemoteEvents(ShippingInfoStateEvent.LoadToken)
    }

    fun fetchRemoteEvents(shippingInfoStateEvent: ShippingInfoStateEvent) {
        viewModelScope.launch {
            when (shippingInfoStateEvent) {
                is ShippingInfoStateEvent.LoadToken -> {
                    dataStoreManager.authToken().onEach { dataState ->
                        _token.value = dataState
                    }.launchIn(viewModelScope)
                }

                is ShippingInfoStateEvent.FetchCityList -> {
                    remoteRepository.fetchCities(
                        token = _token.value!!
                    )
                        .onEach { dataState ->
                            _cityState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is ShippingInfoStateEvent.FetchAreaByCity -> {
                    remoteRepository.fetchAreas(
                        token = _token.value!!,
                        areaId = shippingInfoStateEvent.cityId
                    )
                        .onEach { dataState ->
                            _areaState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is ShippingInfoStateEvent.InsertShippingInfo -> {
                    remoteRepository.addShippingInfo(
                        name = shippingInfoStateEvent.name,
                        phone = shippingInfoStateEvent.phone,
                        city = shippingInfoStateEvent.city,
                        country = shippingInfoStateEvent.country,
                        address = shippingInfoStateEvent.address,
                        area = shippingInfoStateEvent.area,
                        authHeader = _token.value!!
                    ).onEach { dataState ->
                        _shippingInfoState.value = dataState
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class ShippingInfoStateEvent {
    object LoadToken : ShippingInfoStateEvent()

    object FetchCityList : ShippingInfoStateEvent()

    data class FetchAreaByCity(val cityId: Int) : ShippingInfoStateEvent()

    data class InsertShippingInfo(
        val name: String,
        val phone: String,
        val address: String,
        val country: String,
        val city: String,
        val area: String
    ) : ShippingInfoStateEvent()
}