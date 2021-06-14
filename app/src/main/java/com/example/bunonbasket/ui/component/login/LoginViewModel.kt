package com.example.bunonbasket.ui.component.login

import androidx.lifecycle.*
import com.example.bunonbasket.data.models.LoginModel
import com.example.bunonbasket.data.models.base.BaseDetailsModel
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
 * Created by inan on 9/6/21
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val cacheRepository: CacheRepository,
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val _loginState: MutableLiveData<Resource<BaseDetailsModel<LoginModel>>> =
        MutableLiveData()

    private val _saveUserState: MutableLiveData<Resource<Long>> = MutableLiveData()

    val PhoneNumber: MutableLiveData<String> = MutableLiveData()
    val Password: MutableLiveData<String> = MutableLiveData()

    val mPhonePasswordValidator = MediatorLiveData<Boolean>()

    val saveUserState: LiveData<Resource<Long>>
        get() = _saveUserState

    init {
        mPhonePasswordValidator.addSource(PhoneNumber) { validateForm() }
        mPhonePasswordValidator.addSource(Password) { validateForm() }
    }

    private fun validateForm() {
        if (PhoneNumber.value?.length!! >= 11) {
            mPhonePasswordValidator.value = true
        }
    }

    fun onPhoneNumberChanged(text: CharSequence?) {
        PhoneNumber.value = text.toString().trim()
    }

    fun onPasswordChanged(text: CharSequence?) {
        Password.value = text.toString().trim()
    }

    override fun onCleared() {
        mPhonePasswordValidator.removeSource(PhoneNumber)
        mPhonePasswordValidator.removeSource(Password)
    }

    val loginState: LiveData<Resource<BaseDetailsModel<LoginModel>>>
        get() = _loginState


    fun setStateEvent(loginStateEvent: LoginStateEvent) {
        viewModelScope.launch {
            when (loginStateEvent) {
                is LoginStateEvent.LoginUser -> {
                    remoteRepository.loginUser(
                        loginStateEvent.phone,
                        loginStateEvent.password
                    )
                        .onEach { dataState ->
                            _loginState.value = dataState
                        }.launchIn(viewModelScope)
                }
                is LoginStateEvent.SaveUserProfile -> {
                    cacheRepository.createUser(loginStateEvent.loginModel)
                        .onEach { dataState ->
                            _saveUserState.value = dataState
                        }.launchIn(viewModelScope)

                }
                is LoginStateEvent.None -> {

                }
            }
        }
    }


    fun loginButtonClicked() {
        setStateEvent(LoginStateEvent.LoginUser(PhoneNumber.value, Password.value))
    }

    fun saveUserProfile(loginModel: LoginModel) {
        setStateEvent(LoginStateEvent.SaveUserProfile(loginModel))
    }
}


sealed class LoginStateEvent {
    data class LoginUser(val phone: String?, val password: String?) : LoginStateEvent()

    data class SaveUserProfile(val loginModel: LoginModel) : LoginStateEvent()

    object None : LoginStateEvent()
}