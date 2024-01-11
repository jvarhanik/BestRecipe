package com.example.bestrecipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.content.Intent
import com.example.bestrecipe.databinding.ActivityRecipeDetailsBinding
import com.squareup.picasso.Picasso

class RecipeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recipeTitle = intent.getStringExtra("RECIPE_TITLE") ?: "Unknown Title"
        val recipeImage = intent.getStringExtra("RECIPE_IMAGE") ?: ""
        val servings = intent.getIntExtra("RECIPE_SERVINGS", 0)
        val preparationMinutes = intent.getIntExtra("RECIPE_PREPARATION", 0)
        val instructions = intent.getStringExtra("RECIPE_INSTRUCTIONS") ?: ""

        binding.detailsHeader.text = recipeTitle
        binding.textServings.text = "Servings: $servings"
        binding.textPreparation.text = "Preparation Time: ${preparationMinutes} minutes"
        binding.textViewInstructions.text = instructions

        Picasso.get().load(recipeImage).into(binding.imageView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
