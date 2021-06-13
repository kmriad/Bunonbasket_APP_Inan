package com.example.bunonbasket.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by inan on 13/6/21
 */
@Entity(tableName = "user")
data class UserEntity(

    @ColumnInfo(name = "address")
    val address: String?,

    @ColumnInfo(name = "avatar")
    val avatar: String?,

    @ColumnInfo(name = "avatar_original")
    val avatar_original: String?,

    @ColumnInfo(name = "balance")
    val balance: Int,

    @ColumnInfo(name = "city")
    val city: String?,

    @ColumnInfo(name = "country")
    val country: String?,

    @ColumnInfo(name = "created_at")
    val created_at: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "email_verified_at")
    val email_verified_at: String,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "phone")
    val phone: String?,

    @ColumnInfo(name = "postal_code")
    val postal_code: String?,

    @ColumnInfo(name = "provider_id")
    val provider_id: String?,

    @ColumnInfo(name = "token")
    val token: String,

    @ColumnInfo(name = "updated_at")
    val updated_at: String,

    @ColumnInfo(name = "user_type")
    val user_type: String
) {

}