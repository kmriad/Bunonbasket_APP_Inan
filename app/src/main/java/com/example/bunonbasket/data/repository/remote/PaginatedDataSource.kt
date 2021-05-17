package com.example.bunonbasket.data.repository.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.data.remote.BunonRetrofit

/**
 * Created by inan on 11/5/21
 */
class PaginatedDataSource(
    private val bunonRetrofit: BunonRetrofit,
    private val subcategoryId: String
) :
    PagingSource<Int, Product>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = bunonRetrofit.fetchAllProducts(subcategoryId, currentLoadingPageKey, 10)
            Log.d("ProductListActivity",response.data.toString())
            val responseData = mutableListOf<Product>()
            val data = response.data
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            Log.d("ProductListActivity",e.toString());
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int = 1
}