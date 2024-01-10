package com.example.bestrecipe// Inside com.example.bestrecipe.RecipeDetailsActivity.kt
import IngredientAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bestrecipe.databinding.ActivityRecipeDetailsBinding
import com.example.bestrecipe.models.Ingredient
import com.squareup.picasso.Picasso

class RecipeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the recipe details from intent
        val recipeTitle = intent.getStringExtra("RECIPE_TITLE") ?: "Unknown Title"
        val recipeImage = intent.getStringExtra("RECIPE_IMAGE") ?: ""
        val servings = intent.getIntExtra("RECIPE_SERVINGS", 0)
        val preparationMinutes = intent.getIntExtra("RECIPE_PREPARATION", 0)
        val instructions = intent.getStringExtra("RECIPE_INSTRUCTIONS") ?: ""


        binding.detailsHeader.text = recipeTitle
        // Bind data to the views
        binding.textServings.text = "Servings: $servings"
        binding.textPreparation.text = "Preparation Time: ${preparationMinutes} minutes"
        binding.textViewInstructions.text = instructions

        // Load recipe image using Glide (or any other image loading library)
        Picasso.get().load(recipeImage).into(binding.imageView)

    }
}
