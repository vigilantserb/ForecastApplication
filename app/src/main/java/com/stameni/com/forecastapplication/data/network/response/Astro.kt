package com.stameni.com.forecastapplication.data.network.response

import com.google.gson.annotations.SerializedName

data class Astro(
    @SerializedName("moonrise")
    val moonrise: String,
    @SerializedName("moonset")
    val moonset: String,
    @SerializedName("sunrise")
    val sunrise: String,
    @SerializedName("sunset")
    val sunset: String
)