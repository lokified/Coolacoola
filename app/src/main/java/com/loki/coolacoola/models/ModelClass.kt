package com.loki.coolacoola.models

import com.google.gson.annotations.SerializedName

data class ModelClass (

    @SerializedName("offset") val offset : Int,
    @SerializedName("number") val number : Int,
    @SerializedName("results") val results : List<Results>,
    @SerializedName("totalResults") val totalResults : Int
    )