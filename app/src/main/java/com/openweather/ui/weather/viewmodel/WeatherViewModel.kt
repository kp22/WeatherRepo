package com.openweather.ui.weather.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.openweather.BuildConfig
import com.openweather.data.network.services.SafeApiCall
import com.openweather.data.repository.AppRepository
import com.openweather.ui.base.BaseViewModel
import com.openweather.ui.weather.model.CityDataRes
import com.openweather.ui.weather.model.WeatherRes
import com.openweather.utils.FORMAT_YYYYMMDD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewModel(private val appRepository: AppRepository) : BaseViewModel() {

    val cityDataLiveData = MutableLiveData<CityDataRes>()
    val weatherDataLiveData = MutableLiveData<WeatherRes>()
    var cityName: MutableLiveData<String> = MutableLiveData("")
    var day1: MutableLiveData<String> = MutableLiveData("")
    var day2: MutableLiveData<String> = MutableLiveData("")
    var day3: MutableLiveData<String> = MutableLiveData("")
    var day4: MutableLiveData<String> = MutableLiveData("")
    var day5: MutableLiveData<String> = MutableLiveData("")
    var day6: MutableLiveData<String> = MutableLiveData("")

    //get city data using zipCode and countryCode
    fun getCityData(zip: String) {
        showLoading.value = true
        launch {
            val result = withContext(Dispatchers.IO) {
                appRepository.getCityData(zip, BuildConfig.APP_ID)
            }
            showLoading.value = false
            when (result) {
                is SafeApiCall.Success -> {
                    cityDataLiveData.value = result.data
                }
                is SafeApiCall.Error -> showError.value = getErrorMessage(result.exception)
            }
        }
    }

    //get weather data from lati,logi
    fun getWeatherData(lat: String, lon: String) {
        showLoading.value = true
        launch {
            val result = withContext(Dispatchers.IO) {
                appRepository.getWeatherData(lat, lon, BuildConfig.APP_ID)
            }
            showLoading.value = false
            when (result) {
                is SafeApiCall.Success -> {
                    if (result.data.cod == 200) {
                        weatherDataLiveData.value = result.data
                    } else {
                        showError.value = errorMsg
                    }
                }
                is SafeApiCall.Error -> showError.value = getErrorMessage(result.exception)
            }
        }
    }

    //read json file from assets
    private fun getJsonFromAssets(context: Context, fileName: String): String {
        val jsonString: String
        jsonString = try {
            val inputStream: InputStream = context.getAssets().open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
        return jsonString
    }

    //it will return a date base on dayNo
    fun getDate(dayNo: Int, firstDate: String): String {
        var result = ""
        val format = SimpleDateFormat(FORMAT_YYYYMMDD, Locale.getDefault())
        val currentDay = Calendar.getInstance(Locale.getDefault())
        currentDay.time = format.parse(firstDate)
        //add day in calender
        currentDay.add(Calendar.DAY_OF_MONTH, dayNo - 1)
        result = format.format(currentDay.time)
        return result
    }

    //get the filtered data by date
    fun getDataByDay(list: List<WeatherRes.Weather>, day: Int): Any {
        return list.filter { it.dtTxt.contains(getDate(day, list.get(0).dtTxt), true) }
    }

    //read data from the Json file
    fun getOfflineData(context: Context, fileName: String): WeatherRes {
        val jsonFileString: String = getJsonFromAssets(context, fileName)
        val listUserType = object : TypeToken<WeatherRes>() {}.type
        val data: WeatherRes = Gson().fromJson(jsonFileString, listUserType)
        return data
    }
}