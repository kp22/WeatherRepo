package com.openweather.data.network.services

import com.openweather.ui.weather.model.CityDataRes
import com.openweather.ui.weather.model.WeatherRes
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("geo/1.0/zip")
    fun getCityData(@Query("zip") zip: String, @Query("appid") appid: String): Deferred<CityDataRes>

    @GET("data/2.5/forecast")
    fun getWeatherData(@Query("lat") lat: String, @Query("lon") lon: String,
        @Query("appid") appid: String): Deferred<WeatherRes>
}