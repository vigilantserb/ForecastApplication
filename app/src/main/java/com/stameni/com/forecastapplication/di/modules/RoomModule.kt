package com.stameni.com.forecastapplication.di.modules

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stameni.com.forecastapplication.data.db.CurrentWeatherDao
import com.stameni.com.forecastapplication.data.db.ForecastDatabase
import com.stameni.com.forecastapplication.data.db.FutureWeatherDao
import com.stameni.com.forecastapplication.data.db.WeatherLocationDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun getForecastDatabase(context: Context): ForecastDatabase =
        ForecastDatabase(context) as ForecastDatabase

    @Singleton
    @Provides
    fun getCurrentWeatherDao(forecastDatabase: ForecastDatabase): CurrentWeatherDao =
        forecastDatabase.currentWeatherDao()

    @Singleton
    @Provides
    fun getFutureWeatherDao(forecastDatabase: ForecastDatabase): FutureWeatherDao =
        forecastDatabase.futureWeatherDao()

    @Singleton
    @Provides
    fun getWeatherLocationDao(forecastDatabase: ForecastDatabase): WeatherLocationDao =
        forecastDatabase.weatherLocationDao()
}