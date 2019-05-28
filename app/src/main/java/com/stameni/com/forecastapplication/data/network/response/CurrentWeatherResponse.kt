package com.stameni.com.forecastapplication.data.network.response

import com.google.gson.annotations.SerializedName
import com.stameni.com.forecastapplication.data.db.entity.CurrentWeatherEntry
import com.stameni.com.forecastapplication.data.db.entity.WeatherLocation

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    @SerializedName("location")
    val location: WeatherLocation
)