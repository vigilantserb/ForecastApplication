package com.stameni.com.forecastapplication.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.forecastapplication.data.network.response.CurrentWeatherResponse
import com.stameni.com.forecastapplication.data.network.response.FutureWeatherResponse
import io.reactivex.disposables.Disposable

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    var subscription: Disposable

    fun fetchCurrentWeather(location: String, lang: String)

    fun fetchFutureWeather(location: String, lang: String)
}