package com.openweathermap.weatherforecast.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class City(
    @PrimaryKey(autoGenerate = false)
    val cityId: Int,

    val isFavorite: Boolean
)
