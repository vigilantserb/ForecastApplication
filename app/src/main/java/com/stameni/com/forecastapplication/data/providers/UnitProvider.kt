package com.stameni.com.forecastapplication.data.providers

import com.stameni.com.forecastapplication.internals.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}