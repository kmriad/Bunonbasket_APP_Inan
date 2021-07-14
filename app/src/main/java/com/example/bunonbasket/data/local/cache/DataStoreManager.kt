package com.example.bunonbasket.data.local.cache

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.bunonbasket.utils.Constants
import com.example.bunonbasket.utils.helpers.PreferenceStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by inan on 10/6/21
 */

val Context.dataStore by preferencesDataStore("token")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) :
    PreferenceStorage {

    private val profileDataStore = appContext.dataStore
    override fun authToken(): Flow<String> = profileDataStore.data.map {
        Log.d("CartFragment", it[PREF_KEY].toString())
        it[PREF_KEY] ?: ""
    }.catch {
        Log.d("CartFragment", it.message!!)
        throw it
    }

    override suspend fun saveAuthToken(authToken: String) {
        profileDataStore.edit { preferences ->
            preferences[PREF_KEY] = authToken
        }
    }

    override suspend fun saveDeviceToken(deviceToken: String) {
        profileDataStore.edit { preferences ->
            preferences[DEVICE_KEY] = deviceToken
        }
    }

    override suspend fun clear() {
        profileDataStore.edit { preferences ->
            preferences.remove(PREF_KEY)
        }
    }

    companion object {
        private val PREF_KEY = stringPreferencesKey(Constants.KEY_AUTH)
        private val DEVICE_KEY = stringPreferencesKey(Constants.DEVICE_AUTH)
    }
}