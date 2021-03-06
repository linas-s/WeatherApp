package com.example.weatherapp.di

import android.app.Application
import androidx.room.Room
import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.data.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(WeatherApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): WeatherDatabase =
        Room.databaseBuilder(app, WeatherDatabase::class.java, "weather_database")
            .build()
}