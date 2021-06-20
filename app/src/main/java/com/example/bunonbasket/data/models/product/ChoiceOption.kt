package com.example.bunonbasket.data.models.product

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ChoiceOption(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("options")
    @Expose
    val options: List<String>,
    @SerializedName("title")
    @Expose
    val title: String
) : Serializable