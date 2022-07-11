package com.loki.coolacoola.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loki.coolacoola.R
import com.loki.coolacoola.data.models.Results
import com.loki.coolacoola.ui.RecipeDetailActivity

class RecipesAdapter: RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>()  {

    private var recipes = mutableListOf<Results>()
    var cardPosition : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recipe_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        val newCardPosition : Int = holder.adapterPosition

        cardPosition = newCardPosition

        val imageUri : String = recipes[position].image

        Glide.with(holder.recipeImg).load(imageUri).into(holder.recipeImg)

        holder.recipeTitle.text = recipes[position].title


        holder.itemView.setOnClickListener {

            Intent(it.context.applicationContext, RecipeDetailActivity::class.java).also { intent ->
                intent.putExtra("id", recipes[position].id)
                intent.putExtra("title", recipes[position].title)
                intent.putExtra("image", recipes[position].image)

                it.context.applicationContext.startActivity(intent)

            }

        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setRecipeList(recipes: List<Results>) {

        this.recipes = recipes.toMutableList()
        notifyDataSetChanged()
    }


     inner class RecipeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val recipeImg : ImageView = itemView.findViewById(R.id.recipe_img)
        val recipeTitle : TextView = itemView.findViewById(R.id.recipe_title)
    }

}