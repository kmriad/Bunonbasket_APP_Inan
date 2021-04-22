package com.example.bunonbasket.data.repository.cache

import com.example.bunonbasket.data.local.cache.LocalData
import com.example.bunonbasket.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by inan on 15/4/21
 */
class CacheRepository @Inject constructor(
    private val localData: LocalData,
) : CacheRepositorySource {

    override suspend fun saveAppIntro(): Flow<Resource<Boolean>> {
        return flow {
            emit(localData.saveAppIntro(true))
        }
    }

    override suspend fun loadAppIntro(): Flow<Resource<Boolean>> {
        return flow {
            emit(localData.isAppIntro())
        }
    }

    override suspend fun saveShowCase(): Flow<Resource<Boolean>> {
        return flow {
            emit(localData.saveShowCase(true))
        }
    }

    override suspend fun loadShowCase(): Flow<Resource<Boolean>> {
        return flow {
            emit(localData.isShowCased())
        }
    }
}