package com.example.bunonbasket.ui.component.details

import androidx.lifecycle.*
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.cart.CartModel
import com.example.bunonbasket.data.models.product.ProductDetails
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by inan on 24/5/21
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val dataStoreManager: DataStoreManager,
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {
    private val _productDataState: MutableLiveData<Resource<BaseDetailsModel<ProductDetails>>> =
        MutableLiveData()

    private val _cartDataState: MutableLiveData<Resource<BaseDetailsModel<CartModel>>> =
        MutableLiveData()

    val productDataState: LiveData<Resource<BaseDetailsModel<ProductDetails>>>
        get() = _productDataState

    val cartDataState: LiveData<Resource<BaseDetailsModel<CartModel>>>
        get() = _cartDataState

    var _counter: MutableLiveData<Int> = MutableLiveData()

    val counter: LiveData<Int>
        get() = _counter

    private val _token: MutableLiveData<String> = MutableLiveData()

    val token: LiveData<String>
        get() = _token

    fun setcounter(number: Int) {
        viewModelScope.launch {
            _counter.value = number
        }
    }

    fun incrementCounter() {
        viewModelScope.launch {
            if (_counter.value!! >= 0) {
                _counter.value = _counter.value!! + 1
            }
        }
    }

    fun decrementCounter() {
        viewModelScope.launch {
            if (_counter.value!! >= 0) {
                _counter.value = _counter.value!! - 1
            }
        }
    }

    init {
        setcounter(0)
    }

    override fun onCleared() {
        super.onCleared()
        setcounter(0)
    }

    fun fetchProductDetails(productDetailsEvent: ProductDetailsEvent) {
        viewModelScope.launch {
            when (productDetailsEvent) {
                is ProductDetailsEvent.FetchProductDetails -> {
                    remoteRepository.fetchProductDetails(productDetailsEvent.id)
                        .onEach { dataState ->
                            _productDataState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is ProductDetailsEvent.AddToCart -> {
                    remoteRepository.addToCart(
                        productDetailsEvent.productId,
                        productDetailsEvent.quantity,
                        _token.value!!,
                    )
                        .onEach { dataState ->
                            _cartDataState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is ProductDetailsEvent.LoadToken -> {
                    dataStoreManager.authToken().onEach { dataState ->
                        _token.value = dataState
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    fun addToCart(productId: String) {
        fetchProductDetails(ProductDetailsEvent.AddToCart(productId, _counter.value!!))
    }

    fun fetchToken() {
        fetchProductDetails(ProductDetailsEvent.LoadToken)
    }
}

sealed class ProductDetailsEvent {
    data class FetchProductDetails(val id: String) : ProductDetailsEvent()

    data class AddToCart(val productId: String, val quantity: Int) : ProductDetailsEvent()

    object LoadToken : ProductDetailsEvent()
}