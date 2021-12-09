package com.example.weatherapp.data

import com.example.weatherapp.api.WeatherApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {

    suspend fun getSearchResults(query: String): List<LocationWeather> =
        weatherApi.searchLocation(query)
}