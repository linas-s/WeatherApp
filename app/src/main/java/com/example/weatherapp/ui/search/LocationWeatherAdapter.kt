package com.example.weatherapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.entities.WeatherLocation
import com.example.weatherapp.databinding.ItemLocationBinding

class LocationAdapter(private val listener: OnItemClickListener) : ListAdapter<WeatherLocation, LocationAdapter.LocationViewHolder>(LocationComparator()) {

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

    inner class LocationViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val location = getItem(position)
                        listener.onItemClick(location)
                    }
                }
            }
        }

        fun bind(weatherLocation: WeatherLocation) {
            binding.apply {
                textViewCity.text = weatherLocation.name
                textViewStateCountry.text =
                    if (weatherLocation.state != null) weatherLocation.state + ", " + weatherLocation.country
                    else weatherLocation.country
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(weatherLocation: WeatherLocation)
    }

    class LocationComparator : DiffUtil.ItemCallback<WeatherLocation>(){
        override fun areItemsTheSame(oldItem: WeatherLocation, newItem: WeatherLocation) =
            oldItem.locationId == newItem.locationId

        override fun areContentsTheSame(oldItem: WeatherLocation, newItem: WeatherLocation) =
            oldItem == newItem

    }
}