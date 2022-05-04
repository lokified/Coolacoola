package com.loki.coolacoola.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.loki.coolacoola.R
import com.loki.coolacoola.adapters.RecipeCategoryAdapter
import com.loki.coolacoola.adapters.RecipesAdapter
import com.loki.coolacoola.databinding.ActivityMainBinding
import com.loki.coolacoola.models.ModelClass
import com.loki.coolacoola.models.RecipeInfo
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

        val userName : String = FirebaseAuth.getInstance().currentUser?.displayName.toString()
        binding.userName.text = userName

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        mEditor = sharedPreferences.edit()


        val categories : List<String> = listOf("Breakfast", "Lunch", "Dinner")

        setUpCategory(categories)


        binding.search.setOnClickListener {
            val recipe: String = binding.searchRes.text.toString()
            addToSharedPreference(recipe)
            showProgressBar()
            fetchRecipeData(recipe)
        }

        mSearchTerm = sharedPreferences.getString("recipe", null).toString()

        fetchRecipeData(mSearchTerm)

        //show popup menu
        binding.userAcc.setOnClickListener {

            showPopUpMenu(it as ImageView)
        }

    }

    private fun showPopUpMenu(imageView: ImageView) {
        val popupMenu = PopupMenu(this, imageView)
        popupMenu.inflate(R.menu.user_menu)
        popupMenu.setOnMenuItemClickListener {
            val itemId: Int = it.itemId

            if (itemId == R.id.account) {

                Intent(this, UserAccount::class.java).also { intent ->
                    startActivity(intent)
                }
            }

            if (itemId == R.id.logout) {
                logOut()
            }

            return@setOnMenuItemClickListener true
        }
        popupMenu.show()

    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()

        Intent(this, LoginActivity::class.java).also {
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(it)
            finish()
            Toast.makeText(this, "logged out", Toast.LENGTH_LONG).show()
        }
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
                        setUpRecipes(response.body())

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


    private fun setUpRecipes(recipes : ModelClass?) {

        if (recipes != null) {

            recipesRecycler = binding.recipeRecycler
            recipesRecycler.layoutManager = GridLayoutManager(this, 2)

            recipesAdapter = RecipesAdapter(recipes.results)
            recipesRecycler.adapter = recipesAdapter

          //  val car : CardView = findViewById(R.id.main_card)

            //val position: Int? = recipesAdapter.RecipeViewHolder(car).adapterPosition

            //setUpServingReady(recipes.results[position!!].id)
        }
    }

    private fun setUpServingReady(id : Int) {
        ApiUtil.getApiInterface()?.getRecipeDetail(id)?.enqueue(
            object : Callback<RecipeInfo> {
                override fun onResponse(call: Call<RecipeInfo>, response: Response<RecipeInfo>) {
                    if (response.isSuccessful){

                        val serving : TextView = findViewById(R.id.recipe_serving)
                        val ready : TextView = findViewById(R.id.recipe_ready)

                        serving.text = response.body()!!.servings.toString()
                        ready.text = response.body()!!.readyInMinutes.toString()
                    }
                }

                override fun onFailure(call: Call<RecipeInfo>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
        )
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