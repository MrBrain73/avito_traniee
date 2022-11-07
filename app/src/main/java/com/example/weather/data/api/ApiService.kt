package com.example.weather.data.api

import com.example.weather.models.CurrentWeather
import com.example.weather.models.ForecastWeather
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(@Query("q") cityName : String,
                                  @Query("appid") apiKey : String,
                                  @Query("units") units : String) : Response<CurrentWeather>

    @GET("data/2.5/forecast")
    suspend fun getForecastWeather(@Query("q") cityName : String,
                                   @Query("appid") apiKey: String,
                                   @Query("units") units: String) : Response<ForecastWeather>
}