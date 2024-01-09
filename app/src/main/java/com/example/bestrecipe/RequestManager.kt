package com.example.bestrecipe

import com.example.bestrecipe.interfaces.RandomRecipeInterface
import android.content.Context
import com.example.bestrecipe.interfaces.RandomRecipeResponseListener
import com.example.bestrecipe.models.RecipeResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestManager(private val context: Context) {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getRandomRecipes(listener: RandomRecipeResponseListener) {
        val call = retrofit.create(RandomRecipeInterface::class.java)
            .callRandomRecipe(context.getString(R.string.api_key), "10")
        enqueueCall(call, listener)
    }

    fun getRecipesByTag(tag: String, listener: RandomRecipeResponseListener) {
        val call = retrofit.create(RandomRecipeInterface::class.java)
            .callRecipesByTag(context.getString(R.string.api_key), "10", tag)
        enqueueCall(call, listener)
    }

    private fun enqueueCall(call: Call<RecipeResponse>, listener: RandomRecipeResponseListener) {
        call.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                if (!response.isSuccessful) {
                    listener.error(response.message())
                } else {
                    response.body()?.let { listener.fetch(it, response.message()) }
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                listener.error(t.message.toString())
            }
        })
    }
}
