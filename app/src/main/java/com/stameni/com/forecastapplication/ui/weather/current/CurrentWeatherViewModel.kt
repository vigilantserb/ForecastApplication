package com.stameni.com.forecastapplication.ui.weather.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.stameni.com.forecastapplication.data.db.entity.WeatherLocation
import com.stameni.com.forecastapplication.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.stameni.com.forecastapplication.data.providers.UnitProvider
import com.stameni.com.forecastapplication.data.repository.ForecastRepository
import com.stameni.com.forecastapplication.internals.UnitSystem

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    fun getCurrentWeather(): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return forecastRepository.getCurrentWeather(isMetric)
    }

    fun getWeatherLocation(): LiveData<WeatherLocation> {
        return forecastRepository.getWeatherLocation()
    }
}
