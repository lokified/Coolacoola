package com.loki.coolacoola.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loki.coolacoola.R

class RecipeCategoryAdapter(
    val  recipe : List<String>
) : RecyclerView.Adapter<RecipeCategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.food_cat_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.recipe_name.text = recipe[position]
    }

    override fun getItemCount(): Int {
        return recipe.size
    }



    inner class CategoryViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {

        var recipe_name: TextView = itemView.findViewById(R.id.recipe_cat_name)

    }

}