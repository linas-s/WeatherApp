package com.example.weatherapp.data.entities

data class WeatherResponse(
    val lat: Double,
    val lon: Double,
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>
)