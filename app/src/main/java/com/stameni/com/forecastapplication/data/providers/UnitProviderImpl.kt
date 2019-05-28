package com.stameni.com.forecastapplication.data.providers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.stameni.com.forecastapplication.internals.UnitSystem

const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl(
    context: Context
) : PreferenceProvider(context), UnitProvider {

    override fun getUnitSystem(): UnitSystem {
        val unit = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(unit!!)
    }
}