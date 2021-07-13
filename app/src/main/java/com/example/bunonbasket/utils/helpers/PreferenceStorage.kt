package com.example.bunonbasket.utils.helpers

import kotlinx.coroutines.flow.Flow

/**
 * Created by inan on 10/6/21
 */
interface PreferenceStorage {
    fun authToken(): Flow<String>
    suspend fun saveAuthToken(authToken: String)
    suspend fun clear()
}