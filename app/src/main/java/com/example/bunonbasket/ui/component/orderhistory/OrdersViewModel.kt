package com.example.bunonbasket.ui.component.orderhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.orders.OrderHistoryModel
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {
    private val _token: MutableLiveData<String> = MutableLiveData()

    private val _dataState: MutableLiveData<Resource<BaseModel<OrderHistoryModel>>> =
        MutableLiveData()

    val dataState: LiveData<Resource<BaseModel<OrderHistoryModel>>>
        get() = _dataState

    private val _deliveryState: MutableLiveData<Resource<BaseModel<OrderHistoryModel>>> =
        MutableLiveData()

    val deliveryState: LiveData<Resource<BaseModel<OrderHistoryModel>>>
        get() = _deliveryState

    private val _cancelledState: MutableLiveData<Resource<BaseModel<OrderHistoryModel>>> =
        MutableLiveData()

    val cancelledState: LiveData<Resource<BaseModel<OrderHistoryModel>>>
        get() = _cancelledState

    val token: LiveData<String>
        get() = _token

    private val taskEventChannel = Channel<OrderStateEvent>()
    val homeEvent = taskEventChannel.receiveAsFlow()

    fun onCheckOutClicked(cartId: Int) = viewModelScope.launch {
        taskEventChannel.send(OrderStateEvent.NavigateToDeliveryStatus(cartId = cartId))
    }


    init {
        setStateEvent(OrderStateEvent.LoadToken)
    }

    fun setStateEvent(orderStateEvent: OrderStateEvent) {
        viewModelScope.launch {
            when (orderStateEvent) {
                is OrderStateEvent.LoadToken -> {
                    dataStoreManager.authToken().onEach { dataState ->
                        _token.value = dataState
                    }.launchIn(viewModelScope)
                }
                is OrderStateEvent.FetchOrders -> {
                    remoteRepository.fetchOrderHistory(authHeader = _token.value!!)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }.launchIn(viewModelScope)
                }
                is OrderStateEvent.FetchDeliveries -> {
                    remoteRepository.fetchDeliveredOrders(authHeader = _token.value!!)
                        .onEach { dataState ->
                            _deliveryState.value = dataState
                        }.launchIn(viewModelScope)
                }
                is OrderStateEvent.FetchCancels -> {
                    remoteRepository.fetchCancelledOrders(authHeader = _token.value!!)
                        .onEach { dataState ->
                            _cancelledState.value = dataState
                        }.launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class OrderStateEvent {

    object FetchOrders : OrderStateEvent()

    object FetchDeliveries : OrderStateEvent()

    object FetchCancels : OrderStateEvent()

    object LoadToken : OrderStateEvent()

    data class NavigateToDeliveryStatus(val cartId: Int) : OrderStateEvent()

}