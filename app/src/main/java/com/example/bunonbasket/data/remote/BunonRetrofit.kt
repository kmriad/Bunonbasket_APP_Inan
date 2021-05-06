package com.example.bunonbasket.data.remote

import com.example.bunonbasket.data.models.banner.Banner
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.base.BasePaginatedModel
import com.example.bunonbasket.data.models.brands.Brand
import com.example.bunonbasket.data.models.category.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by inan on 21/4/21
 */
interface BunonRetrofit {

    @GET("banner")
    suspend fun fetchBanners(): BaseModel<Banner>

    @GET("categories")
    suspend fun fetchCategories(): BaseModel<Category>

    @GET("brands")
    suspend fun fetchBrands(): BaseModel<Brand>

    @GET("feature_products")
    suspend fun fetchFeaturedProducts(): BaseModel<Product>

    @GET("best_selling_products")
    suspend fun fetchBestSellingProducts(): BaseModel<Product>

    @GET("sub_categories/{category_id}")
    suspend fun fetchSubCategories(@Path(value = "category_id") categoryId: String): BaseModel<SubCategory>

    @GET("sub_category/products/{sub_category_id}")
    suspend fun fetchProductBySubCategories(
        @Path(value = "sub_category_id") subCategoryId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): BasePaginatedModel<PaginatedModel>
}