package com.loki.coolacoola.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loki.coolacoola.R
import com.loki.coolacoola.adapters.IngredientAdapter
import com.loki.coolacoola.adapters.InstructionAdapter
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

    private lateinit var  instAdapter : InstructionAdapter

    private lateinit var mReady : TextView
    private lateinit var mServing : TextView
    private lateinit var mSource : TextView
    private lateinit var mSourceLink : TextView
    private lateinit var mInstruction : TextView

    private lateinit var mLayout : RelativeLayout
    private lateinit var mProgress : ProgressBar
    private lateinit var mError : TextView

    private var isToFormat: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val mImage : ImageView = findViewById(R.id.recipe_det_img)
        val mTitle : TextView = findViewById(R.id.recipe_det_title)

        mReady = findViewById(R.id.recipe_det_time)
        mServing = findViewById(R.id.recipe_det_serve)
        mSource = findViewById(R.id.source_name)
        mSourceLink = findViewById(R.id.source_link)
        mInstruction = findViewById(R.id.instruction)

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
                    hideProgressBar()
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

            if (isToFormat) {
                formatInstruction(recipe.instructions)
                mInstruction.visibility = View.GONE
            }
            else {
                mInstruction.text = recipe.instructions
            }

            Log.i("instruct", recipe.instructions)
            mServing.text = recipe.servings.toString()
            mReady.text = recipe.readyInMinutes.toString() + " mins"

            if (recipe.sourceName != null) {
                mSource.text = "Source : " + recipe.sourceName
            }
            else{
                mSource.text = "Source : visit the website"
            }

            val webLink: String = recipe.sourceUrl

            mSourceLink.setOnClickListener {

                Intent(Intent.ACTION_VIEW, Uri.parse(webLink)).also {
                    startActivity(it)
                }
            }
        }
    }

    private fun formatInstruction(instructions : String){

        isToFormat

        val tags = listOf("<ol>", "</ol>", "<li>", "</li>", "</ol>")

        var newInst = ""

        for (i in tags.indices) {
            if (instructions.contains(tags[i])) {
                val newInst1: String = instructions.replace(tags[0], "")
                val newInst2: String = newInst1.replace(tags[1], "")
                val newInst3: String = newInst2.replace(tags[2], "")
                newInst = newInst3.replace(tags[3], "")

                val finalString : String = newInst.replace(".", ",")

                val arrSplit = finalString.split(",").toTypedArray()


                for (k in arrSplit.indices) {
                    setUpInstructions(arrSplit)
                }
            }
        }
    }

    private fun setUpInstructions(instruct: Array<String>)  {

        val recycler : RecyclerView = findViewById(R.id.instruct_recycler)

        recycler.layoutManager = LinearLayoutManager(this)
        instAdapter = InstructionAdapter(instruct)
        recycler.adapter = instAdapter
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