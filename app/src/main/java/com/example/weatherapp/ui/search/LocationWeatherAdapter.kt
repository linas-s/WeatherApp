package com.example.weatherapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.LocationWeather
import com.example.weatherapp.databinding.ItemLocationBinding

class LocationWeatherAdapter : ListAdapter<LocationWeather, LocationWeatherAdapter.LocationViewHolder>(LocationComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class LocationViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(locationWeather: LocationWeather) {
            binding.apply {
                textViewCity.text = locationWeather.name
                textViewStateCountry.text =
                    if (locationWeather.state != null) locationWeather.state + ", " + locationWeather.country
                    else locationWeather.country
            }
        }
    }

    class LocationComparator : DiffUtil.ItemCallback<LocationWeather>(){
        override fun areItemsTheSame(oldItem: LocationWeather, newItem: LocationWeather) =
            oldItem.lat == newItem.lat && oldItem.lon ==newItem.lon
        //TODO(change to id later)

        override fun areContentsTheSame(oldItem: LocationWeather, newItem: LocationWeather) =
            oldItem == newItem

    }
}