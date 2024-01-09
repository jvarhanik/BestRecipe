package com.example.bestrecipe.models

import com.google.gson.annotations.SerializedName

data class Step(
    @SerializedName("number")
    val number: Int,
    @SerializedName("step")
    val step: String,
    @SerializedName("ingredients")
    val ingredients: List<Ingredient>,
    @SerializedName("equipment")
    val equipment: List<Equipment>,
    @SerializedName("length")
    val length: Length?
)