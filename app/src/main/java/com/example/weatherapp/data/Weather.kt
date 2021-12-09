package com.example.weatherapp.data

import androidx.room.PrimaryKey

data class LocationWeather(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val lat: Double,
    val lon: Double,
    val name: String,
    val country: String,
    val state: String?,
    val current: Current?,
    val hourly: List<Hourly?>,
    val daily: List<Daily?>
)

data class Current(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val locationId: Long?,
    val dt: Long,
    val temp: Double,
    val feels_like: Double,
    val wind_speed: Double,
    val weather: Weather
)

data class Weather(
    @PrimaryKey val id: Long,
    val main: String,
    val icon: String
)

data class Hourly(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val locationId: Long?,
    val dt: Long,
    val temp: Double,
    val feels_like: Double,
    val weather: Weather
)

data class Daily(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val locationId: Long?,
    val dt: Long,
    val temp: Temp,
    val wind_speed: Double,
    val weather: Weather,
)

data class Temp(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val dailyId: Long?,
    val day: Double,
    val night: Double
)