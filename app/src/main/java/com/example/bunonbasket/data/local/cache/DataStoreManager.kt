package com.example.bunonbasket.data.local.cache

import android.content.Context
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
        it[PREF_KEY] ?: ""
    }.catch {
        throw it
    }

    override suspend fun saveAuthToken(authToken: String) {
        profileDataStore.edit { preferences ->
            preferences[PREF_KEY] = authToken
        }
    }

    companion object {
        private val PREF_KEY = stringPreferencesKey(Constants.KEY_AUTH)
    }
}