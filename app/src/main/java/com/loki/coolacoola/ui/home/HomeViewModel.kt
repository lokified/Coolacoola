package com.loki.coolacoola.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loki.coolacoola.data.domain.MainRepository
import com.loki.coolacoola.data.models.Results
import com.loki.coolacoola.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repository: MainRepository
): ViewModel() {

    private val mErrorToast = MutableLiveData<Event<String>>()
    val errorToast: LiveData<Event<String>> = mErrorToast

    val recipeList: MutableLiveData<List<Results>> = MutableLiveData()

    init {

        loadRecipes()
    }

    fun loadRecipes() {

        repository.getRecipes("pasta", recipeList ) { mErrorToast.postValue(Event(it)) }
    }
}