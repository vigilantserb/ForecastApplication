package com.stameni.com.forecastapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stameni.com.forecastapplication.data.db.entity.WEATHER_LOCATION_ID
import com.stameni.com.forecastapplication.data.db.entity.WeatherLocation
import io.reactivex.Single

@Dao
interface WeatherLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation: WeatherLocation)

    @Query("SELECT * FROM weather_location WHERE id = $WEATHER_LOCATION_ID")
    fun getWeatherLocation(): LiveData<WeatherLocation>

    @Query("SELECT * FROM weather_location WHERE id = $WEATHER_LOCATION_ID")
    fun getObservableWeatherLocation(): Single<WeatherLocation>
}