package com.example.weatherapp.data

import androidx.room.withTransaction
import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.data.entities.WeatherLocation
import com.example.weatherapp.util.networkBoundResource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val db: WeatherDatabase,
    ) {

    private val weatherDao = db.weatherDao()

    fun getLocations(query: String) = networkBoundResource(
        query = {
            weatherDao.getLocations(query)
        },
        fetch = {
            weatherApi.getLocations(query)
        },
        saveFetchResult = { locations ->
            db.withTransaction {
                weatherDao.insertLocations(locations)
            }
        }
    )

    fun getWeather(weatherLocation: WeatherLocation) = networkBoundResource(
            query = {
                weatherDao.getWeather(weatherLocation.locationId)
            },
            fetch = {
                weatherApi.getWeather(weatherLocation.lat, weatherLocation.lon)
            },
            saveFetchResult = { weather ->
                db.withTransaction {
                    weather.apply {
                        current.locationId = weatherLocation.locationId
                        current.firstWeather = current.weather?.get(0)
                        hourly.map {
                            it.locationId = weatherLocation.locationId
                            it.firstWeather = it.weather?.get(0)
                        }
                        daily.map {
                            it.locationId = weatherLocation.locationId
                            it.firstWeather = it.weather?.get(0)
                        }
                    }
                    weatherDao.insertCurrentWeather(weather.current)

                    weatherDao.deleteDailyWeather(weatherLocation.locationId)
                    weatherDao.insertDailyWeather(weather.daily)

                    weatherDao.deleteHourlyWeather(weatherLocation.locationId)
                    weatherDao.insertHourlyWeather(weather.hourly)
                }
            }
        )
}