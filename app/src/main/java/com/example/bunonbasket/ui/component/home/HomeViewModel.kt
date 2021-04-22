package com.example.bunonbasket.ui.component.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunonbasket.data.models.banner.BannerModel
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
 * Created by inan on 20/4/21
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataRepository: CacheRepository,
    private val remoteRepository: RemoteRepository,
) : ViewModel() {

    private val _dataState: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    private val _loadDataState: MutableLiveData<Resource<Boolean>> = MutableLiveData()


    private val _bannerState: MutableLiveData<Resource<BannerModel>> = MutableLiveData()

    val bannerState: LiveData<Resource<BannerModel>>
        get() = _bannerState

    val dataState: LiveData<Resource<Boolean>>
        get() = _dataState

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
            }
        }
    }
}

sealed class HomeStateEvent {
    object SaveShowCase : HomeStateEvent()

    object LoadShowCase : HomeStateEvent()

    object FetchBanners : HomeStateEvent()

    object None : HomeStateEvent()
}