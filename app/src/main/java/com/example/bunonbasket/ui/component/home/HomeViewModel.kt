package com.example.bunonbasket.ui.component.home

import androidx.lifecycle.*
import com.example.bunonbasket.data.models.banner.BannerModel
import com.example.bunonbasket.data.models.brands.BrandModel
import com.example.bunonbasket.data.models.category.Category
import com.example.bunonbasket.data.models.category.CategoryModel
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
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val _dataState: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    private val _loadDataState: MutableLiveData<Resource<Boolean>> = MutableLiveData()


    private val _bannerState: MutableLiveData<Resource<BannerModel>> = MutableLiveData()
    private val _categoryState: MutableLiveData<Resource<CategoryModel>> = MutableLiveData()
    private val _brandState: MutableLiveData<Resource<BrandModel>> = MutableLiveData()

    private val taskEventChannel = Channel<HomeStateEvent>()
    val homeEvent = taskEventChannel.receiveAsFlow()


    val categoryState: LiveData<Resource<CategoryModel>>
        get() = _categoryState

    val bannerState: LiveData<Resource<BannerModel>>
        get() = _bannerState

    val brandState: LiveData<Resource<BrandModel>>
        get() = _brandState

    val loadDataState: LiveData<Resource<Boolean>>
        get() = _loadDataState


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

                is HomeStateEvent.None -> {

                }
            }
        }
    }

    fun fetchRemoteEvents(mainStateEvent: HomeStateEvent) {
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
                    remoteRepository.fetchBrands()
                        .onEach { dataState ->
                            _brandState.value = dataState
                        }.launchIn(viewModelScope)
                }
            }
        }
    }

    fun onCategoryClicked(category: Category) = viewModelScope.launch {
        taskEventChannel.send(HomeStateEvent.NavigateToCategory(category))
    }
}

sealed class HomeStateEvent {
    object SaveShowCase : HomeStateEvent()

    object LoadShowCase : HomeStateEvent()

    object FetchBanners : HomeStateEvent()

    object FetchCategories : HomeStateEvent()

    object FetchBrands : HomeStateEvent()

    data class NavigateToCategory(val category: Category) : HomeStateEvent()

    object None : HomeStateEvent()
}