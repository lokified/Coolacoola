package com.loki.coolacoola.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.loki.coolacoola.R
import com.loki.coolacoola.databinding.FragmentHomeBinding
import com.loki.coolacoola.ui.adapters.RecipeCategoryAdapter
import com.loki.coolacoola.ui.adapters.RecipesAdapter
import com.loki.coolacoola.util.EventObserver
import com.loki.coolacoola.util.extension.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var categoryAdapter: RecipeCategoryAdapter
    private val  recipesAdapter  =  RecipesAdapter()
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {

            viewModel
        }

        setUpCategory()
        setUpRecipes()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName : String = FirebaseAuth.getInstance().currentUser?.displayName.toString()
        binding.userName.text = userName

        setUpObserver()

        //show popup menu
        binding.userAcc.setOnClickListener {

            showPopUpMenu(it as ImageView)
        }
    }

    private fun setUpCategory() {
        val categories : List<String> = listOf("Breakfast", "Lunch", "Dinner")

        binding.apply {

            binding.foodCatRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            categoryAdapter = RecipeCategoryAdapter(categories)
            binding.foodCatRecycler.adapter = categoryAdapter
        }

    }

    private fun setUpObserver() {

        viewModel.recipeList.observe(viewLifecycleOwner, Observer {

            recipesAdapter.setRecipeList(it)

        })

        viewModel.errorToast.observe(viewLifecycleOwner, EventObserver {

            showToast(it)
        })
    }

    private fun setUpRecipes() {

        binding.apply {

            recipeRecycler.layoutManager = GridLayoutManager(context, 2)
            recipeRecycler.adapter = recipesAdapter
        }

    }


    private fun showPopUpMenu(imageView: ImageView) {
        val popupMenu = PopupMenu(context, imageView)
        popupMenu.inflate(R.menu.user_menu)
        popupMenu.setOnMenuItemClickListener {
            val itemId: Int = it.itemId

            if (itemId == R.id.account) {

                //navigate to user account fragment

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

        //navigate to login
    }

}