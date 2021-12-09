package com.example.weatherapp.api

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.LocationWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        const val API_KEY = BuildConfig.API_KEY
    }

    @GET("geo/1.0/direct")
    suspend fun getWeatherLocations(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = API_KEY,
        @Query("limit") limit: Int = 5
    ): List<LocationWeather>
}