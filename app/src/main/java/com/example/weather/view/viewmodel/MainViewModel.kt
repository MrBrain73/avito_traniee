package com.example.weather.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.repository.Repository
import com.example.weather.models.CurrentWeather
import com.example.weather.models.ForecastWeather
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Query

class MainViewModel(private val repository: Repository) : ViewModel() {

    val currentWeather : MutableLiveData<Response<CurrentWeather>> = MutableLiveData()
    val forecastWeather : MutableLiveData<Response<ForecastWeather>> = MutableLiveData()

    fun getCurrentWeather(cityName : String, apiKey : String, units : String) {
        viewModelScope.launch {
            currentWeather.value = repository.getCurrentWeather(cityName, apiKey, units)
        }
    }

    fun getForecastWeather(cityName : String, apiKey: String, units: String) {
        viewModelScope.launch {
            forecastWeather.value = repository.getForecastWeather(cityName, apiKey, units)
        }
    }
}