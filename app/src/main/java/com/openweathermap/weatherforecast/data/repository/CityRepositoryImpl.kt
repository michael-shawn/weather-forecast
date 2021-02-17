package com.openweathermap.weatherforecast.data.repository

import com.openweathermap.weatherforecast.data.db.CityDao
import com.openweathermap.weatherforecast.data.db.WeatherForecastDatabase
import com.openweathermap.weatherforecast.data.model.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityRepositoryImpl(
    weatherForecastDatabase: WeatherForecastDatabase
) : CityRepository {

    private val cityDao: CityDao = weatherForecastDatabase.cityDao()

    override suspend fun persistCity(city: City) =
        withContext(Dispatchers.IO) {
            return@withContext try {
                cityDao.upsertCity(city)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }

    override suspend fun getCities(): List<City> =
        withContext(Dispatchers.IO) {
            return@withContext cityDao.getCities()
        }

    override suspend fun getCity(cityId: Int): City =
        withContext(Dispatchers.IO) {
            return@withContext cityDao.getCity(cityId)
        }

    override suspend fun deletePersistedCities() =
        withContext(Dispatchers.IO) {
            return@withContext try {
                cityDao.deletePersistedCities()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
}