package com.openweathermap.weatherforecast

import android.app.Application
import com.openweathermap.weatherforecast.data.db.WeatherForecastDatabase
import com.openweathermap.weatherforecast.data.network.ResponseHandler
import com.openweathermap.weatherforecast.data.network.WeatherForecastApi
import com.openweathermap.weatherforecast.data.repository.CityRepository
import com.openweathermap.weatherforecast.data.repository.CityRepositoryImpl
import com.openweathermap.weatherforecast.data.repository.ForecastRepository
import com.openweathermap.weatherforecast.data.repository.ForecastRepositoryImpl
import com.openweathermap.weatherforecast.utilities.ResourceHelper
import com.openweathermap.weatherforecast.viewmodels.factory.ViewModelFactory
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule

class WeatherForecastApplication : Application(), DIAware {

    override val di = DI.lazy {
        import(androidXModule(this@WeatherForecastApplication))

        bind() from provider { ViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { ResponseHandler() }
        bind() from provider { ResourceHelper(instance()) }
        bind() from provider { WeatherForecastDatabase(instance()) }
        bind() from singleton { WeatherForecastApi() }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
        bind<CityRepository>() with singleton { CityRepositoryImpl(instance()) }
    }
}