package com.example.weatherapp.ui.weather

import androidx.lifecycle.*
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.entities.WeatherLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    state: SavedStateHandle
) : ViewModel() {
    private val currentLocation = state.getLiveData<WeatherLocation>("location")

    val weather = currentLocation.switchMap { location ->
        repository.getWeather(location).asLiveData()
    }

    fun getDayOfWeek(timestamp: Long?): String? =
        SimpleDateFormat("EEEE").format((timestamp ?: 0) * 1000)
}