package com.openweathermap.weatherforecast.data.repository

import com.openweathermap.weatherforecast.data.model.WeatherForecast
import com.openweathermap.weatherforecast.data.model.WeatherForecastResponse
import com.openweathermap.weatherforecast.data.network.Resource
import com.openweathermap.weatherforecast.data.network.ResponseHandler
import com.openweathermap.weatherforecast.data.network.WeatherForecastApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ForecastRepositoryImpl(
    private val weatherForecastApi: WeatherForecastApi,
    private val responseHandler: ResponseHandler
) : ForecastRepository {

    override suspend fun getListWeatherForecast(cityIds: String): Resource<WeatherForecastResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.handleSuccess(weatherForecastApi.getListWeatherForecast(cityIds))
            } catch (exception: Exception) {
                responseHandler.handleException(exception)
            }
        }

    override suspend fun getDetailedWeatherForecast(cityId: Int): Resource<WeatherForecast> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.handleSuccess(weatherForecastApi.getDetailedWeatherForecast(cityId))
            } catch (exception: Exception) {
                responseHandler.handleException(exception)
            }
        }
}