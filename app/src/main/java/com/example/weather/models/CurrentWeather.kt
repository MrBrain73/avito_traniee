package com.example.weather.models

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("cod") val cod: Int,
    @SerializedName("dt") val dt: Long,
    @SerializedName("main") val main: Main,
    @SerializedName("name") val name: String,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("clouds") val clouds : All,
    @SerializedName("wind") val wind : Wind
)