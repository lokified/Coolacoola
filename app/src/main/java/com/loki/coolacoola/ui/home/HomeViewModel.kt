package com.loki.coolacoola.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loki.coolacoola.data.domain.MainRepository
import com.loki.coolacoola.data.models.Random
import com.loki.coolacoola.data.models.Recipes
import com.loki.coolacoola.util.Event
import com.loki.coolacoola.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repository: MainRepository
): ViewModel() {

    private val mErrorToast = MutableLiveData<Event<String>>()
    val errorToast: LiveData<Event<String>> = mErrorToast

    val recipeList: MutableLiveData<List<Random>> = MutableLiveData()

    init {

        loadRecipes()
    }

    private fun loadRecipes() {

        Resource.success(repository.getRandomRecipes(recipeList) { mErrorToast.postValue(Event(it)) })
    }
}