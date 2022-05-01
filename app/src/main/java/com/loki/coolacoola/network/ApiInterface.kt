package com.loki.coolacoola.network

import com.loki.coolacoola.models.ModelClass
import com.loki.coolacoola.models.RecipeInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("recipes/complexSearch?&number=6&")
    fun getRecipeData (
        @Query("query") query: String
    ): Call<ModelClass>

    @GET("recipes/{id}/information?includeNutrition=false&")
    fun getRecipeDetail(
        @Path("id") id: Int
    ) : Call<RecipeInfo>

}