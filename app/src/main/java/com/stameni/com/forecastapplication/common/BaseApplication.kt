package com.stameni.com.forecastapplication.common

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.stameni.com.forecastapplication.di.components.ApplicationComponent
import com.stameni.com.forecastapplication.di.components.DaggerApplicationComponent
import com.stameni.com.forecastapplication.di.modules.AppModule

const val TAG = "ForecastApplication"

class BaseApplication: Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        AndroidThreeTen.init(this)
    }
}
