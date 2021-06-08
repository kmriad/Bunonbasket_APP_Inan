package com.example.bunonbasket.ui.component.home.fragments.account

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
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
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val taskEventChannel = Channel<AccountStateEvent>()
    val accountStateEvent = taskEventChannel.receiveAsFlow()


    fun onLoginButtonClicked() = viewModelScope.launch {
        taskEventChannel.send(AccountStateEvent.NavigateToLoginActivity)
    }
}

sealed class AccountStateEvent {

    object NavigateToLoginActivity : AccountStateEvent()
}