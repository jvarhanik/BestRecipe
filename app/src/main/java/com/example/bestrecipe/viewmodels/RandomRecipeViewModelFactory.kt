package com.example.bestrecipe.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bestrecipe.RequestManager

class RandomRecipeViewModelFactory(private val requestManager: RequestManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RandomRecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RandomRecipeViewModel(requestManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
