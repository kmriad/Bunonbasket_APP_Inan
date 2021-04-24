package com.example.bunonbasket.di

import android.content.Context
import com.example.bunonbasket.data.local.cache.LocalData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by inan on 15/4/21
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalRepository(@ApplicationContext context: Context): LocalData {
        return LocalData(context)
    }
}