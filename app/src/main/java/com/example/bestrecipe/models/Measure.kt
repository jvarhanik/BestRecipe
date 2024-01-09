package com.example.bestrecipe.models

import com.google.gson.annotations.SerializedName

data class Measure(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("unitShort")
    val unitShort: String,
    @SerializedName("unitLong")
    val unitLong: String
)