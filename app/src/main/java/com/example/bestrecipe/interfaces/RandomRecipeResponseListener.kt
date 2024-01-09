package com.example.bestrecipe.interfaces

import com.example.bestrecipe.models.RecipeResponse

interface RandomRecipeResponseListener {
    fun fetch(response: RecipeResponse, message: String)
    fun error(message: String)
}
