package com.example.bunonbasket.data

import com.example.bunonbasket.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by inan on 15/4/21
 */
interface DataRepositorySource {
    suspend fun  saveAppIntro():Flow<Resource<Boolean>>
    suspend fun  loadAppIntro():Flow<Resource<Boolean>>
}