package com.example.bestrecipe.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bestrecipe.databinding.HeaderLayoutBinding
import com.example.bestrecipe.databinding.RandomRecipeBinding
import com.example.bestrecipe.models.Recipe
import com.squareup.picasso.Picasso

class RandomRecipeAdapter(val context: Context, private val list: List<Recipe>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> {
                val binding = HeaderLayoutBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            else -> {
                val binding = RandomRecipeBinding.inflate(inflater, parent, false)
                RandomRecipeViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                // Bind header data here
                holder.bind("Welcome to Best Recipe App")
            }
            is RandomRecipeViewHolder -> {
                // Adjust position by subtracting 1 because of the header
                val item = list[position - 1]
                holder.binding.titleText.apply {
                    text = item.title
                    isSelected = true
                }
                holder.binding.likesText.text = "${item.aggregateLikes} Likes"
                holder.binding.portionsText.text = "${item.servings} Servings"
                holder.binding.preparationText.text = "${item.readyInMinutes} Minutes"

                Picasso.get().load(item.image).into(holder.binding.foodImage)
            }
        }
    }

    override fun getItemCount(): Int {
        // Add 1 for the header
        return list.size + 1
    }

    // HeaderViewHolder implementation
    class HeaderViewHolder(val binding: HeaderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(headerText: String) {
            binding.appHeader.text = headerText
        }
    }

    // Assuming RandomRecipeViewHolder is implemented using ViewBinding
    class RandomRecipeViewHolder(val binding: RandomRecipeBinding) : RecyclerView.ViewHolder(binding.root)
}
