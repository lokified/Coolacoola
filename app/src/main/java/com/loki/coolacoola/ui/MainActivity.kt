package com.loki.coolacoola.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loki.coolacoola.R
import com.loki.coolacoola.adapters.FoodAdapter
import com.loki.coolacoola.models.Food

class MainActivity : AppCompatActivity() {

    private lateinit var foodAdapter: FoodAdapter
    private lateinit var foodRecycler: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var  food: Array<Food>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        foodRecycler = findViewById(R.id.food_recycler)

        food = arrayOf(Food("mango", R.drawable.test), Food("githeri", R.drawable.test ))


        foodAdapter = FoodAdapter(food)
        layoutManager = LinearLayoutManager(this)
        foodRecycler.layoutManager = layoutManager
        foodRecycler.adapter = foodAdapter
    }


}