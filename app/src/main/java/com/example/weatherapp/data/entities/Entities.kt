package com.example.weatherapp.data.entities

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity(tableName = "location", indices = [Index(value = ["lat", "lon"], unique = true)])
@Parcelize
data class Location(
    @PrimaryKey(autoGenerate = true) val locationId: Long,
    var lat: Double,
    var lon: Double,
    val name: String,
    val country: String,
    val state: String?
) : Parcelable

@Entity(tableName = "current_weather")
data class CurrentWeather @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true) val currentWeatherId: Long,
    var locationId: Long,
    val dt: Long,
    val temp: Double,
    val feels_like: Double,
    val wind_speed: Double,
    @Ignore var date: java.util.Date? = null,
    @Ignore var weather: List<WeatherInfo>? = null,
    @Embedded var firstWeather : WeatherInfo?
)

@Entity(tableName = "hourly_weather")
data class HourlyWeather @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true) val hourlyWeatherId: Long,
    var locationId: Long,
    val dt: Long,
    val temp: Double,
    val feels_like: Double,
    val wind_speed: Double,
    @Ignore var date: java.util.Date? = null,
    @Ignore var weather: List<WeatherInfo>? = null,
    @Embedded var firstWeather : WeatherInfo?
)

@Entity(tableName = "daily_weather")
data class DailyWeather @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true) val dailyWeatherId: Long,
    var locationId: Long,
    val dt: Long,
    val wind_speed: Double,
    @Ignore var date: java.util.Date? = null,
    //@Embedded val temp: Temp,
    @Ignore var weather: List<WeatherInfo>? = null,
    @Embedded var firstWeather : WeatherInfo?
)

@Entity(tableName = "daily_temp")
data class Temp(
    @PrimaryKey(autoGenerate = true) val tempId: Long,
    val day: Double,
    val night: Double
)

@Entity(tableName = "weather_info")
data class WeatherInfo(
    @PrimaryKey val id: Long,
    val main: String,
    val icon: String
)

data class WeatherResponse(
    val lat: Double,
    val lon: Double,
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>
)