package com.stameni.com.forecastapplication.data.repository

import androidx.lifecycle.LiveData
import com.stameni.com.forecastapplication.data.db.entity.WeatherLocation
import com.stameni.com.forecastapplication.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.stameni.com.forecastapplication.data.db.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

interface ForecastRepository {
    fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
    fun getFutureWeatherList(startDate: LocalDate, metric: Boolean): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>
    fun getWeatherLocation(): LiveData<WeatherLocation>
}