package com.example.bunonbasket.ui.component.deliverystatus

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.deliverystatus.DeliveryStatusModel
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DeliveryStatusViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {
    private val _token: MutableLiveData<String> = MutableLiveData()

    private val _deliverStatusState: MutableLiveData<Resource<BaseDetailsModel<DeliveryStatusModel>>> =
        MutableLiveData()

    val token: LiveData<String>
        get() = _token

    val deliverStatusState: LiveData<Resource<BaseDetailsModel<DeliveryStatusModel>>>
        get() = _deliverStatusState

    init {
        fetchRemoteEvents(DeliveryStatusEvent.LoadToken)
    }

    fun fetchRemoteEvents(cartStateEvent: DeliveryStatusEvent) {
        viewModelScope.launch {
            when (cartStateEvent) {

                is DeliveryStatusEvent.FetchDeliveryStatus -> {
                    Log.d("token", _token.value!!)
                    remoteRepository.fetchDeliveryStatus(
                        cartId = cartStateEvent.cartId,
                        authHeader = _token.value!!
                    ).onEach { dataState ->
                        _deliverStatusState.value = dataState
                    }.launchIn(viewModelScope)
                }

                is DeliveryStatusEvent.LoadToken -> {
                    dataStoreManager.authToken().onEach { dataState ->
                        _token.value = dataState
                    }.launchIn(viewModelScope)
                }
            }
        }
    }


}

sealed class DeliveryStatusEvent {

    object LoadToken : DeliveryStatusEvent()

    data class FetchDeliveryStatus(val cartId: Int) : DeliveryStatusEvent()

}