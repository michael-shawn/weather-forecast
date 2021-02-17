package com.openweathermap.weatherforecast.data.model


import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("id")
    val id: Int,

    @SerializedName("main")
    val main: String
)