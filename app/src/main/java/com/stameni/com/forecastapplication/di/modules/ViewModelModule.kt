package com.stameni.com.forecastapplication.di.modules

import android.content.Context
import com.stameni.com.forecastapplication.data.providers.UnitProvider
import com.stameni.com.forecastapplication.data.providers.UnitProviderImpl
import com.stameni.com.forecastapplication.data.repository.ForecastRepository
import com.stameni.com.forecastapplication.ui.weather.current.ViewModelFactory
import com.stameni.com.forecastapplication.ui.weather.future.list.FutureListWeatherViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun getUnitProvider(context: Context): UnitProvider = UnitProviderImpl(context)

    @Provides
    fun getCurrentWeatherViewModelFactory(
        forecastRepository: ForecastRepository,
        unitProvider: UnitProvider
    ): ViewModelFactory = ViewModelFactory(forecastRepository, unitProvider)

    @Provides
    fun getFutureWeatherViewModelFactory(
        forecastRepository: ForecastRepository,
        unitProvider: UnitProvider
    ): FutureListWeatherViewModelFactory = FutureListWeatherViewModelFactory(forecastRepository, unitProvider)
}
