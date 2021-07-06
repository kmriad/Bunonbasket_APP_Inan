package com.example.bunonbasket.ui.component.home.fragments.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.cart.ShippingInfo
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ShippingAddressViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    private val _token: MutableLiveData<String> = MutableLiveData()

    val token: LiveData<String>
        get() = _token

    private val _shippingDataState: MutableLiveData<Resource<BaseDetailsModel<ShippingInfo>>> =
        MutableLiveData()

    val shippingDataState: LiveData<Resource<BaseDetailsModel<ShippingInfo>>>
        get() = _shippingDataState

    private val shippingEventChannel = Channel<ShippingAddressStateEvent>()
    val shippingStateEvent = shippingEventChannel.receiveAsFlow()

    init {
        setStateEvent(ShippingAddressStateEvent.LoadToken)
    }

    fun onShippingAddressClicked() = viewModelScope.launch {
        shippingEventChannel.send(ShippingAddressStateEvent.NavigateToShippingAddress)
    }

    fun setStateEvent(shippingAddressStateEvent: ShippingAddressStateEvent) {
        viewModelScope.launch {
            when (shippingAddressStateEvent) {
                is ShippingAddressStateEvent.LoadToken -> {
                    dataStoreManager.authToken().onEach { dataState ->
                        _token.value = dataState
                    }.launchIn(viewModelScope)
                }
                is ShippingAddressStateEvent.LoadShippingAddress -> {
                    remoteRepository.fetchShippingInfo(authHeader = _token.value!!)
                        .onEach { dataState ->
                            _shippingDataState.value = dataState
                        }.launchIn(viewModelScope)
                }
            }
        }
    }

}

sealed class ShippingAddressStateEvent {
    object LoadToken : ShippingAddressStateEvent()
    object LoadShippingAddress : ShippingAddressStateEvent()
    object NavigateToShippingAddress : ShippingAddressStateEvent()
}