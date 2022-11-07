package com.example.weather.models

import com.google.gson.annotations.SerializedName

data class ForecastWeather(
    @SerializedName("cnt") val cnt: Int,
    @SerializedName("cod") val cod: String,
    @SerializedName("list") val list: List<ForecastItem>,
    @SerializedName("message") val message: Int
)