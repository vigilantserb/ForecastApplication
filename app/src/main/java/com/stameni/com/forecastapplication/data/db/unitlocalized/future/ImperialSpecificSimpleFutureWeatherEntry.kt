package com.stameni.com.forecastapplication.data.db.unitlocalized.future

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

data class ImperialSpecificSimpleFutureWeatherEntry(
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "day_avgtempF")
    override val avgTemparature: Double,
    @ColumnInfo(name = "day_condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "day_condition_icon")
    override val conditionIconUrl: String
) : UnitSpecificSimpleFutureWeatherEntry