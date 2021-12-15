package com.example.weatherapp.data

import androidx.room.*
import com.example.weatherapp.data.entities.CurrentWeather
import com.example.weatherapp.data.entities.DailyWeather
import com.example.weatherapp.data.entities.HourlyWeather
import com.example.weatherapp.data.entities.Location
import com.example.weatherapp.data.entities.relations.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocations(location: List<Location>)

    @Query("SELECT * FROM location WHERE name LIKE '%' || :query || '%'")
    fun getLocations(query: String): Flow<List<Location>>

    @Query("SELECT * FROM location WHERE locationId = :locationId")
    suspend fun getLocation(locationId: Long): Location

    @Query("DELETE FROM location")
    suspend fun deleteAllLocations()

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeather: CurrentWeather)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyWeather(dailyWeather: List<DailyWeather>)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeather(hourlyWeather: List<HourlyWeather>)

    @Query("SELECT * FROM current_weather WHERE locationId = :locationId ORDER BY dt DESC LIMIT 1")
    fun getCurrentWeather(locationId: Long): Flow<CurrentWeather>

    @Transaction
    @Query("SELECT * FROM location WHERE locationId = :locationId")
    fun getWeather(locationId: Long): Flow<Weather>
}