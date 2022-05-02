package com.loki.coolacoola.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loki.coolacoola.R
import com.loki.coolacoola.adapters.IngredientAdapter
import com.loki.coolacoola.models.Ingredients
import com.loki.coolacoola.models.RecipeInfo
import com.loki.coolacoola.network.ApiUtil
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var ingredientRecycler : RecyclerView
    private lateinit var ingredientAdapter : IngredientAdapter

    private lateinit var mInstruction : TextView
    private lateinit var mReady : TextView
    private lateinit var mServing : TextView

    private lateinit var mLayout : RelativeLayout
    private lateinit var mProgress : ProgressBar
    private lateinit var mError : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val mImage : ImageView = findViewById(R.id.recipe_det_img)
        val mTitle : TextView = findViewById(R.id.recipe_det_title)

        mInstruction = findViewById(R.id.recipe_det_instr)
        mReady = findViewById(R.id.recipe_det_time)
        mServing = findViewById(R.id.recipe_det_serve)

        mLayout = findViewById(R.id.ing_layout)
        mProgress = findViewById(R.id.detail_progress)
        mError = findViewById(R.id.err_msg)

        val intent : Intent = intent

        val title : String? = intent.getStringExtra("title")
        val image : String? = intent.getStringExtra("image")
        val id : Int = intent.getIntExtra("id", 0)


        Picasso.get().load(image).into(mImage)
        mTitle.text = title

        fetchRecipeDetail(id)

    }


    private fun fetchRecipeDetail( id : Int) {

        ApiUtil.getApiInterface()?.getRecipeDetail(id)?.enqueue(
            object : Callback<RecipeInfo> {
                override fun onResponse(call: Call<RecipeInfo>, response: Response<RecipeInfo>) {
                    if (response.isSuccessful){
                        setUpRecipeDetail(id, response.body()!!)
                        setUpIngredient(response.body()!!.extendedIngredients)

                        showDetails()
                        hideProgressBar()
                    }
                }

                override fun onFailure(call: Call<RecipeInfo>, t: Throwable) {
                    showUnsuccessfulMessage()
                }
            }
        )
    }


    private fun setUpIngredient(ingredient : List<Ingredients>) {

        ingredientRecycler = findViewById(R.id.ingredientsRecycler)

        ingredientRecycler.layoutManager = LinearLayoutManager(this)

        ingredientAdapter = IngredientAdapter(ingredient)
        ingredientRecycler.adapter = ingredientAdapter
    }

    private fun setUpRecipeDetail(id : Int, recipe : RecipeInfo) {

        if (id == recipe.id) {
            mInstruction.text = recipe.instructions
            mServing.text = recipe.servings.toString()
            mReady.text = recipe.readyInMinutes.toString() + " mins"
        }
    }

    private fun hideProgressBar() {
        mProgress.visibility = View.GONE
    }

    private fun showDetails() {
        mLayout.visibility = View.VISIBLE
    }

    private fun showUnsuccessfulMessage(){
        mError.visibility = View.VISIBLE
    }
}