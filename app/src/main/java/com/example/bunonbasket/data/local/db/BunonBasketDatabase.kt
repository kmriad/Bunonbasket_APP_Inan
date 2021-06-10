package com.example.bunonbasket.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bunonbasket.data.models.LoginModel

/**
 * Created by inan on 10/6/21
 */
@Database(entities = [LoginModel::class], version = 1)
abstract class BunonBasketDatabase : RoomDatabase() {
    abstract fun bunonBasketDao(): BunonBasketDao

    companion object {
        val DATABASE_NAME: String = "bunonbasket_db"
    }
}