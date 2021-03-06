package com.stameni.com.forecastapplication.data.network.response

import com.google.gson.annotations.SerializedName
import com.stameni.com.forecastapplication.data.db.entity.FutureWeatherEntry

data class ForecastDaysContainer(
    @SerializedName("forecastday")
    val entries: List<FutureWeatherEntry>
)