package com.example.weather.models

import com.google.gson.annotations.SerializedName

data class All(
    @SerializedName("all") val all : Int
)
