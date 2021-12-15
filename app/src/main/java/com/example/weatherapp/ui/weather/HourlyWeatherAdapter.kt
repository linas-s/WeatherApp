package com.example.weatherapp.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.entities.HourlyWeather
import com.example.weatherapp.databinding.ItemHourlyBinding

class HourlyWeatherAdapter : ListAdapter<HourlyWeather, HourlyWeatherAdapter.HourlyWeatherViewHolder> (HourlyWeatherComaprator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
       val binding =
           ItemHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)

       return HourlyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem != null){
            holder.bind(currentItem)
        }
    }

    class HourlyWeatherViewHolder(private val binding: ItemHourlyBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(hourlyWeather: HourlyWeather){
                binding.apply {
                    textViewTime.text = hourlyWeather.dt.toString()
                    textViewTemp.text = hourlyWeather.temp.toString()
                    textViewWind.text = hourlyWeather.wind_speed.toString()
                }
            }
    }

    class HourlyWeatherComaprator : DiffUtil.ItemCallback<HourlyWeather>(){
        override fun areItemsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather) =
            oldItem.hourlyWeatherId == newItem.hourlyWeatherId

        override fun areContentsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather) =
            oldItem == newItem
    }
}