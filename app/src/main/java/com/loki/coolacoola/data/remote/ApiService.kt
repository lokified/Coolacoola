package com.loki.coolacoola.data.remote

import com.loki.coolacoola.data.models.ModelClass
import com.loki.coolacoola.data.models.RecipeInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/complexSearch?&number=100&")
    fun getRecipeData (
        @Query("query") query: String
    ): Call<ModelClass>

    @GET("recipes/{id}/information?includeNutrition=false&")
    fun getRecipeDetail(
        @Path("id") id: Int
    ) : Call<RecipeInfo>

}