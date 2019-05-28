package com.stameni.com.forecastapplication.common

import androidx.fragment.app.Fragment
import com.stameni.com.forecastapplication.di.components.ApplicationComponent
import com.stameni.com.forecastapplication.di.components.PresentationComponent
import com.stameni.com.forecastapplication.di.modules.*

abstract class BaseFragment : Fragment() {

    protected fun getPresentationComponent(): PresentationComponent {
        return getApplicationComponent()
            .newPresentationComponent(PresentationModule(activity!!), NetworkModule(), RoomModule(), ViewModelModule(), LocationModule())

    }

    private fun getApplicationComponent(): ApplicationComponent {
        return (activity!!.application as BaseApplication).component
    }

}