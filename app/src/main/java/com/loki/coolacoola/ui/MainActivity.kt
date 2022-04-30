package com.loki.coolacoola.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loki.coolacoola.R
import com.loki.coolacoola.adapters.RecipeCategoryAdapter
import com.loki.coolacoola.databinding.ActivityMainBinding
import com.loki.coolacoola.models.Food

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: RecipeCategoryAdapter
    private lateinit var categoryRecycler : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryRecycler = binding.foodCatRecycler
        categoryRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        val categories : List<String> = listOf("Breakfast", "Lunch", "Dinner")

        categoryAdapter = RecipeCategoryAdapter(categories)
        categoryRecycler.adapter = categoryAdapter

    }


}