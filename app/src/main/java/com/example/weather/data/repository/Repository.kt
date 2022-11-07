package com.example.weather.data.repository

import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import com.example.weather.data.api.RetrofitService
import com.example.weather.models.CurrentWeather
import com.example.weather.models.ForecastWeather
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Query

class Repository {

    suspend fun getCurrentWeather(cityName : String, apiKey : String, units : String) :
            Response<CurrentWeather> = RetrofitService.getRetrofitService()
        .getCurrentWeather(cityName, apiKey, units)

    suspend fun getForecastWeather(cityName : String, apiKey: String, units: String) :
            Response<ForecastWeather> = RetrofitService.getRetrofitService()
        .getForecastWeather(cityName, apiKey, units)
}