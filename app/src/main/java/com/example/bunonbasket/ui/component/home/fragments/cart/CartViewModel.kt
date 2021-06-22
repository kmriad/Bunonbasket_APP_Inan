package com.example.bunonbasket.ui.component.home.fragments.cart

import androidx.lifecycle.*
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.cart.CartListModel
import com.example.bunonbasket.data.repository.cache.CacheRepository
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by inan on 21/6/21
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class CartViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val cacheRepository: CacheRepository,
    private val dataStoreManager: DataStoreManager,
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val _token: MutableLiveData<String> = MutableLiveData()
    private val _price: MutableLiveData<Int> = MutableLiveData()
    private val _cartDataState: MutableLiveData<Resource<BaseModel<CartListModel>>> =
        MutableLiveData()

    val token: LiveData<String>
        get() = _token

    val price: LiveData<Int>
        get() = _price

    val cartState: LiveData<Resource<BaseModel<CartListModel>>>
        get() = _cartDataState

    fun fetchRemoteEvents(cartStateEvent: CartStateEvent) {
        viewModelScope.launch {
            when (cartStateEvent) {
                is CartStateEvent.FetchCarts -> {
                    remoteRepository.fetchCart(_token.value!!)
                        .onEach { dataState ->
                            _cartDataState.value = dataState
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
}

sealed class CartStateEvent {

    object FetchCarts : CartStateEvent()

    object LoadToken : CartStateEvent()
}