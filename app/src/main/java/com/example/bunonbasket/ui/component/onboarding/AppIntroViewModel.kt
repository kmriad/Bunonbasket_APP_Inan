package com.example.bunonbasket.ui.component.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunonbasket.data.DataRepository
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
class AppIntroViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _dataState: MutableLiveData<Resource<Boolean>> = MutableLiveData()

    val dataState: LiveData<Resource<Boolean>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: AppIntroStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is AppIntroStateEvent.SaveIntro -> {
                    dataRepository.saveAppIntro()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }.launchIn(viewModelScope)
                }
                is AppIntroStateEvent.None -> {

                }
            }
        }
    }
}

sealed class AppIntroStateEvent {
    object SaveIntro : AppIntroStateEvent()

    object None : AppIntroStateEvent()
}