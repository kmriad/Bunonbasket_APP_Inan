package com.example.bunonbasket.data.repository.remote

import android.util.Log
import com.example.bunonbasket.data.models.LoginModel
import com.example.bunonbasket.data.models.banner.Banner
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.base.BasePaginatedModel
import com.example.bunonbasket.data.models.brands.Brand
import com.example.bunonbasket.data.models.cart.CartListModel
import com.example.bunonbasket.data.models.cart.CartModel
import com.example.bunonbasket.data.models.cart.QuantityUpdateModel
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
                Log.d("ProductDetails", productDetails.results.name)
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

    override suspend fun loginUser(
        phone: String?,
        password: String?
    ): Flow<Resource<BaseDetailsModel<LoginModel>>> = flow {
        emit(Resource.Loading)
        try {

            val loginModel =
                bunonRetrofit.loginUser(phone.toString().trim(), password.toString().trim())
            Log.d("RemoteRepository", loginModel.message)
            emit(Resource.Success(loginModel))
        } catch (e: HttpException) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun registerUser(
        name: String,
        phone: String,
        password: String,
        userType: String
    ): Flow<Resource<BaseDetailsModel<LoginModel>>> = flow {
        emit(Resource.Loading)
        try {
            val registrationModel = bunonRetrofit.registerUser(
                name = name,
                phone = phone,
                password = password,
                userType = userType
            )
            emit(Resource.Success(registrationModel))
        } catch (e: Exception) {
            Log.d("RemoteRepository", e.message.toString())
            emit(Resource.Error(e))
        }
    }

    override suspend fun addToCart(
        productId: String,
        quantity: Int,
        token: String
    ): Flow<Resource<BaseDetailsModel<CartModel>>> = flow {
        emit(Resource.Loading)
        try {
            val cartModel = bunonRetrofit.addToCart(productId, quantity, "Bearer $token")
            emit(Resource.Success(cartModel))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun fetchCart(token: String): Flow<Resource<BaseModel<CartListModel>>> = flow {
        emit(Resource.Loading)
        try {
            val cartListModel = bunonRetrofit.fetchCarts(authHeader = "Bearer $token")
            emit(Resource.Success(cartListModel))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun updateQuantity(
        cartId: Int,
        quantity: Int,
        token: String
    ): Flow<Resource<BaseDetailsModel<QuantityUpdateModel>>> = flow {
        try {
            val quantityUpdateModel =
                bunonRetrofit.updateQuantity(
                    cartId = cartId,
                    quantity = quantity,
                    authHeader = "Bearer $token"
                )
            emit(Resource.Success(quantityUpdateModel))
        } catch (e: Exception) {
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
}
