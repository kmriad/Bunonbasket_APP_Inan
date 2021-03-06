package com.example.bunonbasket.data.repository.cache

import android.util.Log
import com.example.bunonbasket.data.local.cache.LocalData
import com.example.bunonbasket.data.local.db.CacheMapper
import com.example.bunonbasket.data.local.db.UserDao
import com.example.bunonbasket.data.models.LoginModel
import com.example.bunonbasket.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by inan on 15/4/21
 */
class CacheRepository @Inject constructor(
    private val localData: LocalData,
    private val userDao: UserDao,
    private val cacheMapper: CacheMapper,
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

    override suspend fun createUser(loginModel: LoginModel): Flow<Resource<Long>> = flow {
        emit(Resource.Loading)
        try {
            val userEntity = cacheMapper.mapToEntity(loginModel)
            val success = userDao.insert(userEntity)
            Log.d("CacheRepository", success.toString())
            emit(Resource.Success(success))
        } catch (e: Exception) {
            Log.d("CacheRepository", e.message.toString())
            emit(Resource.Error(e))
        }
    }

    override suspend fun getUserDetails(): Flow<Resource<List<LoginModel>>> = flow {
        emit(Resource.Loading)
        try {
            val success = userDao.get()
            Log.d("CacheRepository", success.toString())
            val result = cacheMapper.mapFromEntityList(success)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            Log.d("CacheRepository", e.toString())
            emit(Resource.Error(e))
        }
    }

    override suspend fun delete() {
        userDao.delete()
    }

    override suspend fun loadDeviceToken(): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        val token = localData.loadDeviceToken()
        emit(token)
    }
}