package com.loki.coolacoola.data.domain

import androidx.lifecycle.MutableLiveData
import com.loki.coolacoola.data.models.ModelClass
import com.loki.coolacoola.data.models.RecipeInfo
import com.loki.coolacoola.data.models.Results
import com.loki.coolacoola.data.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun getRecipes(query: String, recipeList: MutableLiveData<List<Results>>, errorText: (String) -> Unit) {

        val call = apiService.getRecipeData(query)

        call.enqueue(object: Callback<ModelClass> {
            override fun onResponse(call: Call<ModelClass>, response: Response<ModelClass>) {

                if(response.isSuccessful) {
                    recipeList.postValue(response.body()?.results)
                }
                else {
                    errorText("something went wrong")
                }
            }

            override fun onFailure(call: Call<ModelClass>, t: Throwable) {
                errorText(t.localizedMessage!!)
            }
        })
    }
}