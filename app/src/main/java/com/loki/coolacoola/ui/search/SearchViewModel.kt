package com.loki.coolacoola.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loki.coolacoola.data.domain.MainRepository
import com.loki.coolacoola.data.models.Recipes
import com.loki.coolacoola.util.Event
import com.loki.coolacoola.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {


    private val mErrorToast = MutableLiveData<Event<String>>()
    val errorToast: LiveData<Event<String>> = mErrorToast

    val recipeList: MutableLiveData<List<Recipes>> = MutableLiveData()


    fun loadRecipes(query: String) {

        Resource.success(repository.getRecipes(query, recipeList) { mErrorToast.postValue(Event(it)) })
    }
}