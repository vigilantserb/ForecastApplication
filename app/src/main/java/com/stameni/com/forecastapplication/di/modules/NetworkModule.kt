package com.stameni.com.forecastapplication.di.modules

import android.content.Context
import com.stameni.com.forecastapplication.data.db.CurrentWeatherDao
import com.stameni.com.forecastapplication.data.db.FutureWeatherDao
import com.stameni.com.forecastapplication.data.db.WeatherLocationDao
import com.stameni.com.forecastapplication.data.network.WeatherApi
import com.stameni.com.forecastapplication.data.network.WeatherNetworkDataSource
import com.stameni.com.forecastapplication.data.network.WeatherNetworkDataSourceImpl
import com.stameni.com.forecastapplication.data.providers.LocationProvider
import com.stameni.com.forecastapplication.data.providers.LocationProviderImpl
import com.stameni.com.forecastapplication.data.repository.ForecastRepository
import com.stameni.com.forecastapplication.data.repository.ForecastRepositoryImpl
import com.stameni.com.forecastapplication.internals.ConnectivityInterceptor
import com.stameni.com.forecastapplication.internals.ConnectivityInterceptorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun getConectivityInterceptor(context: Context): ConnectivityInterceptor = ConnectivityInterceptorImpl(context)

    @Singleton
    @Provides
    fun getWeatherApi(connectivityInterceptor: ConnectivityInterceptor): WeatherApi =
        WeatherApi(connectivityInterceptor)

    @Provides
    @Singleton
    fun getWeatherNetworkDataSource(weatherApi: WeatherApi): WeatherNetworkDataSource =
        WeatherNetworkDataSourceImpl(weatherApi)

    @Provides
    fun getLocationProvider(context: Context) =
        LocationProviderImpl(context)

    @Provides
    @Singleton
    fun getForecastRepository(
        weatherNetworkDataSource: WeatherNetworkDataSource,
        currentWeatherDao: CurrentWeatherDao,
        futureWeatherDao: FutureWeatherDao,
        weatherLocationDao: WeatherLocationDao,
        locationProvider: LocationProvider
    ): ForecastRepository =
        ForecastRepositoryImpl(
            weatherNetworkDataSource,
            currentWeatherDao,
            futureWeatherDao,
            weatherLocationDao,
            locationProvider
        )
}