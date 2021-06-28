package com.example.bunonbasket.ui.component.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.models.LoginModel
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.cart.CartListModel
import com.example.bunonbasket.data.models.checkout.CheckoutModel
import com.example.bunonbasket.data.repository.cache.CacheRepository
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by inan on 24/6/21
 */

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val dataStoreManager: DataStoreManager,
    private val cacheRepository: CacheRepository,
) : ViewModel() {

    private val _dataState: MutableLiveData<Resource<List<LoginModel>>> = MutableLiveData()

    private val _cartState: MutableLiveData<Resource<BaseModel<CartListModel>>> = MutableLiveData()

    private val _checkOutState: MutableLiveData<Resource<BaseDetailsModel<CheckoutModel>>> =
        MutableLiveData()

    private val _token: MutableLiveData<String> = MutableLiveData()

    private val _price: MutableLiveData<Int> = MutableLiveData()

    val token: LiveData<String>
        get() = _token

    val dataState: LiveData<Resource<List<LoginModel>>>
        get() = _dataState

    val cartState: LiveData<Resource<BaseModel<CartListModel>>>
        get() = _cartState

    val chekoutState: LiveData<Resource<BaseDetailsModel<CheckoutModel>>>
        get() = _checkOutState

    val price: LiveData<Int>
        get() = _price


    init {
        setStateEvent(CheckoutStateEvent.LoadToken)
        setStateEvent(CheckoutStateEvent.LoadProfile)
    }

    fun setStateEvent(accountStateEvent: CheckoutStateEvent) {
        viewModelScope.launch {
            when (accountStateEvent) {
                is CheckoutStateEvent.LoadProfile -> {
                    cacheRepository.getUserDetails()
                        .onEach { dataState ->
                            when (dataState) {
                                is Resource.Success<List<LoginModel>> -> {
                                    _dataState.value = dataState
                                }
                            }
                        }.launchIn(viewModelScope)
                }
                is CheckoutStateEvent.LoadToken -> {
                    dataStoreManager.authToken().onEach { dataState ->
                        _token.value = dataState
                    }.launchIn(viewModelScope)
                }

                is CheckoutStateEvent.FetchCarts -> {
                    remoteRepository.fetchCart(token = _token.value!!).onEach { dataState ->
                        _cartState.value = dataState
                    }.launchIn(viewModelScope)
                }

                is CheckoutStateEvent.DoCheckout -> {
                    remoteRepository.doCheckout(authHeader = _token.value!!).onEach { dataState ->
                        _checkOutState.value = dataState
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    fun fetchCarts() {
        setStateEvent(CheckoutStateEvent.FetchCarts)
    }

    fun setcounter(number: Int) {
        viewModelScope.launch {
            _price.value = number
        }
    }

}

sealed class CheckoutStateEvent {

    object LoadProfile : CheckoutStateEvent()

    object FetchCarts : CheckoutStateEvent()

    object LoadToken : CheckoutStateEvent()

    object DoCheckout : CheckoutStateEvent()

}