package com.openweathermap.weatherforecast.data.network

import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

enum class ErrorCodes(val code: Int) { SocketTimeOut(-1) }
enum class ConnectivityCode(val code: Int) { ConnectionTimeOut(-2) }
enum class DefaultStatusCode(val code: Int) { StatusCode(-3) }

class ResponseHandler {

    companion object {
        const val CONNECTION_TIMEOUT =
            "Sorry, we're unable to reach the server right now. Please try again later."
        const val SOCKET_TIMEOUT =
            "There may be a problem in your internet connection. Please try again."
        const val DEFAULT_MESSAGE = "Oops! Something went wrong. Please try again."
        const val UNAUTHORIZED =
            "You are unauthorized to proceed with your request. Please, check your right API Key or check you tariff in your personal account."
        const val NOT_FOUND = "You make a wrong API request. Please, check your API request."
    }

    fun <T : Any> handleSuccess(response: Response<T>): Resource<T> {
        with(response) {
            return if (isSuccessful) {
                Resource.Success(body())
            } else {
                Resource.Error(
                    statusCode = code(),
                    message = getErrorMessage(code()),
                    data = null
                )
            }
        }
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is ConnectException -> Resource.Error(
                ConnectivityCode.ConnectionTimeOut.code,
                getErrorMessage(ConnectivityCode.ConnectionTimeOut.code)
            )
            is SocketTimeoutException -> Resource.Error(
                ErrorCodes.SocketTimeOut.code,
                getErrorMessage(ErrorCodes.SocketTimeOut.code)
            )
            else -> Resource.Error(
                DefaultStatusCode.StatusCode.code,
                getErrorMessage(Int.MAX_VALUE)
            )
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ConnectivityCode.ConnectionTimeOut.code -> CONNECTION_TIMEOUT
            ErrorCodes.SocketTimeOut.code -> SOCKET_TIMEOUT
            401 -> UNAUTHORIZED
            404 -> NOT_FOUND
            else -> DEFAULT_MESSAGE
        }
    }
}
