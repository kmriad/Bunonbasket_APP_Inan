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
    private val remoteRepository: RemoteRepository,
    private val subcategoryId: String
) :
    PagingSource<Int, Product>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {

        return try {
            val nextPage = params.key ?: 1
            val response = remoteRepository.fetchAllProducts(subcategoryId, nextPage, 10)

            LoadResult.Page(
                data = response.results.data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < response.results.total)
                    nextPage.plus(1) else null
            )
        } catch (e: Exception) {
            Log.d("ProductListActivity",e.toString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int = 1
}