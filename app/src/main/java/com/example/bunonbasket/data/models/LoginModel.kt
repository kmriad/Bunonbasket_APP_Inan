package com.example.bunonbasket.data.models

/**
 * Created by inan on 7/6/21
 */
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("address")
    @Expose
    val address: String?,

    @SerializedName("avatar")
    @Expose
    val avatar: String?,

    @SerializedName("avatar_original")
    @Expose
    val avatar_original: String?,

    @SerializedName("balance")
    @Expose
    val balance: Int?,

    @SerializedName("city")
    @Expose
    val city: String?,

    @SerializedName("country")
    @Expose
    val country: String?,

    @SerializedName("created_at")
    @Expose
    val created_at: String?,

    @SerializedName("email")
    @Expose
    val email: String?,

    @SerializedName("email_verified_at")
    @Expose
    val email_verified_at: String?,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String?,

    @SerializedName("phone")
    @Expose
    val phone: String?,

    @SerializedName("postal_code")
    @Expose
    val postal_code: String?,

    @SerializedName("provider_id")
    @Expose
    val provider_id: String?,

    @SerializedName("token")
    @Expose
    val token: String,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String?,

    @SerializedName("user_type")
    @Expose
    val user_type: String?
)
