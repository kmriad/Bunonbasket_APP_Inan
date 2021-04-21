package com.example.bunonbasket.di

import com.example.bunonbasket.data.remote.BunonRetrofit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by inan on 21/4/21
 */
object RetrofitModule {
    @Module
    @InstallIn(SingletonComponent::class)
    object RetrofitModule {

        @Singleton
        @Provides
        fun provideGsonBuilder(): Gson {
            return GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create()
        }

        @Singleton
        @Provides
        fun provideRetrofit(gson: Gson): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl("https://bunonbasket.com/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
        }

        @Singleton
        @Provides
        fun provideBlogService(retrofit: Retrofit.Builder): BunonRetrofit {
            return retrofit
                .build()
                .create(BunonRetrofit::class.java)
        }
    }
}