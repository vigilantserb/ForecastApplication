package com.stameni.com.forecastapplication.di.components

import com.stameni.com.forecastapplication.common.BaseApplication
import com.stameni.com.forecastapplication.di.modules.*
import dagger.Component

@Component(modules = [AppModule::class])
interface ApplicationComponent {
    fun inject(app: BaseApplication)
    fun newPresentationComponent(
        presentationModule: PresentationModule,
        networkModule: NetworkModule,
        roomModule: RoomModule,
        viewModelModule: ViewModelModule,
        locationModule: LocationModule
    ): PresentationComponent
}