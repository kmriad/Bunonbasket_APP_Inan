package com.example.bunonbasket.ui.component.home.fragments.account

import androidx.lifecycle.*
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
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val taskEventChannel = Channel<AccountStateEvent>()
    val accountStateEvent = taskEventChannel.receiveAsFlow()

    private val _dataState: MutableLiveData<Resource<List<LoginModel>>> = MutableLiveData()


    val dataState: LiveData<Resource<List<LoginModel>>>
        get() = _dataState

    fun onLoginButtonClicked() = viewModelScope.launch {
        taskEventChannel.send(AccountStateEvent.NavigateToLoginActivity)
    }


}

sealed class AccountStateEvent {
    object NavigateToLoginActivity : AccountStateEvent()
}