package com.example.weatherapp.ui.weather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.entities.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    state: SavedStateHandle
) : ViewModel() {
    private val currentLocation = state.getLiveData<Location>("location")

    val weather = currentLocation.switchMap { location ->
        repository.getWeather(location).asLiveData()
    }

}