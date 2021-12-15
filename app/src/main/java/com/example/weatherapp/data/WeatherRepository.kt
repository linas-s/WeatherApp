package com.example.weatherapp.data

import androidx.room.withTransaction
import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.data.entities.CurrentWeather
import com.example.weatherapp.data.entities.DailyWeather
import com.example.weatherapp.data.entities.Location
import com.example.weatherapp.data.entities.relations.Weather
import com.example.weatherapp.util.networkBoundResource
import java.math.RoundingMode
import java.security.Timestamp
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val db: WeatherDatabase,
    ) {

    private val weatherDao = db.weatherDao()
    private val df = DecimalFormat("#.####")

    fun getLocations(query: String) = networkBoundResource(
        query = {
            weatherDao.getLocations(query)
        },
        fetch = {
            weatherApi.getLocations(query)
        },
        saveFetchResult = { locations ->
            db.withTransaction {
                df.roundingMode = RoundingMode.HALF_UP
                locations.map {
                    it.lat = df.format(it.lat).toDouble()
                    it.lon = df.format(it.lon).toDouble()
                }
                weatherDao.insertLocations(locations)
            }
        }
    )

    fun getWeather(location: Location) = networkBoundResource(
            query = {
                weatherDao.getWeather(location.locationId)
            },
            fetch = {
                weatherApi.getWeather(location.lat, location.lon)
            },
            saveFetchResult = { weather ->
                db.withTransaction {
                    weather.apply {
                        current.locationId = location.locationId
                        current.firstWeather = current.weather?.get(0)
                        current.date = Date(current.dt * 1000)
                        hourly.map {
                            it.locationId = location.locationId
                            it.firstWeather = it.weather?.get(0)
                            it.date = Date(it.dt * 1000)
                        }
                        daily.map {
                            it.locationId = location.locationId
                            it.firstWeather = it.weather?.get(0)
                            it.date = Date(it.dt * 1000)
                        }
                    }
                    weatherDao.insertCurrentWeather(weather.current)
                    weatherDao.insertDailyWeather(weather.daily)
                    weatherDao.insertHourlyWeather(weather.hourly)
                }
            }
        )
}