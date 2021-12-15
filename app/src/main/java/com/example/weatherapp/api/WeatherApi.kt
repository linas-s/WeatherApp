package com.example.weatherapp.api

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.entities.Location
import com.example.weatherapp.data.entities.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
        const val API_KEY = BuildConfig.API_KEY
    }

    @GET("geo/1.0/direct")
    suspend fun getLocations(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = API_KEY,
        @Query("limit") limit: Int = 5
    ): List<Location>

    @GET("data/2.5/onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = API_KEY,
        @Query("exclude") exclude : String = "minutely,alerts",
        @Query("units") units: String = "metric"
    ): WeatherResponse
}