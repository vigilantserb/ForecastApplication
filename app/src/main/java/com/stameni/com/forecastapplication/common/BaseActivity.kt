package com.stameni.com.forecastapplication.common

import androidx.appcompat.app.AppCompatActivity
import com.stameni.com.forecastapplication.di.components.ApplicationComponent
import com.stameni.com.forecastapplication.di.components.PresentationComponent
import com.stameni.com.forecastapplication.di.modules.*

abstract class BaseActivity: AppCompatActivity() {

    protected fun getPresentationComponent(): PresentationComponent {
        return getApplicationComponent()
            .newPresentationComponent(PresentationModule(this), NetworkModule(), RoomModule(), ViewModelModule(), LocationModule())

    }

    private fun getApplicationComponent(): ApplicationComponent {
        return (this.application as BaseApplication).component
    }
}