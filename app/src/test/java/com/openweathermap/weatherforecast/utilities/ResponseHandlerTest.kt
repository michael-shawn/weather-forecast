package com.openweathermap.weatherforecast.utilities

import com.openweathermap.weatherforecast.data.model.WeatherForecastResponse
import com.openweathermap.weatherforecast.data.network.ResponseHandler
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.ConnectException
import java.net.SocketTimeoutException

@RunWith(JUnit4::class)
class ResponseHandlerTest {

    private lateinit var responseHandler: ResponseHandler

    @Before
    fun onSetup() {
        responseHandler = ResponseHandler()
    }

    @Test
    fun `when exception is server connection then return connect exception for WeatherForecastResponse`() {
        val connectException = ConnectException()
        val response = responseHandler.handleException<WeatherForecastResponse>(connectException)
        Assert.assertEquals(
            "Sorry, we're unable to reach the server right now. Please try again later.",
            response.message
        )
    }

    @Test
    fun `when exception is timeout then return timeout error for WeatherForecastResponse`() {
        val socketTimeoutException = SocketTimeoutException()
        val response =
            responseHandler.handleException<WeatherForecastResponse>(socketTimeoutException)
        Assert.assertEquals(
            "There may be a problem in your internet connection. Please try again.",
            response.message
        )
    }

    @Test
    fun `when exception is out of defined exception scope then return default exception for WeatherForecastResponse`() {
        val defaultException = Exception()
        val response = responseHandler.handleException<WeatherForecastResponse>(defaultException)
        Assert.assertEquals(
            "Oops! Something went wrong. Please try again.",
            response.message
        )
    }
}
