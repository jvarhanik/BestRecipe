package com.example.bestrecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bestrecipe.adapters.RandomRecipeAdapter
import com.example.bestrecipe.databinding.ActivityMainBinding
import com.example.bestrecipe.models.RecipeResponse
import com.example.bestrecipe.viewmodels.RandomRecipeViewModel
import com.example.bestrecipe.viewmodels.RandomRecipeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var loadingDialog: AlertDialog
    private lateinit var viewModel: RandomRecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the RecyclerView
        binding.recyclerRandom.setHasFixedSize(true)
        binding.recyclerRandom.layoutManager = GridLayoutManager(this, 1)

        // Initialize the Spinner
        val tags = resources.getStringArray(R.array.tag) // Replace with dynamic data if needed
        val adapter = ArrayAdapter(this, R.layout.spinner_text, tags)
        adapter.setDropDownViewResource(R.layout.spinner_inner_text)
        binding.spinnerTag.adapter = adapter

        // Set Spinner listener
        binding.spinnerTag.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedTag = parent.getItemAtPosition(position).toString()
                viewModel.getRecipesByTag(selectedTag)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optional: Handle no selection
            }
        }

        // Initialize the loading dialog
        val dialogView = layoutInflater.inflate(R.layout.progress_dialog, null)
        loadingDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        // Initialize the ViewModel with the factory
        val requestManager = RequestManager(this)
        val factory = RandomRecipeViewModelFactory(requestManager)
        viewModel = ViewModelProvider(this, factory)[RandomRecipeViewModel::class.java]

        // Set up LiveData observation
        viewModel.recipes.observe(this) { response ->
            updateUI(response)
        }

        viewModel.error.observe(this) { errorMessage ->
            showError(errorMessage)
        }

        // Initial fetch of recipes
        loadingDialog.show()
        viewModel.getRandomRecipes()
    }

    private fun updateUI(response: RecipeResponse) {
        loadingDialog.dismiss()
        binding.recyclerRandom.adapter = RandomRecipeAdapter(this, response.recipes)
    }

    private fun showError(message: String) {
        loadingDialog.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
