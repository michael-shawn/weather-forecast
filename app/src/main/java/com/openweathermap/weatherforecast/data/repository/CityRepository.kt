package com.openweathermap.weatherforecast.data.repository

import com.openweathermap.weatherforecast.data.model.City

interface CityRepository {

    suspend fun persistCity(city: City)

    suspend fun getCities(): List<City>

    suspend fun getCity(cityId: Int): City

    suspend fun deletePersistedCities()

}