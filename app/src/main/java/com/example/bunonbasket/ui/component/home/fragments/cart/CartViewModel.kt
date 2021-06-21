package com.example.bunonbasket.ui.component.home.fragments.cart

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.repository.cache.CacheRepository
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by inan on 21/6/21
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class CartViewModel(
    private val remoteRepository: RemoteRepository,
    private val cacheRepository: CacheRepository,
    private val dataStoreManager: DataStoreManager,
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {

}