package com.example.weatherapp.data.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.weatherapp.data.entities.CurrentWeather
import com.example.weatherapp.data.entities.DailyWeather
import com.example.weatherapp.data.entities.HourlyWeather
import com.example.weatherapp.data.entities.Location

data class Weather(
    @Embedded val location: Location?,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val currentWeather: CurrentWeather?,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val hourlyWeather: List<HourlyWeather?>,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "locationId"
    )
    val dailyWeather: List<DailyWeather?>
)
