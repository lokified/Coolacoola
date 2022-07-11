package com.loki.coolacoola.data.models

import com.google.gson.annotations.SerializedName

data class Results (

     @SerializedName("id") val id : Int,
     @SerializedName("title") val title : String,
     @SerializedName("image") val image : String
)