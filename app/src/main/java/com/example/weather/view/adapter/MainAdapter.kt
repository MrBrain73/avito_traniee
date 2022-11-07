package com.example.weather.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.databinding.ItemListBinding
import com.example.weather.models.ForecastItem
import com.squareup.picasso.Picasso
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainAdapter : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    private var listForecastItem = ArrayList<ForecastItem>()

    class DataViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemListBinding.bind(itemView)
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(forecastItem: ForecastItem) {
            binding.apply {
                tempValue.text = forecastItem.main.temp.toInt().toString() + "°"
                val stamp = Timestamp(forecastItem.dt * 1000)
                dateName.text = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date(stamp.time))
                timeName.text = SimpleDateFormat("H:mm").format(Date(stamp.time))

                val url = "http://openweathermap.org/img/w/" + forecastItem.weather[0].icon + ".png"

                Picasso.with(itemView.context).load(url).into(iconWeather)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false))

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(listForecastItem[position])
    }

    override fun getItemCount(): Int = listForecastItem.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listForecastItem: ArrayList<ForecastItem>) {
        this.listForecastItem.clear()
        this.listForecastItem = listForecastItem
        notifyDataSetChanged()
    }
}