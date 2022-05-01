package com.loki.coolacoola.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loki.coolacoola.R
import com.loki.coolacoola.models.Ingredients
import com.squareup.picasso.Picasso

class IngredientAdapter(
    val ingredient : List<Ingredients>
) : RecyclerView.Adapter<IngredientAdapter.IngredientsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {

        val ingredients : Ingredients = ingredient[position]

        ingredients.apply {
            holder.ingredient.text = this.aisle
            holder.amount.text = this.amount.toString()
            holder.consitency.text = this.consitency
            holder.name.text = this.name
            holder.original.text = this.original

            Picasso.get().load("https://spoonacular.com/recipeImages/" + this.image).into(holder.image)
        }
    }

    override fun getItemCount(): Int {
        return ingredient.size
    }


    inner class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ingredient : TextView = itemView.findViewById(R.id.recipe_det_ingredient)
        val amount : TextView = itemView.findViewById(R.id.recipe_det_amount)
        val consitency : TextView = itemView.findViewById(R.id.recipe_det_consitency)
        val name : TextView = itemView.findViewById(R.id.ingredient_name)
        val original : TextView = itemView.findViewById(R.id.ingredient_original)
        val image : ImageView = itemView.findViewById(R.id.ingredient_image)
    }
}