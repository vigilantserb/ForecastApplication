package com.stameni.com.forecastapplication.di.components

import com.stameni.com.forecastapplication.di.modules.*
import com.stameni.com.forecastapplication.ui.MainActivity
import com.stameni.com.forecastapplication.ui.weather.current.CurrentWeatherFragment
import com.stameni.com.forecastapplication.ui.weather.future.list.FutureListWeatherFragment
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Subcomponent(
    modules = [
        NetworkModule::class,
        RoomModule::class,
        ViewModelModule::class,
        PresentationModule::class,
        LocationModule::class
    ]
)
interface PresentationComponent {
    fun inject(currentWeatherFragment: CurrentWeatherFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(futureListWeatherFragment: FutureListWeatherFragment)
}