package com.example.bunonbasket.data.remote

import com.example.bunonbasket.data.models.banner.BannerModel
import retrofit2.http.GET

/**
 * Created by inan on 21/4/21
 */
interface BunonRetrofit {

    @GET("banner")
    suspend fun fetchBanners(): BannerModel
}