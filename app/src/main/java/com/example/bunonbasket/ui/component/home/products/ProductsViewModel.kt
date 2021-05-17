package com.example.bunonbasket.ui.component.home.products

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by inan on 17/5/21
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {


    private val _productLiveData: MutableLiveData<PagingData<Product>> = MutableLiveData()

    val productLiveData: LiveData<PagingData<Product>>
        get() = _productLiveData

//    val photos = currentQuery.switchMap { queryString ->
//        remoteRepository.fetchAllProducts(queryString).cachedIn(viewModelScope)
//    }

    fun fetchProducts(productStateEvent: ProductStateEvent) {
        viewModelScope.launch {
            when (productStateEvent) {
                is ProductStateEvent.FetchProducts -> {
//                    currentQuery.switchMap { queryString ->
//                        remoteRepository.fetchAllProducts(queryString).cachedIn(viewModelScope)
//                    }

                    Log.d("ProductListActivity",productStateEvent.id)
                    try{
                        remoteRepository.fetchAllProducts(productStateEvent.id).cachedIn(viewModelScope).
//                        _productLiveData.switchMap {
//                            remoteRepository.fetchAllProducts(productStateEvent.id)
//                                .cachedIn(viewModelScope)
//                        }
                    }catch (e:Exception){
                       Log.d("ProductListActivity",e.toString())
                    }


                }
            }
        }
    }

    fun fetchEvent(query: String) {
        Log.d("ProductListActivity",query)
        fetchProducts(ProductStateEvent.FetchProducts(query))
    }

    companion object {
        private const val DEFAULT_QUERY = ""
    }
}

sealed class ProductStateEvent {
    data class FetchProducts(val id: String) : ProductStateEvent()
}