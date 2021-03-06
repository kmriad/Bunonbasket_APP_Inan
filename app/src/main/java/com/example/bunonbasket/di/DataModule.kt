package com.example.bunonbasket.di

import com.example.bunonbasket.data.local.cache.LocalData
import com.example.bunonbasket.data.local.db.CacheMapper
import com.example.bunonbasket.data.local.db.UserDao
import com.example.bunonbasket.data.remote.BunonRetrofit
import com.example.bunonbasket.data.repository.cache.CacheRepository
import com.example.bunonbasket.data.repository.cache.CacheRepositorySource
import com.example.bunonbasket.data.repository.remote.RemoteRepository
import com.example.bunonbasket.data.repository.remote.RemoteRepositorySource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by inan on 15/4/21
 */

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDataRepository(
        localData: LocalData,
        userDao: UserDao,
        cacheMapper: CacheMapper
    ): CacheRepositorySource {
        return CacheRepository(localData = localData, userDao = userDao, cacheMapper = cacheMapper)
    }

    @Provides
    @Singleton
    fun provideRemoteDataRepository(
        bunonRetrofit: BunonRetrofit,
    ): RemoteRepositorySource {
        return RemoteRepository(bunonRetrofit)
    }

}