package com.loki.coolacoola.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loki.coolacoola.adapters.RecipeCategoryAdapter
import com.loki.coolacoola.adapters.RecipesAdapter
import com.loki.coolacoola.databinding.ActivityMainBinding
import com.loki.coolacoola.models.ModelClass
import com.loki.coolacoola.network.ApiUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: RecipeCategoryAdapter
    private lateinit var categoryRecycler : RecyclerView

    private lateinit var recipesAdapter: RecipesAdapter
    private lateinit var recipesRecycler : RecyclerView

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mEditor : SharedPreferences.Editor
    private lateinit var mSearchTerm : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        mEditor = sharedPreferences.edit()


        val categories : List<String> = listOf("Breakfast", "Lunch", "Dinner")

        setUpCategory(categories)

        hideProgressBar()

        binding.search.setOnClickListener {
            val recipe: String = binding.searchRes.text.toString()
            addToSharedPreference(recipe)
            showProgressBar()
            fetchRecipeData(recipe)
        }

        mSearchTerm = sharedPreferences.getString("recipe", null).toString()

        fetchRecipeData(mSearchTerm)

    }

    private fun setUpCategory(category : List<String>) {
        categoryRecycler = binding.foodCatRecycler
        categoryRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        categoryAdapter = RecipeCategoryAdapter(category)
        categoryRecycler.adapter = categoryAdapter
    }


    private fun fetchRecipeData(term : String){

        ApiUtil.getApiInterface()?.getRecipeData(term)?.enqueue(
            object : Callback<ModelClass> {
                override fun onResponse(call: Call<ModelClass>, response: Response<ModelClass>) {

                    hideProgressBar()

                    if (response.isSuccessful) {
                        setData(response.body())

                        showRecipes()
                    }
                    else{

                        showUnSuccessfulMessage()
                    }
                }

                override fun onFailure(call: Call<ModelClass>, t: Throwable) {

                    hideProgressBar()
                    showFailureMessage()
                }
            }
        )
    }


    private fun setData(recipes : ModelClass?) {

        if (recipes != null) {

            recipesRecycler = binding.recipeRecycler
            recipesRecycler.layoutManager = GridLayoutManager(this, 2)

            recipesAdapter = RecipesAdapter(recipes.results)
            recipesRecycler.adapter = recipesAdapter

        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
        binding.txtSearch.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.txtSearch.visibility = View.VISIBLE
    }

    private fun showFailureMessage() {
        binding.txtSearch.text = "Something went wrong. Please check your Internet connection and try again later"
        binding.txtSearch.visibility = View.VISIBLE
    }

    private fun showUnSuccessfulMessage() {
        binding.txtSearch.text = "Something went wrong. Please try again later"
        binding.txtSearch.visibility = View.VISIBLE
    }

    private fun hideErrorMessage() {
        binding.txtSearch.visibility = View.GONE
    }

    private fun showRecipes() {
        binding.recipeRecycler.visibility = View.VISIBLE
    }

    private fun addToSharedPreference(term : String) {
        mEditor.putString("recipe", term).apply()
    }

}