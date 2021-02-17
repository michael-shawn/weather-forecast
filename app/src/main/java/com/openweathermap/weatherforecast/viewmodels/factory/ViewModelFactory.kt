package com.openweathermap.weatherforecast.viewmodels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openweathermap.weatherforecast.data.repository.CityRepository
import com.openweathermap.weatherforecast.data.repository.ForecastRepository
import com.openweathermap.weatherforecast.utilities.ResourceHelper
import com.openweathermap.weatherforecast.viewmodels.DetailedWeatherForecastsViewModel
import com.openweathermap.weatherforecast.viewmodels.WeatherForecastsViewModel

class ViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val cityRepository: CityRepository,
    private val resourceHelper: ResourceHelper
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(WeatherForecastsViewModel::class.java) -> {
                WeatherForecastsViewModel(forecastRepository, cityRepository) as T
            }
            modelClass.isAssignableFrom(DetailedWeatherForecastsViewModel::class.java) -> {
                DetailedWeatherForecastsViewModel(
                    forecastRepository,
                    cityRepository,
                    resourceHelper
                ) as T
            }
            else -> throw IllegalArgumentException("ViewModel Class Not Found")
        }
    }
}