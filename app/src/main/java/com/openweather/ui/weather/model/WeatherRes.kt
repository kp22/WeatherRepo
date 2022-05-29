package com.openweather.ui.weather.model


import com.google.gson.annotations.SerializedName
import com.openweather.ui.base.BaseResponse

data class WeatherRes(
    @SerializedName("cnt") val cnt: Int,
    @SerializedName("list") val list: List<Weather>,
                     ) : BaseResponse() {

    data class Weather(@SerializedName("dt") val dt: Int, @SerializedName("dt_txt") val dtTxt: String,
        @SerializedName("main") val main: Main, @SerializedName("weather") val weather: List<Weather>) {
        data class Main(@SerializedName("temp") val temp: Double)
        data class Weather(@SerializedName("icon") val icon: String)

    }
}