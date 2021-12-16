package com.example.weatherapp.data

import androidx.room.*
import com.example.weatherapp.data.entities.CurrentWeather
import com.example.weatherapp.data.entities.DailyWeather
import com.example.weatherapp.data.entities.HourlyWeather
import com.example.weatherapp.data.entities.WeatherLocation
import com.example.weatherapp.data.entities.relations.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocations(weatherLocation: List<WeatherLocation>)

    @Query("SELECT * FROM location WHERE name LIKE '%' || :query || '%'")
    fun getLocations(query: String): Flow<List<WeatherLocation>>

    @Query("SELECT * FROM location WHERE locationId = :locationId")
    suspend fun getLocation(locationId: Long): WeatherLocation

    @Query("DELETE FROM location")
    suspend fun deleteAllLocations()

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeather: CurrentWeather)

    @Query ("DELETE FROM daily_weather WHERE locationId = :locationId")
    suspend fun deleteDailyWeather(locationId: Long)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyWeather(dailyWeather: List<DailyWeather>)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeather(hourlyWeather: List<HourlyWeather>)

    @Query ("DELETE FROM hourly_weather WHERE locationId = :locationId")
    suspend fun deleteHourlyWeather(locationId: Long)

    @Transaction
    @Query("SELECT * FROM location WHERE locationId = :locationId")
    fun getWeather(locationId: Long): Flow<Weather>
}