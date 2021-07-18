package com.example.bunonbasket.ui.component.home.fragments.account

import androidx.lifecycle.*
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.models.LoginModel
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
 * Created by inan on 8/6/21
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val cacheRepository: CacheRepository,
    private val dataStoreManager: DataStoreManager,
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val taskEventChannel = Channel<AccountStateEvent>()
    val accountStateEvent = taskEventChannel.receiveAsFlow()

    private val accountEventChannel = Channel<AccountStateEvent>()
    val profileStateEvent = accountEventChannel.receiveAsFlow()

    private val shippingEventChannel = Channel<AccountStateEvent>()
    val shippingStateEvent = shippingEventChannel.receiveAsFlow()

    private val orderStatusEventChannel = Channel<AccountStateEvent>()
    val orderStatusStateEvent = orderStatusEventChannel.receiveAsFlow()

    private val _dataState: MutableLiveData<Resource<List<LoginModel>>> = MutableLiveData()
    private val _errorState: MutableLiveData<Resource<Exception>> = MutableLiveData()

    private val _token: MutableLiveData<String> = MutableLiveData()

    val token: LiveData<String>
        get() = _token

    val dataState: LiveData<Resource<List<LoginModel>>>
        get() = _dataState

    val errorState: LiveData<Resource<Exception>>
        get() = _errorState

    fun onLoginButtonClicked() = viewModelScope.launch {
        taskEventChannel.send(AccountStateEvent.NavigateToLoginActivity)
    }

    fun onOrderHistoryClicked() = viewModelScope.launch {
        accountEventChannel.send(AccountStateEvent.NavigateToOrderHistory)
    }

    fun onShippingAddressClicked() = viewModelScope.launch {
        shippingEventChannel.send(AccountStateEvent.NavigateToShippingAddress)
    }

    fun onOrderStatusClicked() = viewModelScope.launch {
        orderStatusEventChannel.send(AccountStateEvent.NavigateToOrderStatus)
    }

    fun onLoginProfile() {
        setStateEvent(AccountStateEvent.LoadProfile)
    }

    fun setStateEvent(accountStateEvent: AccountStateEvent) {
        viewModelScope.launch {
            when (accountStateEvent) {
                is AccountStateEvent.LoadProfile -> {
                    cacheRepository.getUserDetails()
                        .onEach { dataState ->
                            when (dataState) {
                                is Resource.Success<List<LoginModel>> -> {
                                    _dataState.value = dataState
                                }
                                is Resource.Error -> {
                                    _errorState.value = dataState
                                }
                            }
                        }.launchIn(viewModelScope)
                }
                is AccountStateEvent.LoadToken -> {
                    dataStoreManager.authToken().onEach { dataState ->
                        _token.value = dataState
                    }.launchIn(viewModelScope)
                }
                is AccountStateEvent.Logout -> {
                    dataStoreManager.clear()
                    viewModelScope.launch {
                        cacheRepository.delete()
                    }
                }
            }
        }
    }

}

sealed class AccountStateEvent {

    object LoadProfile : AccountStateEvent()

    object LoadToken : AccountStateEvent()

    object NavigateToLoginActivity : AccountStateEvent()

    object NavigateToOrderHistory : AccountStateEvent()

    object NavigateToShippingAddress : AccountStateEvent()

    object NavigateToOrderStatus : AccountStateEvent()

    object Logout : AccountStateEvent()
}