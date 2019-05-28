package com.stameni.com.forecastapplication.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stameni.com.forecastapplication.common.TAG
import com.stameni.com.forecastapplication.data.network.response.CurrentWeatherResponse
import com.stameni.com.forecastapplication.data.network.response.FutureWeatherResponse
import com.stameni.com.forecastapplication.internals.NoConnectivityException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

const val FUTURE_DAYS_COUNT = 7
class WeatherNetworkDataSourceImpl
    (private val weatherApi: WeatherApi) : WeatherNetworkDataSource {

    val _downloadedCurrentWeather: MutableLiveData<CurrentWeatherResponse> = MutableLiveData()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override lateinit var subscription: Disposable

    override fun fetchCurrentWeather(location: String, lang: String) {
        subscription = weatherApi.getCurrentWeather(location, lang)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveCurrentWeatherStart() }
            .doOnTerminate { onRetrieveCurrentWeatherFinish() }
            .doOnError { err -> onRetrieveCurrentWeatherError(err as Exception) }
            .subscribe(
                { result -> onRetrieveCurrentWeatherSuccess(result) },
                { err -> onRetrieveCurrentWeatherError(err as Exception)
                })
    }

    private fun onRetrieveCurrentWeatherSuccess(result: CurrentWeatherResponse) {
        _downloadedCurrentWeather.value = result
    }

    private fun onRetrieveCurrentWeatherError(err: Exception) {
        if (err is NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", err)
        } else {
            Log.e("Unknown", "Unknown error", err)
        }
    }

    private fun onRetrieveCurrentWeatherFinish() {

    }

    private fun onRetrieveCurrentWeatherStart() {
        Log.d(TAG, "Fetching current weather")
    }

    val _downloadedFutureWeather: MutableLiveData<FutureWeatherResponse> = MutableLiveData()

    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override fun fetchFutureWeather(location: String, lang: String) {
        subscription = weatherApi.getFutureWeather(location, FUTURE_DAYS_COUNT,lang)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveFutureWeatherStart() }
            .doOnTerminate { onRetrieveFutureWeatherFinish() }
            .doOnError { err -> onRetrieveFutureWeatherError(err as Exception) }
            .subscribe(
                { result -> onRetrieveFutureWeatherSuccess(result) },
                { err -> onRetrieveFutureWeatherError(err as Exception)
                })
    }

    private fun onRetrieveFutureWeatherSuccess(result: FutureWeatherResponse) {
        _downloadedFutureWeather.value = result
    }

    private fun onRetrieveFutureWeatherError(exception: Exception) {
        if (exception is NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", exception)
        } else {
            Log.e("Unknown", "Unknown error", exception)
        }
    }

    private fun onRetrieveFutureWeatherFinish() {

    }

    private fun onRetrieveFutureWeatherStart() {
        Log.d(TAG, "Fetching future weather")
    }
}