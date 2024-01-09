package com.example.bestrecipe.interfaces

import com.example.bestrecipe.models.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface RandomRecipeInterface {
    @GET("recipes/random")
    fun callRandomRecipe(
        @Query("apiKey") apiKey: String,
        @Query("number") number: String
    ): Call<RecipeResponse>

    // Added method to fetch recipes based on a tag
    @GET("recipes/random")
    fun callRecipesByTag(
        @Query("apiKey") apiKey: String,
        @Query("number") number: String,
        @Query("tags") tag: String
    ): Call<RecipeResponse>
}
