package com.example.weatherapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.entities.Location
import com.example.weatherapp.databinding.ItemLocationBinding

class LocationAdapter(private val listener: OnItemClickListener) : ListAdapter<Location, LocationAdapter.LocationViewHolder>(LocationComparator()) {

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

        fun bind(location: Location) {
            binding.apply {
                textViewCity.text = location.name
                textViewStateCountry.text =
                    if (location.state != null) location.state + ", " + location.country
                    else location.country
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(location: Location)
    }

    class LocationComparator : DiffUtil.ItemCallback<Location>(){
        override fun areItemsTheSame(oldItem: Location, newItem: Location) =
            oldItem.locationId == newItem.locationId

        override fun areContentsTheSame(oldItem: Location, newItem: Location) =
            oldItem == newItem

    }
}