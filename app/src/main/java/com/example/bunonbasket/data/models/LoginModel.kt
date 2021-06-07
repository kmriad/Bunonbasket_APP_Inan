package com.example.bunonbasket.data.models

/**
 * Created by inan on 7/6/21
 */
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("address")
    @Expose
    val address: Any,

    @SerializedName("avatar")
    @Expose
    val avatar: Any,

    @SerializedName("avatar_original")
    @Expose
    val avatar_original: Any,

    @SerializedName("balance")
    @Expose
    val balance: Int,

    @SerializedName("city")
    @Expose
    val city: Any,

    @SerializedName("country")
    @Expose
    val country: Any,

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("email")
    @Expose
    val email: String,

    @SerializedName("email_verified_at")
    @Expose
    val email_verified_at: String,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("phone")
    @Expose
    val phone: Any,

    @SerializedName("postal_code")
    @Expose
    val postal_code: Any,

    @SerializedName("provider_id")
    @Expose
    val provider_id: Any,

    @SerializedName("token")
    @Expose
    val token: String,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String,

    @SerializedName("user_type")
    @Expose
    val user_type: String
)
