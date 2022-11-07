package com.example.weather.models

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("feels_like") val feels_like: Double,
    @SerializedName("temp") val temp: Double,
    @SerializedName("humidity") val humidity : Int
)