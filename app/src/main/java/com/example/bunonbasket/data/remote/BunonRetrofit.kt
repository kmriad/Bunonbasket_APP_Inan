package com.example.bunonbasket.data.remote

import com.example.bunonbasket.data.models.banner.BannerModel
import com.example.bunonbasket.data.models.brands.BrandModel
import com.example.bunonbasket.data.models.category.CategoryModel
import com.example.bunonbasket.data.models.home.HomeModel
import retrofit2.http.GET

/**
 * Created by inan on 21/4/21
 */
interface BunonRetrofit {

    @GET("banner")
    suspend fun fetchBanners(): BannerModel

    @GET("categories")
    suspend fun fetchCategories(): CategoryModel

    @GET("brands")
    suspend fun fetchBrands(): BrandModel

    @GET("feature_products")
    suspend fun fetchFeaturedProducts(): HomeModel

    @GET("best_selling_products")
    suspend fun fetchBestSellingProducts(): HomeModel
}