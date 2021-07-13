package com.example.bunonbasket.data.models.partners

import com.example.bunonbasket.utils.Constants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PartnerModel(

    @SerializedName("created_at")
    @Expose
    val created_at: String,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("image")
    @Expose
    val image: String,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("updated_at")
    @Expose
    val updated_at: String,

    @SerializedName("url")
    @Expose
    val url: Any
) {
    var fullImageUrl: String? = ""
        get() = "${Constants.BASE_URL}/$image"
        set(value) {
            field = value
        }
}