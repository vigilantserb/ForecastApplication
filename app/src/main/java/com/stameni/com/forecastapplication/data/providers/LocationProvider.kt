package com.stameni.com.forecastapplication.data.providers

import com.stameni.com.forecastapplication.data.db.entity.WeatherLocation

interface LocationProvider {
    fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    fun getPreferredLocationString(): String
}