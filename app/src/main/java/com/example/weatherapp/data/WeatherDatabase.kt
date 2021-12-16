package com.example.weatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.entities.*

@Database(
    entities = [
        WeatherLocation::class,
        CurrentWeather::class,
        HourlyWeather::class,
        DailyWeather::class,
        Temp::class,
        WeatherInfo::class],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}