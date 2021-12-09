package com.example.weatherapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locationWeather: List<LocationWeather>)

    @Query("SELECT * FROM location_weather WHERE name = :query")
    fun getWeatherLocations(query: String): Flow<List<LocationWeather>>

    @Query("DELETE FROM location_weather")
    suspend fun deleteAllLocations()
}