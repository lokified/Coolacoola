package com.loki.coolacoola.data.models

import com.google.gson.annotations.SerializedName

data class RecipeInfo (

    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("image") val image : String,
    @SerializedName("servings") val servings : Int,
    @SerializedName("readyInMinutes") val readyInMinutes : Int,
    @SerializedName("sourceName") val sourceName : String,
    @SerializedName("sourceUrl") val sourceUrl : String,
    @SerializedName("instructions") val instructions : String,
    @SerializedName("extendedIngredients") val extendedIngredients : List<Ingredients>

    )