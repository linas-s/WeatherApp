package com.example.weatherapp.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.data.LocationWeather
import com.example.weatherapp.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    repository: WeatherRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val weatherLocations = currentQuery.switchMap { queryString ->
        repository.getWeatherLocations(queryString).asLiveData()
    }

    fun searchLocation(query: String){
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = ""
    }
}