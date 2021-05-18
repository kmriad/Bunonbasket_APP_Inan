package com.example.bunonbasket.data.repository.remote

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.bunonbasket.data.models.banner.Banner
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.base.BasePaginatedModel
import com.example.bunonbasket.data.models.brands.Brand
import com.example.bunonbasket.data.models.category.Category
import com.example.bunonbasket.data.models.category.PaginatedModel
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.data.models.category.SubCategory
import com.example.bunonbasket.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by inan on 21/4/21
 */
interface RemoteRepositorySource {
    suspend fun fetchBanners(): Flow<Resource<BaseModel<Banner>>>
    suspend fun fetchCategories(): Flow<Resource<BaseModel<Category>>>
    suspend fun fetchBrands(): Flow<Resource<BaseModel<Brand>>>
    suspend fun fetchFeaturedProducts(): Flow<Resource<BaseModel<Product>>>
    suspend fun fetchBestSellingProducts(): Flow<Resource<BaseModel<Product>>>
    suspend fun fetchSubCategories(id: String): Flow<Resource<BaseModel<SubCategory>>>
    suspend fun fetchProductBySubCategories(
        id: String,
        page: Int,
        perPage: Int
    ): Flow<Resource<BasePaginatedModel<PaginatedModel>>>

   suspend  fun fetchAllProducts(
        query:String,
        page: Int,
        perPage: Int
    ): BasePaginatedModel<PaginatedModel>
}