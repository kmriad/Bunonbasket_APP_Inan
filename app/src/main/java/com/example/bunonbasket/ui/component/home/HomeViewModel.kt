package com.example.bunonbasket.ui.component.home

import android.util.Log
import androidx.lifecycle.*
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.models.banner.Banner
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.cart.CartListModel
import com.example.bunonbasket.data.models.category.Category
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.data.models.partners.PartnerModel
import com.example.bunonbasket.data.repository.cache.CacheRepository
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
 * Created by inan on 20/4/21
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataRepository: CacheRepository,
    private val remoteRepository: RemoteRepository,
    private val dataStoreManager: DataStoreManager,
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val _dataState: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    private val _loadDataState: MutableLiveData<Resource<Boolean>> = MutableLiveData()


    private val _bannerState: MutableLiveData<Resource<BaseModel<Banner>>> = MutableLiveData()
    private val _categoryState: MutableLiveData<Resource<BaseModel<Category>>> = MutableLiveData()
    private val _brandState: MutableLiveData<Resource<BaseModel<PartnerModel>>> = MutableLiveData()
    private val _bestSellingProductsState: MutableLiveData<Resource<BaseModel<Product>>> =
        MutableLiveData()
    private val _featuredProductsState: MutableLiveData<Resource<BaseModel<Product>>> =
        MutableLiveData()

    private val _cartDataState: MutableLiveData<Resource<BaseModel<CartListModel>>> =
        MutableLiveData()

    val cartState: LiveData<Resource<BaseModel<CartListModel>>>
        get() = _cartDataState

    private val _counterState: MutableLiveData<Int> = MutableLiveData()

    val counterState: LiveData<Int>
        get() = _counterState

    private val taskEventChannel = Channel<HomeStateEvent>()
    val homeEvent = taskEventChannel.receiveAsFlow()


    val categoryState: LiveData<Resource<BaseModel<Category>>>
        get() = _categoryState

    val bannerState: LiveData<Resource<BaseModel<Banner>>>
        get() = _bannerState

    val brandState: LiveData<Resource<BaseModel<PartnerModel>>>
        get() = _brandState

    val loadDataState: LiveData<Resource<Boolean>>
        get() = _loadDataState

    val featuredProductState: LiveData<Resource<BaseModel<Product>>>
        get() = _featuredProductsState

    val bestSellingProductState: LiveData<Resource<BaseModel<Product>>>
        get() = _bestSellingProductsState

    private val _token: MutableLiveData<String> = MutableLiveData()

    val token: LiveData<String>
        get() = _token

    var counter: Int = 0

    fun saveCounter(number: Int) {
        Log.d("HomeViewModel", number.toString())
        counter = number
        _counterState.value = counter
    }


    fun fetchCarts() {
        fetchRemoteEvents(HomeStateEvent.FetchCarts)
    }

    fun setStateEvent(mainStateEvent: HomeStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is HomeStateEvent.SaveShowCase -> {
                    dataRepository.saveShowCase()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is HomeStateEvent.LoadShowCase -> {
                    dataRepository.loadShowCase()
                        .onEach { loadDataState ->
                            _loadDataState.value = loadDataState
                        }.launchIn(viewModelScope)
                }

                is HomeStateEvent.LoadToken -> {
                    dataStoreManager.authToken()
                        .onEach { loadDataState ->
                            _token.value = loadDataState
                        }.launchIn(viewModelScope)
                }

                is HomeStateEvent.None -> {

                }
            }
        }
    }

    fun fetchRemoteEvents(mainStateEvent: HomeStateEvent) {
        Log.d("HomeViewModel", mainStateEvent.toString())
        viewModelScope.launch {
            when (mainStateEvent) {
                is HomeStateEvent.FetchBanners -> {
                    remoteRepository.fetchBanners()
                        .onEach { dataState ->
                            _bannerState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is HomeStateEvent.FetchCategories -> {
                    remoteRepository.fetchCategories()
                        .onEach { dataState ->
                            _categoryState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is HomeStateEvent.FetchBrands -> {
                    remoteRepository.fetchPartners()
                        .onEach { dataState ->
                            _brandState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is HomeStateEvent.FetchBestSellingProducts -> {
                    remoteRepository.fetchBestSellingProducts()
                        .onEach { dataState ->
                            _bestSellingProductsState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is HomeStateEvent.FetchFeaturedProducts -> {
                    remoteRepository.fetchFeaturedProducts()
                        .onEach { dataState ->
                            _featuredProductsState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is HomeStateEvent.FetchCarts -> {
                    remoteRepository.fetchCart(token = _token.value!!).onEach { dataState ->
                        _cartDataState.value = dataState
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    fun onCategoryClicked(category: Category) = viewModelScope.launch {
        taskEventChannel.send(HomeStateEvent.NavigateToCategory(category))
    }

    fun onProductClicked(product: Product) = viewModelScope.launch {
        taskEventChannel.send(HomeStateEvent.NavigateToProductDetails(product))
    }
}

sealed class HomeStateEvent {
    object SaveShowCase : HomeStateEvent()

    object FetchCarts : HomeStateEvent()

    object LoadToken : HomeStateEvent()

    object LoadShowCase : HomeStateEvent()

    object FetchBanners : HomeStateEvent()

    object FetchCategories : HomeStateEvent()

    object FetchBrands : HomeStateEvent()

    object FetchBestSellingProducts : HomeStateEvent()

    object FetchFeaturedProducts : HomeStateEvent()

    data class NavigateToCategory(val category: Category) : HomeStateEvent()

    data class NavigateToProductDetails(val product: Product) : HomeStateEvent()

    object None : HomeStateEvent()
}