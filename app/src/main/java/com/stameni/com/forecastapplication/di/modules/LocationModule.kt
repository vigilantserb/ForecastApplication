package com.stameni.com.forecastapplication.di.modules

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.stameni.com.forecastapplication.data.providers.LocationProvider
import com.stameni.com.forecastapplication.data.providers.LocationProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationModule {

    @Provides
    @Singleton
    fun getFusedLocationProviderClient(context: Context): FusedLocationProviderClient = FusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun getLocationProvider(context: Context): LocationProvider =
            LocationProviderImpl(context)
}