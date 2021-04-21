package com.example.bunonbasket.ui.component.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunonbasket.data.repository.cache.CacheRepository
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by inan on 15/4/21
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataRepository: CacheRepository
) : ViewModel() {

    private val _dataState: MutableLiveData<Resource<Boolean>> = MutableLiveData()

    val dataState: LiveData<Resource<Boolean>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: SplashStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is SplashStateEvent.LoadAppIntro -> {
                    dataRepository.loadAppIntro()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }.launchIn(viewModelScope)
                }
                is SplashStateEvent.None -> {

                }
            }
        }
    }
}

sealed class SplashStateEvent {
    object LoadAppIntro : SplashStateEvent()

    object None : SplashStateEvent()
}