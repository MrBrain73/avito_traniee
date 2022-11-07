package com.example.weather

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.weather.data.repository.Repository
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.models.CurrentWeather
import com.example.weather.models.ForecastItem
import com.example.weather.view.adapter.MainAdapter
import com.example.weather.view.viewmodel.MainViewModel
import com.example.weather.view.viewmodel.MainViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call

import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    private val API_KEY = "0ed37d66d2a91c755f080ced25b3ae2b"
    private lateinit var binding: ActivityMainBinding
    private val adapter = MainAdapter()
    private var locationCity: MutableLiveData<String> = MutableLiveData()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listForecastWeather.adapter = adapter
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        locationCity.observe(this) {
            viewModel.getCurrentWeather(it, API_KEY, "metric")
            viewModel.getForecastWeather(it, API_KEY, "metric")
        }

        viewModel.currentWeather.observe(this) {
            if (it.isSuccessful) {
                binding.apply {
                    textGeolocation.text = it.body()?.name
                    textCurrentWeatherTemp.text = it.body()?.main?.temp?.toInt().toString() + "°"
                    textCurrentWeatherText.text = it.body()?.weather?.get(0)?.main
                    textCloud.text = it.body()?.clouds?.all.toString() + "%"
                    textWindSpeed.text = it.body()?.wind?.speed.toString() + "m/s"
                    textRainVolume.text = it.body()?.main?.humidity.toString() + "%"
                }
            } else println(it.message())
        }

        viewModel.forecastWeather.observe(this) {
            if (it.isSuccessful) {
                adapter.setData(it.body()?.list as ArrayList<ForecastItem>)
            } else println(it.message())
        }

        binding.iconGeolocation.setOnClickListener {
            getLocation()
            binding.textGeolocation.visibility = View.VISIBLE
            binding.textSearch.visibility = View.GONE
        }

        binding.iconSearch.setOnClickListener {
            binding.apply {
                textGeolocation.visibility = View.GONE
                textSearch.visibility = View.VISIBLE
            }
        }

        binding.textSearch.editText?.setOnKeyListener(View.OnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                locationCity.value = binding.textSearch.editText!!.text.toString()
                binding.textGeolocation.visibility = View.VISIBLE
                binding.textSearch.visibility = View.GONE
                binding.textSearch.editText!!.text.clear()
                return@OnKeyListener true
            }
            return@OnKeyListener false
        })
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val address = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                    locationCity.value = address[0].locality
                }
            }
        } else ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                getLocation()
            else Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show()
        }
    }


}
