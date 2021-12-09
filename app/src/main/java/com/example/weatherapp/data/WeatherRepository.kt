package com.example.weatherapp.data

import androidx.room.withTransaction
import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val db: WeatherDatabase
    ) {

    private val weatherDao = db.weatherDao()

    suspend fun getSearchResults(query: String): List<LocationWeather> =
        weatherApi.getWeatherLocations(query)

    fun getWeatherLocations(query: String) = networkBoundResource(
        query = {
            weatherDao.getWeatherLocations(query)
        },
        fetch = {
            weatherApi.getWeatherLocations(query)
        },
        saveFetchResult = { weatherLocations ->
            db.withTransaction {
                //weatherDao.deleteAllLocations()
                weatherDao.insertLocations(weatherLocations)
            }
        }
    )
}