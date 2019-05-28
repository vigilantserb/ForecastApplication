package com.stameni.com.forecastapplication.data.network.response

import com.google.gson.annotations.SerializedName
import com.stameni.com.forecastapplication.data.db.entity.WeatherLocation

data class FutureWeatherResponse(
    @SerializedName("forecast")
    val forecast: ForecastDaysContainer,
    @SerializedName("location")
    val location: WeatherLocation
)