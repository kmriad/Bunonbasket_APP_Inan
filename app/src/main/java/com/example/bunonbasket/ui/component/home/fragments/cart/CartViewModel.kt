package com.example.bunonbasket.ui.component.home.fragments.cart

import android.util.Log
import androidx.lifecycle.*
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.cart.CartListModel
import com.example.bunonbasket.data.models.cart.QuantityUpdateModel
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

/**
 * Created by inan on 21/6/21
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class CartViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val dataStoreManager: DataStoreManager,
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val _token: MutableLiveData<String> = MutableLiveData()
    private val _price: MutableLiveData<Int> = MutableLiveData()
    private val _cartDataState: MutableLiveData<Resource<BaseModel<CartListModel>>> =
        MutableLiveData()

    private val _quantityDataState: MutableLiveData<Resource<BaseDetailsModel<QuantityUpdateModel>>> =
        MutableLiveData()

    private val _deleteDataState: MutableLiveData<Resource<BaseDetailsModel<Any?>>> =
        MutableLiveData()

    private val _shippingDataState: MutableLiveData<Resource<BaseDetailsModel<ShippingInfo>>> =
        MutableLiveData()

    val token: LiveData<String>
        get() = _token

    val price: LiveData<Int>
        get() = _price

    val cartState: LiveData<Resource<BaseModel<CartListModel>>>
        get() = _cartDataState

    val quantityDataState: LiveData<Resource<BaseDetailsModel<QuantityUpdateModel>>>
        get() = _quantityDataState

    val deleteDataState: LiveData<Resource<BaseDetailsModel<Any?>>>
        get() = _deleteDataState

    val shippingDataState: LiveData<Resource<BaseDetailsModel<ShippingInfo>>>
        get() = _shippingDataState

    private val taskEventChannel = Channel<CartStateEvent>()
    val homeEvent = taskEventChannel.receiveAsFlow()

    fun onCheckOutClicked() = viewModelScope.launch {
        taskEventChannel.send(CartStateEvent.NavigateToShippingInfo)
    }

    private val shippingEventChannel = Channel<CartStateEvent>()
    val shippingEvent = shippingEventChannel.receiveAsFlow()

    fun onShippingInfoReceived() = viewModelScope.launch {
        shippingEventChannel.send(CartStateEvent.NavigateToCheckout)
    }


    init {
        Log.d("CartFragment", "called")
        loadToken()
    }
    fun fetchRemoteEvents(cartStateEvent: CartStateEvent) {
        viewModelScope.launch {
            when (cartStateEvent) {
                is CartStateEvent.FetchCarts -> {
                    remoteRepository.fetchCart(_token.value!!)
                        .onEach { dataState ->
                            _cartDataState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is CartStateEvent.FetchShippingInfo -> {
                    remoteRepository.fetchShippingInfo(authHeader = _token.value!!)
                        .onEach { dataState ->
                            _shippingDataState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is CartStateEvent.UpdateQuantity -> {
                    Log.d("token", _token.value!!)
                    remoteRepository.updateQuantity(
                        cartId = cartStateEvent.id,
                        quantity = cartStateEvent.quantity,
                        token = _token.value!!
                    ).onEach { dataState ->
                        _quantityDataState.value = dataState
                    }.launchIn(viewModelScope)
                }

                is CartStateEvent.DeleteCart -> {
                    remoteRepository.deleteItem(
                        cartId = cartStateEvent.id,
                        authHeader = _token.value!!
                    ).onEach { dataState ->
                        _deleteDataState.value = dataState
                    }.launchIn(viewModelScope)
                }

                is CartStateEvent.LoadToken -> {
                    dataStoreManager.authToken().onEach { dataState ->
                        _token.value = dataState
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    fun setcounter(number: Int) {
        viewModelScope.launch {
            _price.value = number
        }
    }

    fun loadToken() {
        fetchRemoteEvents(CartStateEvent.LoadToken)
    }

    fun fetchCarts() {
        fetchRemoteEvents(CartStateEvent.FetchCarts)
    }

    fun deleteCarts(id: Int) {
        fetchRemoteEvents(CartStateEvent.DeleteCart(id))
    }

    fun incrementCounter(quantity: Int, id: Int) {
        fetchRemoteEvents(CartStateEvent.UpdateQuantity(id, quantity))
    }

    fun decrementCounter(quantity: Int, id: Int) {
        fetchRemoteEvents(CartStateEvent.UpdateQuantity(id, quantity))
    }
}

sealed class CartStateEvent {

    object FetchCarts : CartStateEvent()

    object LoadToken : CartStateEvent()

    data class UpdateQuantity(val id: Int, val quantity: Int) : CartStateEvent()

    data class DeleteCart(val id: Int) : CartStateEvent()

    object FetchShippingInfo : CartStateEvent()

    object NavigateToShippingInfo : CartStateEvent()

    object NavigateToCheckout : CartStateEvent()
}