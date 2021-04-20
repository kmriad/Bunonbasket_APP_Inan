package com.example.bunonbasket.data.local.cache

import android.content.Context
import android.content.SharedPreferences
import com.example.bunonbasket.utils.Constants.Companion.APP_INTRO
import com.example.bunonbasket.utils.Constants.Companion.SHARED_PREF
import com.example.bunonbasket.utils.Constants.Companion.SHOWCASE_ID
import com.example.bunonbasket.utils.Resource
import javax.inject.Inject

/**
 * Created by inan on 15/4/21
 */
class LocalData @Inject constructor(
    private val context: Context
) {
    fun saveAppIntro(flag: Boolean): Resource<Boolean> {
        val sharedPref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(APP_INTRO, flag)
        editor.apply()
        val isSuccess = editor.commit()
        return Resource.Success(isSuccess)
    }

    fun isAppIntro(): Resource<Boolean> {
        val sharedPref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val cache = sharedPref.getBoolean(APP_INTRO, false)
        return Resource.Success(cache)
    }

    fun saveShowCase(flag: Boolean): Resource<Boolean> {
        val sharedPref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(SHOWCASE_ID, flag)
        editor.apply()
        val isSuccess = editor.commit()
        return Resource.Success(isSuccess)
    }

    fun isShowCased(): Resource<Boolean> {
        val sharedPref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val cache = sharedPref.getBoolean(SHOWCASE_ID, false)
        return Resource.Success(cache)
    }
}