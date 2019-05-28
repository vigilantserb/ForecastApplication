package com.stameni.com.forecastapplication.ui.weather.future.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel;
import com.stameni.com.forecastapplication.data.db.entity.WeatherLocation
import com.stameni.com.forecastapplication.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.stameni.com.forecastapplication.data.db.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import com.stameni.com.forecastapplication.data.providers.UnitProvider
import com.stameni.com.forecastapplication.data.repository.ForecastRepository
import com.stameni.com.forecastapplication.internals.UnitSystem
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    private val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    fun getFutureWeather(): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
        return forecastRepository.getFutureWeatherList(LocalDate.now(), isMetric)
    }

    fun getWeatherLocation(): LiveData<WeatherLocation> {
        return forecastRepository.getWeatherLocation()
    }
}
