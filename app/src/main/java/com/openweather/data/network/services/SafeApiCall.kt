package com.openweather.data.network.services

sealed class SafeApiCall<out T : Any> {
    class Success<out T : Any>(val data: T) : SafeApiCall<T>()
    class Error(val exception: Throwable) : SafeApiCall<Nothing>()
}