package com.stameni.com.forecastapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stameni.com.forecastapplication.data.db.entity.CURRENT_WEATHER_ID
import com.stameni.com.forecastapplication.data.db.entity.CurrentWeatherEntry
import com.stameni.com.forecastapplication.data.db.unitlocalized.current.ImperialCurrentWeatherEntry
import com.stameni.com.forecastapplication.data.db.unitlocalized.current.MetricCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("SELECT * FROM current_weather WHERE id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>

    @Query("SELECT * FROM current_weather WHERE id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>
}