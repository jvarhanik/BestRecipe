package com.example.bestrecipe.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.bestrecipe.RequestManager
import com.example.bestrecipe.interfaces.RandomRecipeResponseListener
import com.example.bestrecipe.models.RecipeResponse

class RandomRecipeViewModel(private val requestManager: RequestManager) : ViewModel() {

    val recipes = MutableLiveData<RecipeResponse>()
    val error = MutableLiveData<String>()

    fun getRandomRecipes() {
        requestManager.getRandomRecipes(object : RandomRecipeResponseListener {
            override fun fetch(response: RecipeResponse, message: String) {
                recipes.postValue(response)
            }

            override fun error(message: String) {
                error.postValue(message)
            }
        })
    }

    fun getRecipesByTag(tag: String) {
        requestManager.getRecipesByTag(tag, object : RandomRecipeResponseListener {
            override fun fetch(response: RecipeResponse, message: String) {
                recipes.postValue(response)
            }

            override fun error(message: String) {
                error.postValue(message)
            }
        })
    }
}
