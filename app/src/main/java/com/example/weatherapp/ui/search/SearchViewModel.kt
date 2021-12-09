package com.example.weatherapp.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.data.LocationWeather
import com.example.weatherapp.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    repository: WeatherRepository
) : ViewModel() {

    private val weatherLocationsLiveData = MutableLiveData<List<LocationWeather>>()
    val weatherLocations: LiveData<List<LocationWeather>> = weatherLocationsLiveData

    init {
        viewModelScope.launch {
            val weatherLocations = repository.getSearchResults("London")
            weatherLocationsLiveData.value = weatherLocations
        }
    }
}