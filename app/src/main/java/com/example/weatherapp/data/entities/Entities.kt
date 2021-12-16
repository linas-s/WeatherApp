package com.example.weatherapp.data.entities

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity(tableName = "location", indices = [Index(value = ["lat", "lon"], unique = true)])
@Parcelize
data class WeatherLocation(
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
    val wind_deg: Int,
    val wind_speed: Double,
    @Ignore var weather: List<WeatherInfo>? = null,
    @Embedded var firstWeather : WeatherInfo?
){
    val readableTemp: String get() = temp.toInt().toString()
}

@Entity(tableName = "hourly_weather")
data class HourlyWeather @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true) val hourlyWeatherId: Long,
    var locationId: Long,
    val dt: Long,
    val temp: Double,
    val feels_like: Double,
    val wind_speed: Double,
    val wind_deg: Int,
    @Ignore var weather: List<WeatherInfo>? = null,
    @Embedded var firstWeather : WeatherInfo?
){
    val readableTemp: String get() = temp.toInt().toString() + "°"
    val formattedWindSpeed: String get() = "%.1f".format(wind_speed) + " m/s"
}

@Entity(tableName = "daily_weather")
data class DailyWeather @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true) val dailyWeatherId: Long,
    var locationId: Long,
    val dt: Long,
    val wind_speed: Double,
    val wind_deg: Int,
    @Embedded var temp: Temp?,
    @Ignore var weather: List<WeatherInfo>? = null,
    @Embedded var firstWeather : WeatherInfo?
)

@Entity(tableName = "daily_temp")
data class Temp(
    @PrimaryKey(autoGenerate = true) val tempId: Long,
    val day: Double,
    val night: Double
){
    val readableDayTemp: String get() = day.toInt().toString() + "°"
    val readableNightTemp: String get() = night.toInt().toString() + "°"
}

@Entity(tableName = "weather_info")
data class WeatherInfo(
    @PrimaryKey val id: Long,
    val main: String,
    val icon: String
){
    val url: String get() = "https://openweathermap.org/img/wn/$icon.png"
}

data class WeatherResponse(
    val lat: Double,
    val lon: Double,
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>
)