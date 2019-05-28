package com.stameni.com.forecastapplication.data.db.unitlocalized.future

import org.threeten.bp.LocalDate

interface UnitSpecificSimpleFutureWeatherEntry {
    val date: LocalDate
    val avgTemparature: Double
    val conditionText: String
    val conditionIconUrl: String
}
