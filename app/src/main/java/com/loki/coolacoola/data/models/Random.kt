package com.loki.coolacoola.data.models

data class Random(
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val image: String,
    val imageType: String,
    val dishTypes: List<String>,
    val instructions: String,
)
