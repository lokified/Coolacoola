package com.loki.coolacoola.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loki.coolacoola.R
import com.loki.coolacoola.data.models.Random
import com.loki.coolacoola.databinding.RecipeItemLayoutBinding

class RecipesAdapter: RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>()  {

    private var recipes = mutableListOf<Random>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeItemLayoutBinding.inflate(inflater, parent, false)

        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setRecipeList(recipes: List<Random>) {

        this.recipes = recipes.toMutableList()
        notifyDataSetChanged()
    }


     inner class RecipeViewHolder(val binding: RecipeItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {


         fun bind(random: Random) {

             binding.apply {

                 random.apply {

                     val imageUri = image

                     Glide.with(recipeImg).load(imageUri).into(recipeImg)
                     recipeTitle.text = title
                     recipeReady.text = readyInMinutes.toString()
                     recipeServing.text = servings.toString()
                 }
             }
         }
    }

}