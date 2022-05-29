package com.openweather.data.repository

import com.openweather.data.network.services.SafeApiCall
import com.openweather.ui.weather.model.CityDataRes
import com.openweather.ui.weather.model.WeatherRes
import retrofit2.http.Query

/*App Repository*/
interface AppRepository {

    suspend fun getCityData(@Query("zip") zip: String,
        @Query("appid") appid: String): SafeApiCall<CityDataRes>

    suspend fun getWeatherData(@Query("lat") lat: String, @Query("lon") lon: String,
        @Query("appid") appid: String): SafeApiCall<WeatherRes>
}