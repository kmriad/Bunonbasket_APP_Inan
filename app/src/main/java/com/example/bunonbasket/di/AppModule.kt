package com.example.bunonbasket.di

import android.content.Context
import androidx.room.Room
import com.example.bunonbasket.data.local.cache.DataStoreManager
import com.example.bunonbasket.data.local.cache.LocalData
import com.example.bunonbasket.data.local.db.UserDao
import com.example.bunonbasket.data.local.db.UserDatabase
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

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext appContext: Context): DataStoreManager =
        DataStoreManager(appContext)


    @Singleton
    @Provides
    fun provideBlogDb(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            UserDatabase.DATABASE_NAME,
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBlogDao(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao()
    }
}