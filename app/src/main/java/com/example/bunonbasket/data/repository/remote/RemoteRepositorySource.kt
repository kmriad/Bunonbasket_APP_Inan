package com.example.bunonbasket.data.repository.remote

import com.example.bunonbasket.data.models.banner.BannerModel
import com.example.bunonbasket.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by inan on 21/4/21
 */
interface RemoteRepositorySource {
    suspend fun fetchBanners(): Flow<Resource<BannerModel>>
}