package com.openweathermap.weatherforecast.data.network

import com.openweathermap.weatherforecast.BuildConfig
import com.openweathermap.weatherforecast.data.model.WeatherForecast
import com.openweathermap.weatherforecast.data.model.WeatherForecastResponse
import com.openweathermap.weatherforecast.utilities.Constants.BASE_URL
import com.openweathermap.weatherforecast.utilities.Constants.CITIES_WEATHER_FORECAST_URL
import com.openweathermap.weatherforecast.utilities.Constants.CITY_WEATHER_FORECAST_URL
import com.openweathermap.weatherforecast.utilities.Constants.METRIC
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface WeatherForecastApi {

    @GET(CITIES_WEATHER_FORECAST_URL)
    suspend fun getListWeatherForecast(
        @Query("id") cityIds: String,
        @Query("units") unit: String = METRIC,
        @Query("appid") apiTokenKey: String = BuildConfig.WEATHER_FORECAST_KEY
    ): Response<WeatherForecastResponse>

    @GET(CITY_WEATHER_FORECAST_URL)
    suspend fun getDetailedWeatherForecast(
        @Query("id") cityId: Int,
        @Query("units") unit: String = METRIC,
        @Query("appid") apiTokenKey: String = BuildConfig.WEATHER_FORECAST_KEY
    ): Response<WeatherForecast>

    companion object {
        operator fun invoke(): WeatherForecastApi {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherForecastApi::class.java)
        }
    }
}

