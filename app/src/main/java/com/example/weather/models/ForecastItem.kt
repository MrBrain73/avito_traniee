package com.example.weather.models

import com.google.gson.annotations.SerializedName

data class ForecastItem (
    @SerializedName("dt") val dt: Long,
    @SerializedName("dt_txt") val dt_txt: String,
    @SerializedName("main") val main: Main,
    @SerializedName("weather") val weather: List<Weather>
)