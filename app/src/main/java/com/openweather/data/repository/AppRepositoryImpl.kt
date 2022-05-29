package com.openweather.data.repository

import com.openweather.data.network.services.ApiService
import com.openweather.data.network.services.SafeApiCall
import com.openweather.ui.weather.model.CityDataRes
import com.openweather.ui.weather.model.WeatherRes

/*App Repository Impl*/
class AppRepositoryImpl(private val apiService: ApiService) : AppRepository {

    override suspend fun getCityData(zip: String, appid: String): SafeApiCall<CityDataRes> {
        return try {
            val result = apiService.getCityData(zip, appid).await()
            SafeApiCall.Success(result)
        } catch (ex: Throwable) {
            SafeApiCall.Error(ex)
        }
    }

    override suspend fun getWeatherData(lat: String, lon: String, appid: String): SafeApiCall<WeatherRes> {
        return try {
            val result = apiService.getWeatherData(lat, lon, appid).await()
            SafeApiCall.Success(result)
        } catch (ex: Throwable) {
            SafeApiCall.Error(ex)
        }
    }
}
