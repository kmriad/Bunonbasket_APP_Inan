package com.example.bunonbasket.ui.component.home.products

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.data.repository.remote.PaginatedDataSource
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by inan on 17/5/21
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val state: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    lateinit var products: Flow<PagingData<Product>>


    fun fetchEvent(query: String) {
        Log.d("ProductListActivity", query)
        try {
            products = fetchAllProducts(query)
                .map { pagingData ->
                    Log.d("ProductListActivity",pagingData.toString())
                    pagingData.map {
                        Log.d("ProductListActivity",it.name)
                        it }
                }
        } catch (e: Exception) {
            Log.d("ProductListActivity", e.toString())
        }
    }

    private fun fetchAllProducts(query: String) =
        Pager(
            config = PagingConfig(
                5
            ),
            pagingSourceFactory = { PaginatedDataSource(remoteRepository, query) }
        ).flow
}