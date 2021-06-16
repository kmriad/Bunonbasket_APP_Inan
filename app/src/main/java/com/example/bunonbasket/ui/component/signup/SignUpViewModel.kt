package com.example.bunonbasket.ui.component.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by inan on 16/6/21
 */

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private val signUpTaskEventChannel = Channel<SignupStateEvent>()
    val signUpEvent = signUpTaskEventChannel.receiveAsFlow()

    fun onInputNumberEntered(phoneNumber: String) = viewModelScope.launch {
        signUpTaskEventChannel.send(SignupStateEvent.NavigateToSignUpFragment(phoneNumber))
    }
}

sealed class SignupStateEvent {

    object NavigateToInputNumberFragment : SignupStateEvent()

    data class NavigateToSignUpFragment(val phoneNumber: String) : SignupStateEvent()
}