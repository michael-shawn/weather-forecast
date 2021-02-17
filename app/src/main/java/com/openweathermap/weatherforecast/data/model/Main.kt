package com.openweathermap.weatherforecast.data.model


import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

data class Main(
    @SerializedName("temp")
    val currentTemperature: Double,

    @SerializedName("temp_max")
    val highTemperature: Double,

    @SerializedName("temp_min")
    val lowTemperature: Double
) {
    val roundedCurrentTemperature: String
        get() = currentTemperature.roundToOneDecimal()

    val roundedHighTemperature: String
        get() = highTemperature.roundToInt().toString()

    val roundedLowTemperature: String
        get() = lowTemperature.roundToInt().toString()

    private fun Double.roundToOneDecimal(): String = String.format("%.1f", this)
}