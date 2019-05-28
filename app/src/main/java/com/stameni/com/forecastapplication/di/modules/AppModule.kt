package com.stameni.com.forecastapplication.di.modules

import com.stameni.com.forecastapplication.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: BaseApplication) {
    @Provides
    @Singleton
    fun provideApplication() = app
}
