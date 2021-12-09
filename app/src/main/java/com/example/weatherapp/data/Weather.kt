package com.example.weatherapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_weather", primaryKeys = ["lat", "lon"])
data class LocationWeather(
    val lat: Double,
    val lon: Double,
    val name: String,
    val country: String,
    val state: String?
    //val current: Current?,
    //val hourly: List<Hourly?>,
    //val daily: List<Daily?>
)

@Entity(tableName = "current_weather")
data class Current(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val locationId: Long?,
    val dt: Long,
    val temp: Double,
    val feels_like: Double,
    val wind_speed: Double,
    val weather: Weather
)

@Entity(tableName = "weather_description")
data class Weather(
    @PrimaryKey val id: Long,
    val main: String,
    val icon: String
)

@Entity(tableName = "hourly_weather")
data class Hourly(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val locationId: Long?,
    val dt: Long,
    val temp: Double,
    val feels_like: Double,
    val weather: Weather
)

@Entity(tableName = "daily_weather")
data class Daily(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val locationId: Long?,
    val dt: Long,
    val temp: Temp,
    val wind_speed: Double,
    val weather: Weather,
)

@Entity(tableName = "temp_daily")
data class Temp(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val dailyId: Long?,
    val day: Double,
    val night: Double
)