package com.example.bestrecipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bestrecipe.adapters.StoredRecipesAdapter
import com.example.bestrecipe.databinding.ActivityStoredRecipesBinding

class StoredRecipesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoredRecipesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoredRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sharedPreferences = getSharedPreferences("StoredRecipes", Context.MODE_PRIVATE)
        val titlesSet = sharedPreferences.getStringSet("recipeTitles", HashSet())

        val titlesList = titlesSet?.toList() ?: emptyList()

        val adapter = StoredRecipesAdapter(titlesList)
        binding.recyclerViewStoredTitles.adapter = adapter
        binding.recyclerViewStoredTitles.layoutManager = LinearLayoutManager(this)
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
