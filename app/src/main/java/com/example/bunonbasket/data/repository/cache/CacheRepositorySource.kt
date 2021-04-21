package com.example.bunonbasket.data.repository.cache

import com.example.bunonbasket.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by inan on 15/4/21
 */
interface CacheRepositorySource {

    //app intro and showcaseview
    suspend fun  saveAppIntro():Flow<Resource<Boolean>>
    suspend fun  loadAppIntro():Flow<Resource<Boolean>>
    suspend fun  saveShowCase():Flow<Resource<Boolean>>
    suspend fun  loadShowCase():Flow<Resource<Boolean>>


}