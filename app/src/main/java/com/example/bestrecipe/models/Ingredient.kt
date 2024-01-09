package com.example.bestrecipe.models

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("id")
    val id: Int,
    @SerializedName("aisle")
    val aisle: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("consistency")
    val consistency: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("nameClean")
    val nameClean: String,
    @SerializedName("original")
    val original: String,
    @SerializedName("originalName")
    val originalName: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("meta")
    val meta: List<String>,
    @SerializedName("measures")
    val measures: Measures
)
