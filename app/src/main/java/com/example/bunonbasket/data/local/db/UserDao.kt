package com.example.bunonbasket.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by inan on 13/6/21
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity): Long

    @Query("SELECT * FROM user")
    suspend fun get(): List<UserEntity>

    @Query("DELETE FROM user WHERE id = :id")
    fun delete(id: Int?)
}