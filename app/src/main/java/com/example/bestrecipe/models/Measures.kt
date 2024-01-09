package com.example.bestrecipe.models

import com.google.gson.annotations.SerializedName

data class Measures(
    @SerializedName("us")
    val us: Measure,
    @SerializedName("metric")
    val metric: Measure
)