package com.example.weatherapp.ui.search

import androidx.lifecycle.*
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.entities.WeatherLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    private val searchEventChannel = Channel<SearchEvent>()
    val searchEvent = searchEventChannel.receiveAsFlow()

    val locations = currentQuery.switchMap { queryString ->
        repository.getLocations(queryString).asLiveData()
    }

    fun searchLocation(query: String){
        currentQuery.value = query
    }

    fun onLocationSelected(weatherLocation: WeatherLocation) = viewModelScope.launch {
        searchEventChannel.send(SearchEvent.NavigateToWeatherFragment(weatherLocation))
    }

    companion object {
        private const val DEFAULT_QUERY = "London"
    }

    sealed class SearchEvent {
        data class NavigateToWeatherFragment(val weatherLocation: WeatherLocation) : SearchEvent()
    }
}