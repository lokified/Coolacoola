package com.loki.coolacoola.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loki.coolacoola.R
import com.loki.coolacoola.data.models.Food

class FoodAdapter(
    val food: Array<Food>
): RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.food_item_layout, parent, false)

        return FoodViewHolder(v)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.foodImg.setImageResource(food[position].imgId)
        holder.foodName.text = food[position].name
    }

    override fun getItemCount(): Int {
        return food.size
    }

    class FoodViewHolder(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        var foodImg: ImageView = itemView.findViewById(R.id.food_img)
        var foodName: TextView = itemView.findViewById(R.id.food_name)

    }
}