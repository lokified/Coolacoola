package com.loki.coolacoola.data.models

import com.google.gson.annotations.SerializedName

data class RecipeResponse (

    @SerializedName("offset") val offset : Int,
    @SerializedName("number") val number : Int,
    @SerializedName("results") val results : List<Recipes>,
    @SerializedName("totalResults") val totalResults : Int
    )