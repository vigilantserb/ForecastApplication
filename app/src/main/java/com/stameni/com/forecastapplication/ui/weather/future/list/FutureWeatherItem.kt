package com.stameni.com.forecastapplication.ui.weather.future.list

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.stameni.com.forecastapplication.R
import com.stameni.com.forecastapplication.data.db.unitlocalized.future.MetricSpecificSimpleFutureWeatherEntry
import com.stameni.com.forecastapplication.data.db.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_future_weather.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class FutureWeatherItem(
    val weatherEntry: UnitSpecificSimpleFutureWeatherEntry
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_condition.text = weatherEntry.conditionText
            updateConditionImage()
            updateDate()
            updateTemparature()
        }
    }

    override fun getLayout(): Int = R.layout.item_future_weather

    private fun ViewHolder.updateDate() {
        val dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        textView_date.text = weatherEntry.date.format(dateFormat)
    }

    @SuppressLint("SetTextI18n")
    private fun ViewHolder.updateTemparature() {
        val unit = if (weatherEntry is MetricSpecificSimpleFutureWeatherEntry) "C" else "F"
        textView_temperature.text = "${weatherEntry.avgTemparature}${unit}"
    }

    private fun ViewHolder.updateConditionImage() {
        Glide
            .with(this.containerView)
            .load("http:${weatherEntry.conditionIconUrl}")
            .into(imageView_condition_icon)
    }
}