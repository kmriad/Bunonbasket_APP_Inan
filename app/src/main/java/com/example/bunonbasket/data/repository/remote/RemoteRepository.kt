package com.example.bunonbasket.data.repository.remote

import android.util.Log
import com.example.bunonbasket.data.models.LoginModel
import com.example.bunonbasket.data.models.banner.Banner
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.base.BasePaginatedModel
import com.example.bunonbasket.data.models.brands.Brand
import com.example.bunonbasket.data.models.category.Category
import com.example.bunonbasket.data.models.category.PaginatedModel
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.data.models.category.SubCategory
import com.example.bunonbasket.data.models.product.ProductDetails
import com.example.bunonbasket.data.remote.BunonRetrofit
import com.example.bunonbasket.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
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

    override suspend fun fetchProductDetails(id: String): Flow<Resource<BaseDetailsModel<ProductDetails>>> =
        flow {
            emit(Resource.Loading)
            try {
                val productDetails = bunonRetrofit.fetchProductDetails(productId = id)
                emit(Resource.Success(productDetails))
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

    override suspend fun fetchAllProducts(
        query: String,
        page: Int,
        perPage: Int
    ): BasePaginatedModel<PaginatedModel> {
        try {
            val products = bunonRetrofit.fetchAllProducts(query, page, perPage)
            return products
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun loginUser(email: String, password: String): BaseDetailsModel<LoginModel> {
        try {
            val jsonObject = JSONObject()
            jsonObject.put("email", email)
            jsonObject.put("password", password)
            val jsonObjectString = jsonObject.toString()
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            val loginModel = bunonRetrofit.loginUser(requestBody)
            return loginModel
        } catch (e: Exception) {
            throw e
        }
    }
}