package com.openweather.ui.weather.model


import com.google.gson.annotations.SerializedName

data class CityDataRes(
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("zip")
    val zip: String)