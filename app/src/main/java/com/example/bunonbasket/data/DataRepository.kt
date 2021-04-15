package com.example.bunonbasket.data

import com.example.bunonbasket.data.local.cache.LocalData
import com.example.bunonbasket.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by inan on 15/4/21
 */
class DataRepository @Inject constructor(
    private val localRepository: LocalData,
) : DataRepositorySource {

    override suspend fun saveAppIntro(): Flow<Resource<Boolean>> {
        return flow {
            emit(localRepository.saveAppIntro(true))
        }
    }

    override suspend fun loadAppIntro(): Flow<Resource<Boolean>> {
        return flow {
            emit(localRepository.isAppIntro())
        }
    }
}