package com.example.bunonbasket.ui.component.home

import androidx.lifecycle.*
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.wishlist.WishListModel
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
class WishListViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val remoteRepository: RemoteRepository,
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val _token: MutableLiveData<String> = MutableLiveData()

    private val _wishlistDataState: MutableLiveData<Resource<BaseModel<WishListModel>>> =
        MutableLiveData()
    val wishlistDataState: LiveData<Resource<BaseModel<WishListModel>>>
        get() = _wishlistDataState


    val token: LiveData<String>
        get() = _token

    init {
        fetchWishList(WishListStateEvent.LoadToken)
    }

    fun fetchWishList(wishListStateEvent: WishListStateEvent) {
        viewModelScope.launch {
            when (wishListStateEvent) {
                is WishListStateEvent.FetchWishList -> {
                    remoteRepository.fetchWishList(_token.value!!)
                        .onEach { dataState ->
                            _wishlistDataState.value = dataState
                        }.launchIn(viewModelScope)
                }

                is WishListStateEvent.LoadToken -> {
                    dataStoreManager.authToken().onEach { dataState ->
                        _token.value = dataState
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class WishListStateEvent {

    object FetchWishList : WishListStateEvent()

    object LoadToken : WishListStateEvent()

}