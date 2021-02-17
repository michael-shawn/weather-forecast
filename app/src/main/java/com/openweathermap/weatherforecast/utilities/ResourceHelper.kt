package com.openweathermap.weatherforecast.utilities

import android.content.Context
import com.openweathermap.weatherforecast.R

class ResourceHelper(private val applicationContext: Context) {

    val currentTemperature
        get() = applicationContext.getString(R.string.temperature)

    val highAndLowTemperature
        get() = applicationContext.getString(R.string.high_and_low_temperature)
}