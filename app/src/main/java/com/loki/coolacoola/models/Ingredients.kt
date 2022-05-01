package com.loki.coolacoola.models

import com.google.gson.annotations.SerializedName

data class Ingredients (
    @SerializedName("aisle") val aisle : String,
    @SerializedName("amount") val amount : Double,
    @SerializedName("consitency") val consitency : String,
    @SerializedName("name") val name : String,
    @SerializedName("original") val original : String,
    @SerializedName("image") val image : String

    )