package com.example.weatherapp.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.data.entities.HourlyWeather
import com.example.weatherapp.databinding.ItemHourlyBinding
import java.text.SimpleDateFormat
import java.util.*

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

        private val sdf = SimpleDateFormat("HH:mm")


        fun bind(hourlyWeather: HourlyWeather){
                binding.apply {
                    Glide.with(imageView)
                        .load(hourlyWeather.firstWeather?.url)
                        .into(imageView)
                    textViewTime.text = sdf.format(Date(hourlyWeather.dt * 1000))
                    textViewTemp.text = hourlyWeather.readableTemp
                    textViewWind.text = hourlyWeather.formattedWindSpeed
                    when{
                        hourlyWeather.wind_deg < 22 -> imageView2.setImageResource(R.drawable.ic_north)
                        hourlyWeather.wind_deg < 67 -> imageView2.setImageResource(R.drawable.ic_north_east)
                        hourlyWeather.wind_deg < 112 -> imageView2.setImageResource(R.drawable.ic_east)
                        hourlyWeather.wind_deg < 157 -> imageView2.setImageResource(R.drawable.ic_south_east)
                        hourlyWeather.wind_deg < 202 -> imageView2.setImageResource(R.drawable.ic_south)
                        hourlyWeather.wind_deg < 247 -> imageView2.setImageResource(R.drawable.ic_south_west)
                        hourlyWeather.wind_deg < 292 -> imageView2.setImageResource(R.drawable.ic_west)
                        hourlyWeather.wind_deg < 337 -> imageView2.setImageResource(R.drawable.ic_north_west)
                        hourlyWeather.wind_deg <= 360 -> imageView2.setImageResource(R.drawable.ic_north)
                    }
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