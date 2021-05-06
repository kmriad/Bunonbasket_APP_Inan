package com.example.bunonbasket.data.repository.remote

import android.util.Log
import com.example.bunonbasket.data.models.banner.Banner
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.base.BasePaginatedModel
import com.example.bunonbasket.data.models.brands.Brand
import com.example.bunonbasket.data.models.category.*
import com.example.bunonbasket.data.remote.BunonRetrofit
import com.example.bunonbasket.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Created by inan on 21/4/21
 */
class RemoteRepository @Inject constructor(
    private val bunonRetrofit: BunonRetrofit,
) : RemoteRepositorySource {
    override suspend fun fetchBanners(): Flow<Resource<BaseModel<Banner>>> = flow {
        emit(Resource.Loading)
        try {
            val banners = bunonRetrofit.fetchBanners()
            emit(Resource.Success(banners))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun fetchCategories(): Flow<Resource<BaseModel<Category>>> = flow {
        emit(Resource.Loading)
        delay(1000)
        try {
            val categories = bunonRetrofit.fetchCategories()
            emit(Resource.Success(categories))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun fetchBrands(): Flow<Resource<BaseModel<Brand>>> = flow {
        emit(Resource.Loading)
        delay(1000)
        try {
            val brands = bunonRetrofit.fetchBrands()
            emit(Resource.Success(brands))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun fetchFeaturedProducts(): Flow<Resource<BaseModel<Product>>> = flow {
        emit(Resource.Loading)
        try {
            val products = bunonRetrofit.fetchFeaturedProducts()
            emit(Resource.Success(products))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun fetchBestSellingProducts(): Flow<Resource<BaseModel<Product>>> = flow {
        emit(Resource.Loading)
        try {
            val products = bunonRetrofit.fetchBestSellingProducts()
            emit(Resource.Success(products))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun fetchSubCategories(id: String): Flow<Resource<BaseModel<SubCategory>>> =
        flow {
            emit(Resource.Loading)
            try {
                val subCategories = bunonRetrofit.fetchSubCategories(categoryId = id)
                emit(Resource.Success(subCategories))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }

    override suspend fun fetchProductBySubCategories(
        id: String,
        page: Int,
        perPage: Int
    ): Flow<Resource<BasePaginatedModel<PaginatedModel>>> =
        flow {
            emit(Resource.Loading)
            try {
                val products =
                    bunonRetrofit.fetchProductBySubCategories(subCategoryId = id, page, perPage)
                emit(Resource.Success(products))
            } catch (e: HttpException) {
                Log.d("Error", e.response().toString())
                emit(Resource.Error(e))
            }
        }
}