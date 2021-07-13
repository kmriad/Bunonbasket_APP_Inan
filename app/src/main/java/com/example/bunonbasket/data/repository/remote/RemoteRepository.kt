package com.example.bunonbasket.data.repository.remote

import android.util.Log
import com.example.bunonbasket.data.models.LoginModel
import com.example.bunonbasket.data.models.banner.Banner
import com.example.bunonbasket.data.models.base.BaseDetailsModel
import com.example.bunonbasket.data.models.base.BaseModel
import com.example.bunonbasket.data.models.base.BasePaginatedModel
import com.example.bunonbasket.data.models.brands.Brand
import com.example.bunonbasket.data.models.cart.*
import com.example.bunonbasket.data.models.category.Category
import com.example.bunonbasket.data.models.category.PaginatedModel
import com.example.bunonbasket.data.models.category.Product
import com.example.bunonbasket.data.models.category.SubCategory
import com.example.bunonbasket.data.models.checkout.CheckoutModel
import com.example.bunonbasket.data.models.deliverystatus.DeliveryStatusModel
import com.example.bunonbasket.data.models.orders.OrderHistoryModel
import com.example.bunonbasket.data.models.partners.PartnerModel
import com.example.bunonbasket.data.models.product.ProductDetails
import com.example.bunonbasket.data.models.wishlist.PostWishlistModel
import com.example.bunonbasket.data.models.wishlist.WishListModel
import com.example.bunonbasket.data.remote.BunonRetrofit
import com.example.bunonbasket.utils.Resource
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

    override suspend fun fetchCities(token: String): Flow<Resource<BaseModel<CityModel>>> = flow {
        try {
            val cityModel =
                bunonRetrofit.fetchCities(
                    authHeader = "Bearer $token"
                )
            emit(Resource.Success(cityModel))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun fetchAreas(
        token: String,
        areaId: Int
    ): Flow<Resource<BaseModel<AreaModel>>> = flow {
        try {
            val areaModel =
                bunonRetrofit.fetchAreas(
                    authHeader = "Bearer $token",
                    areaId = areaId
                )
            emit(Resource.Success(areaModel))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun addShippingInfo(
        name: String,
        phone: String,
        address: String,
        country: String,
        city: String,
        area: String,
        authHeader: String
    ): Flow<Resource<BaseDetailsModel<ShippingInfo>>> = flow {
        try {
            val areaModel =
                bunonRetrofit.addShippingInfo(
                    name = name,
                    phone = phone,
                    authHeader = "Bearer $authHeader",
                    address = address,
                    country = country,
                    city = city,
                    area = area
                )
            emit(Resource.Success(areaModel))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun fetchShippingInfo(authHeader: String): Flow<Resource<BaseDetailsModel<ShippingInfo>>> =
        flow {
            try {
                val areaModel =
                    bunonRetrofit.fetchShippingInfo("Bearer $authHeader")
                Log.d("RemoteRepository", areaModel.results.name.toString())
                emit(Resource.Success(areaModel))
            } catch (e: Exception) {
                Log.d("RemoteRepository", e.message.toString())
                emit(Resource.Error(e))
            }
        }

    override suspend fun deleteItem(
        cartId: Int,
        authHeader: String
    ): Flow<Resource<BaseDetailsModel<Any?>>> = flow {
        try {
            val data = bunonRetrofit.deleteItem(cartId, "Bearer $authHeader")
            Log.d("RemoteRepository", data.message);
            emit(Resource.Success(data))
        } catch (e: Exception) {
            Log.d("RemoteRepository", e.message.toString());
            emit(Resource.Error(e))
        }
    }

    override suspend fun doCheckout(authHeader: String): Flow<Resource<BaseDetailsModel<CheckoutModel>>> =
        flow {
            try {
                val data = bunonRetrofit.doCheckout("Bearer $authHeader")
                Log.d("RemoteRepository", data.message);
                emit(Resource.Success(data))
            } catch (e: Exception) {
                Log.d("RemoteRepository", e.message.toString());
                emit(Resource.Error(e))
            }
        }

    override suspend fun fetchOrderHistory(authHeader: String): Flow<Resource<BaseModel<OrderHistoryModel>>> =
        flow {
            try {
                val data = bunonRetrofit.fetchAllOrders("Bearer $authHeader");
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }

    override suspend fun fetchDeliveredOrders(authHeader: String): Flow<Resource<BaseModel<OrderHistoryModel>>> =
        flow {
            try {
                val data = bunonRetrofit.fetchDeliveredOrders("Bearer $authHeader");
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }

    override suspend fun fetchCancelledOrders(authHeader: String): Flow<Resource<BaseModel<OrderHistoryModel>>> =
        flow {
            try {
                val data = bunonRetrofit.fetchCancelledOrders("Bearer $authHeader");
                emit(Resource.Success(data))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }

    override suspend fun addToWishList(
        authHeader: String,
        productId: Int
    ): Flow<Resource<BaseDetailsModel<PostWishlistModel>>> = flow {
        try {
            val data = bunonRetrofit.addToWishlist(
                authHeader = "Bearer $authHeader",
                productId = productId
            )
            emit(Resource.Success(data))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun fetchWishList(authHeader: String): Flow<Resource<BaseModel<WishListModel>>> =
        flow {
            try {
                val data = bunonRetrofit.fetchWishList(
                    authHeader = "Bearer $authHeader"
                )
                Log.d("RemoteRepository", data.message)
                emit(Resource.Success(data))
            } catch (e: Exception) {
                Log.d("RemoteRepository", e.message!!)
                emit(Resource.Error(e))
            }
        }

    override suspend fun fetchDeliveryStatus(
        cartId: Int,
        authHeader: String
    ): Flow<Resource<BaseDetailsModel<DeliveryStatusModel>>> =
        flow {
            try {
                val data = bunonRetrofit.fetchDeliveryStatus(
                    cartId = cartId,
                    authHeader = "Bearer $authHeader"
                )
                Log.d("RemoteRepository", data.message)
                emit(Resource.Success(data))
            } catch (e: Exception) {
                Log.d("RemoteRepository", e.message!!)
                emit(Resource.Error(e))
            }
        }

    override suspend fun fetchPartners(): Flow<Resource<BaseModel<PartnerModel>>> = flow {
        try {
            val data = bunonRetrofit.fetchPartners()
            emit(Resource.Success(data))
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
