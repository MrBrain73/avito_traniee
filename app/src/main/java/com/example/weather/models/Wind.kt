package com.example.weather.models

import com.google.gson.annotations.SerializedName

data class Wind (
    @SerializedName("speed") val speed : Double
)