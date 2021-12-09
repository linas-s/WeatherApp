package com.example.weatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        LocationWeather::class],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}