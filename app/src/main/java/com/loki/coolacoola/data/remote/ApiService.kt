package com.loki.coolacoola.data.remote

import com.loki.coolacoola.data.models.RandomResponse
import com.loki.coolacoola.data.models.RecipeResponse
import com.loki.coolacoola.data.models.RecipeDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/random?&number=100&")
    fun getRandomRecipes(): Call<RandomResponse>

    @GET("recipes/complexSearch?&number=100&")
    fun getRecipeData (
        @Query("query") query: String
    ): Call<RecipeResponse>

    @GET("recipes/{id}/information?includeNutrition=false&")
    fun getRecipeDetail(
        @Path("id") id: Int
    ) : Call<RecipeDetail>

}