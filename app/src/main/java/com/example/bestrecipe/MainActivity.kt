package com.example.bestrecipe

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bestrecipe.adapters.RandomRecipeAdapter
import com.example.bestrecipe.databinding.ActivityMainBinding
import com.example.bestrecipe.models.RecipeResponse
import com.example.bestrecipe.viewmodels.RandomRecipeViewModel
import com.example.bestrecipe.viewmodels.RandomRecipeViewModelFactory
import com.example.bestrecipe.interfaces.RecipeClickListener

class MainActivity : AppCompatActivity(), RecipeClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var loadingDialog: AlertDialog
    private lateinit var viewModel: RandomRecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerRandom.setHasFixedSize(true)
        binding.recyclerRandom.layoutManager = GridLayoutManager(this, 1)

        val btnOpenMainActivity = findViewById<Button>(R.id.btnOpenMainActivity)
        btnOpenMainActivity.setOnClickListener {
            val intent = Intent(this, StoredRecipesActivity::class.java)
            startActivity(intent)
        }

        var tags = resources.getStringArray(R.array.tag)
        val adapter = ArrayAdapter(this, R.layout.spinner_text, tags)
        adapter.setDropDownViewResource(R.layout.spinner_inner_text)
        binding.spinnerTag.adapter = adapter

        binding.spinnerTag.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedTag = parent.getItemAtPosition(position).toString()
                viewModel.getRecipesByTag(selectedTag)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        val dialogView = layoutInflater.inflate(R.layout.progress_dialog, null)
        loadingDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        val requestManager = RequestManager(this)
        val factory = RandomRecipeViewModelFactory(requestManager)
        viewModel = ViewModelProvider(this, factory)[RandomRecipeViewModel::class.java]

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                tags = arrayOf(query)
                viewModel.getRecipesByTag(tags[0])
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        viewModel.recipes.observe(this) { response ->
            updateUI(response)
        }

        viewModel.error.observe(this) { errorMessage ->
            showError(errorMessage)
        }

        viewModel.recipes.observe(this) { response ->
            binding.recyclerRandom.adapter = RandomRecipeAdapter(this, response.recipes, this)
        }

        loadingDialog.show()
        viewModel.getRandomRecipes()
    }

    private fun updateUI(response: RecipeResponse) {
        loadingDialog.dismiss()
        binding.recyclerRandom.adapter = RandomRecipeAdapter(this, response.recipes, this)
    }

    private fun showError(message: String) {
        loadingDialog.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onRecipeClicked(id: Int) {
        val selectedRecipe = viewModel.recipes.value?.recipes?.find { it.id == id }
        selectedRecipe?.let {
            val intent = Intent(this, RecipeDetailsActivity::class.java)
            intent.putExtra("RECIPE_TITLE", it.title)
            intent.putExtra("RECIPE_IMAGE", it.image)
            intent.putExtra("RECIPE_SERVINGS", it.servings)
            intent.putExtra("RECIPE_PREPARATION", it.readyInMinutes)
            intent.putExtra("RECIPE_INSTRUCTIONS", it.instructions)

            saveRecipeTitle(it.title)

            startActivity(intent)
        }
    }

    private fun saveRecipeTitle(recipeTitle: String) {
        val sharedPreferences = getSharedPreferences("StoredRecipes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val currentTitlesSet = sharedPreferences.getStringSet("recipeTitles", HashSet()) ?: HashSet()
        currentTitlesSet.add(recipeTitle)

        editor.putStringSet("recipeTitles", currentTitlesSet)
        editor.apply()
    }
}
