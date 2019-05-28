package com.stameni.com.forecastapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stameni.com.forecastapplication.data.db.entity.CurrentWeatherEntry
import com.stameni.com.forecastapplication.data.db.entity.FutureWeatherEntry
import com.stameni.com.forecastapplication.data.db.entity.WeatherLocation

@Database(entities = [CurrentWeatherEntry::class, FutureWeatherEntry::class, WeatherLocation::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao
    abstract fun futureWeatherDao(): FutureWeatherDao

    companion object {
        private var instance: RoomDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context): RoomDatabase {
            return Room.databaseBuilder(context.applicationContext, ForecastDatabase::class.java, "forecast.db").build()
        }
    }
}