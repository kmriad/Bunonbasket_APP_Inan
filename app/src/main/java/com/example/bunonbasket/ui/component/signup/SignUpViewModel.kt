package com.example.bunonbasket.ui.component.signup

import android.util.Log
import androidx.lifecycle.*
import com.example.bunonbasket.data.models.LoginModel
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.repository.cache.CacheRepository
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import com.example.bunonbasket.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by inan on 16/6/21
 */

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val cacheRepository: CacheRepository,
) : ViewModel() {

    val userPhoneNumber: MutableLiveData<String> = MutableLiveData()
    val userPhoneNumberValidator = MediatorLiveData<Boolean>()

    private val _loginState: MutableLiveData<Resource<BaseDetailsModel<LoginModel>>> =
        MutableLiveData()

    private val _saveUserState: MutableLiveData<Resource<Long>> = MutableLiveData()

    val loginState: LiveData<Resource<BaseDetailsModel<LoginModel>>>
        get() = _loginState

    val saveUserState: LiveData<Resource<Long>>
        get() = _saveUserState

    private val signUpTaskEventChannel = Channel<SignupStateEvent>()
    val signUpEvent = signUpTaskEventChannel.receiveAsFlow()

    fun addSourceToPhoneNumber() {
        userPhoneNumberValidator.addSource(userPhoneNumber) { validateForm() }
    }

    private fun validateForm() {
        if (userPhoneNumber.value?.length!! >= 11) {
            userPhoneNumberValidator.value = true
        }
    }

    fun onPhoneNumberChanged(text: CharSequence?) {
        Log.d("InputNumberFragment", text.toString())
        userPhoneNumber.value = text.toString().trim()
    }

    fun signUpButtonClicked() = viewModelScope.launch {
        signUpTaskEventChannel.send(SignupStateEvent.NavigateToSignUpFragment(userPhoneNumber.value!!))
    }

    fun setStateEvent(stateEvent: SignupStateEvent) {
        viewModelScope.launch {
            when (stateEvent) {
                is SignupStateEvent.CreateUserEvent -> {
                    remoteRepository.registerUser(
                        name = stateEvent.name,
                        password = stateEvent.password,
                        phone = stateEvent.phoneNumber,
                        userType = "customer"
                    ).onEach { dataState ->
                        _loginState.value = dataState
                    }.launchIn(viewModelScope)
                }

                is SignupStateEvent.SaveUserProfile -> {
                    cacheRepository.createUser(stateEvent.loginModel)
                        .onEach { dataState ->
                            _saveUserState.value = dataState
                        }.launchIn(viewModelScope)

                }
            }
        }
    }

    fun saveUserProfile(loginModel: LoginModel) {
        setStateEvent(SignupStateEvent.SaveUserProfile(loginModel))
    }

    fun registerUser(phoneNumber: String, name: String, password: String) {
        setStateEvent(
            SignupStateEvent.CreateUserEvent(
                name = name,
                phoneNumber = phoneNumber,
                password = password,
            )
        )
    }
}

sealed class SignupStateEvent {

    object NavigateToInputNumberFragment : SignupStateEvent()

    data class NavigateToSignUpFragment(val phoneNumber: String) : SignupStateEvent()

    data class SaveUserProfile(val loginModel: LoginModel) : SignupStateEvent()

    data class CreateUserEvent(val name: String, val phoneNumber: String, val password: String) :
        SignupStateEvent()
}