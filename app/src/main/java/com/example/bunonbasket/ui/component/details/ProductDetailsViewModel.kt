package com.example.bunonbasket.ui.component.details

import androidx.lifecycle.*
import com.example.bunonbasket.data.models.base.BaseDetailsModel
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
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {
    private val _productDataState: MutableLiveData<Resource<BaseDetailsModel<ProductDetails>>> =
        MutableLiveData()

    val productDataState: LiveData<Resource<BaseDetailsModel<ProductDetails>>>
        get() = _productDataState

    fun fetchProductDetails(productDetailsEvent: ProductDetailsEvent) {
        viewModelScope.launch {
            when (productDetailsEvent) {
                is ProductDetailsEvent.FetchProductDetails -> {
                    remoteRepository.fetchProductDetails(productDetailsEvent.id)
                        .onEach { dataState ->
                            _productDataState.value = dataState
                        }.launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class ProductDetailsEvent {
    data class FetchProductDetails(val id: String) : ProductDetailsEvent()
}