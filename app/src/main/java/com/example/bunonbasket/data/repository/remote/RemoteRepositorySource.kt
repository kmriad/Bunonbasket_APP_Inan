package com.example.bunonbasket.data.repository.remote

import com.example.bunonbasket.data.models.banner.BannerModel
import com.example.bunonbasket.data.models.brands.BrandModel
import com.example.bunonbasket.data.models.category.CategoryModel
import com.example.bunonbasket.data.models.home.HomeModel
import com.example.bunonbasket.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by inan on 21/4/21
 */
interface RemoteRepositorySource {
    suspend fun fetchBanners(): Flow<Resource<BannerModel>>
    suspend fun fetchCategories(): Flow<Resource<CategoryModel>>
    suspend fun fetchBrands(): Flow<Resource<BrandModel>>
    suspend fun fetchFeaturedProducts():Flow<Resource<HomeModel>>
    suspend fun fetchBestSellingProducts(): Flow<Resource<HomeModel>>
}