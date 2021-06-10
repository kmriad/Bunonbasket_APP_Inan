package com.example.bunonbasket.data.local.db

import androidx.room.*
import com.example.bunonbasket.data.models.LoginModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by inan on 10/6/21
 */
@Dao
interface BunonBasketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: LoginModel): Long

    @Transaction
    @Query("SELECT * FROM User")
    fun getSingleUserDetails(): Flow<List<LoginModel>>

    @Delete
    suspend fun deleteUser(user: LoginModel)
}