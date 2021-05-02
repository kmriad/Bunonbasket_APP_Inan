package com.example.bunonbasket.data.repository.remote

import com.example.bunonbasket.data.models.banner.BannerModel
import com.example.bunonbasket.data.models.brands.BrandModel
import com.example.bunonbasket.data.models.category.CategoryModel
import com.example.bunonbasket.data.models.category.SubCategoryModel
import com.example.bunonbasket.data.models.home.HomeModel
import com.example.bunonbasket.data.remote.BunonRetrofit
import com.example.bunonbasket.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by inan on 21/4/21
 */
class RemoteRepository @Inject constructor(
    private val bunonRetrofit: BunonRetrofit,
) : RemoteRepositorySource {
    override suspend fun fetchBanners(): Flow<Resource<BannerModel>> = flow {
        emit(Resource.Loading)
        try {
            val banners = bunonRetrofit.fetchBanners()
            emit(Resource.Success(banners))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun fetchCategories(): Flow<Resource<CategoryModel>> = flow {
        emit(Resource.Loading)
        delay(1000)
        try {
            val categories = bunonRetrofit.fetchCategories()
            emit(Resource.Success(categories))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun fetchBrands(): Flow<Resource<BrandModel>> = flow {
        emit(Resource.Loading)
        delay(1000)
        try {
            val brands = bunonRetrofit.fetchBrands()
            emit(Resource.Success(brands))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun fetchFeaturedProducts(): Flow<Resource<HomeModel>> = flow {
        emit(Resource.Loading)
        try {
            val products = bunonRetrofit.fetchFeaturedProducts()
            emit(Resource.Success(products))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun fetchBestSellingProducts(): Flow<Resource<HomeModel>> = flow {
        emit(Resource.Loading)
        try {
            val products = bunonRetrofit.fetchBestSellingProducts()
            emit(Resource.Success(products))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun fetchSubCategories(id: String): Flow<Resource<SubCategoryModel>> = flow {
        emit(Resource.Loading)
        try {
            val subCategories = bunonRetrofit.fetchSubCategories(categoryId = id)
            emit(Resource.Success(subCategories))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}