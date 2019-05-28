package com.stameni.com.forecastapplication.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.EmptyResultSetException
import com.stameni.com.forecastapplication.common.TAG
import com.stameni.com.forecastapplication.data.db.CurrentWeatherDao
import com.stameni.com.forecastapplication.data.db.FutureWeatherDao
import com.stameni.com.forecastapplication.data.db.WeatherLocationDao
import com.stameni.com.forecastapplication.data.db.entity.WeatherLocation
import com.stameni.com.forecastapplication.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.stameni.com.forecastapplication.data.db.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import com.stameni.com.forecastapplication.data.network.FUTURE_DAYS_COUNT
import com.stameni.com.forecastapplication.data.network.WeatherNetworkDataSource
import com.stameni.com.forecastapplication.data.network.response.FutureWeatherResponse
import com.stameni.com.forecastapplication.data.providers.LocationProvider
import com.stameni.com.forecastapplication.internals.NoConnectivityException
import org.threeten.bp.ZonedDateTime
import java.util.*
import io.reactivex.schedulers.Schedulers
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.threeten.bp.LocalDate
import java.lang.Exception


class ForecastRepositoryImpl(
    private val networkDataSource: WeatherNetworkDataSource,
    private val currentWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private var locationProvider: LocationProvider
) : ForecastRepository {

    init {

        networkDataSource.apply {
            downloadedCurrentWeather.observeForever {
                Completable.fromAction {
                    currentWeatherDao.upsert(it.currentWeatherEntry)
                    weatherLocationDao.upsert(it.location)
                }.subscribeOn(Schedulers.io()).subscribe({}, { err ->
                    onRetrieveCurrentWeatherError(err as Exception)
                })
            }
            downloadedFutureWeather.observeForever {
                println("ts")
                Completable.fromAction {
                    val today = LocalDate.now()
                    futureWeatherDao.deleteOldEntries(today)

                    futureWeatherDao.insert(it.forecast.entries)
                    weatherLocationDao.upsert(it.location)
                }.subscribeOn(Schedulers.io()).subscribe()
            }
        }
    }

    private fun persistFetchedFutureWeather(it: FutureWeatherResponse) {
    }

    override fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        initWeatherData()
        if (metric) {
            return currentWeatherDao.getWeatherMetric()
        }
        return currentWeatherDao.getWeatherImperial()
    }

    override fun getWeatherLocation(): LiveData<WeatherLocation> {
        return weatherLocationDao.getWeatherLocation()
    }

    override fun getFutureWeatherList(
        startDate: LocalDate,
        metric: Boolean
    ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
        initWeatherData()
        if (metric)
            return futureWeatherDao.getSimpleWeatherForecastMetric(startDate)
        return futureWeatherDao.getSimpleWeatherForecastImperial(startDate)
    }

    @SuppressLint("CheckResult")
    private fun initWeatherData() {
        val disposable = weatherLocationDao
            .getObservableWeatherLocation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { err -> onRetrieveCurrentWeatherError(err as Exception) }
            .subscribe(
                { it ->
                    if (it == null || locationProvider.hasLocationChanged(it)) {
                        fetchCurrentWeather()
                        fetchFutureWeather()
                    }
                    if (!isFetchCurrentNeeded(it.zonedDateTime)) {
                        fetchCurrentWeather()
                    }
                    isFutureFetchNeeded()
                },
                { err ->
                    onInitWeatherDataError(err as Exception)
                })
    }

    private fun onInitWeatherDataError(exception: Exception) {
        when (exception) {
            is NoConnectivityException -> Log.e("Connectivity", "No internet connection", exception)
            is EmptyResultSetException -> {
                fetchCurrentWeather()
                fetchFutureWeather()
            }
            else -> Log.e("Unknown", "Unknown error", exception)
        }
    }

    private fun onRetrieveCurrentWeatherError(err: Exception) {
        if (err is NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", err)
        } else {
            Log.e("Unknown", "Unknown error", err)
        }
    }

    @SuppressLint("CheckResult")
    private fun isFutureFetchNeeded(){
        val today = LocalDate.now()
        futureWeatherDao
            .countFutureWeather(today)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { err -> onRetrieveCurrentWeatherError(err as Exception) }
            .subscribe(
                {
                    if (it < 7)
                        fetchFutureWeather()
                },
                { err ->
                    onRetrieveCurrentWeatherError(err as Exception)
                })
    }

    private fun fetchFutureWeather() {
        networkDataSource.fetchFutureWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private fun fetchCurrentWeather() {
        networkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return thirtyMinutesAgo.isBefore(lastFetchTime)
    }
}