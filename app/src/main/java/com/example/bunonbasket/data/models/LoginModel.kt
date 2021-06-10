package com.example.bunonbasket.data.models

/**
 * Created by inan on 7/6/21
 */
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "User")
data class LoginModel(
    @SerializedName("address")
    @Expose
    @ColumnInfo(name = "address")
    val address: Any,

    @SerializedName("avatar")
    @Expose
    @ColumnInfo(name = "avatar")
    val avatar: Any,

    @SerializedName("avatar_original")
    @Expose
    @ColumnInfo(name = "avatar_original")
    val avatar_original: Any,

    @SerializedName("balance")
    @Expose
    @ColumnInfo(name = "balance")
    val balance: Int,

    @SerializedName("city")
    @Expose
    @ColumnInfo(name = "city")
    val city: Any,

    @SerializedName("country")
    @Expose
    @ColumnInfo(name = "country")
    val country: Any,

    @SerializedName("created_at")
    @Expose
    @ColumnInfo(name = "created_at")
    val created_at: String,

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email")
    val email: String,

    @SerializedName("email_verified_at")
    @Expose
    @ColumnInfo(name = "email_verified_at")
    val email_verified_at: String,

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    val name: String,

    @SerializedName("phone")
    @Expose
    @ColumnInfo(name = "phone")
    val phone: Any,

    @SerializedName("postal_code")
    @Expose
    @ColumnInfo(name = "postal_code")
    val postal_code: Any,

    @SerializedName("provider_id")
    @Expose
    @ColumnInfo(name = "provider_id")
    val provider_id: Any,

    @SerializedName("token")
    @Expose
    @ColumnInfo(name = "token")
    val token: String,

    @SerializedName("updated_at")
    @Expose
    @ColumnInfo(name = "updated_at")
    val updated_at: String,

    @SerializedName("user_type")
    @Expose
    @ColumnInfo(name = "user_type")
    val user_type: String
)
