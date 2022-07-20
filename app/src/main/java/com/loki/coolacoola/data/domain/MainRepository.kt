package com.loki.coolacoola.data.domain

import androidx.lifecycle.MutableLiveData
import com.loki.coolacoola.data.models.Random
import com.loki.coolacoola.data.models.Recipes
import com.loki.coolacoola.data.remote.ApiService
import com.loki.coolacoola.util.extension.onException
import com.loki.coolacoola.util.extension.onFailure
import com.loki.coolacoola.util.extension.onSuccess
import com.loki.coolacoola.util.extension.request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService
) {

     fun getRecipes(
         query: String,
         recipeList: MutableLiveData<List<Recipes>>,
         errorText: (String) -> Unit) {

        val call = apiService.getRecipeData(query)

        call.request { response ->

            response.onSuccess { data?.let {
                recipeList.postValue(it.results)
            } }
            response.onFailure { message?.let { errorText(it) } }
            response.onException { message?.let { errorText(it)} }
        }
    }

    fun getRandomRecipes(
        recipeList: MutableLiveData<List<Random>>,
        errorText: (String) -> Unit
    ) {

        val call = apiService.getRandomRecipes()

        call.request { response ->

            response.onSuccess { data?.let {
                recipeList.postValue(it.recipes)
            } }
            response.onFailure { message?.let { errorText(it) } }
            response.onException { message?.let { errorText(it)} }
        }
    }
}