package com.openweather.ui.weather.view

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.openweather.R
import com.openweather.databinding.ActivityWeatherBinding
import com.openweather.ui.adapter.WeatherViewPagerAdapter
import com.openweather.ui.base.BaseActivity
import com.openweather.ui.weather.model.WeatherRes
import com.openweather.ui.weather.viewmodel.WeatherViewModel
import org.koin.android.ext.android.inject


class WeatherActivity : BaseActivity() {
    lateinit var binding: ActivityWeatherBinding
    val viewModel by inject<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initScreen()
        initObservers()
        initListner()
    }

    //init screen
    private fun initScreen() {
        binding.weatherModel = viewModel
        binding.lifecycleOwner = this
    }

    //handle the click listnear
    private fun initListner() {
        binding.switchOffline.setOnCheckedChangeListener(
            { compoundButton: CompoundButton, isChecked: Boolean ->
                if (!isChecked) readOfflineData()
                else getCityData()
            })
    }

    //observe the live data
    private fun initObservers() {
        //observe the error
        viewModel.showError.observe(this, {
            showToast(it)
        })
        //observe the loading
        viewModel.showLoading.observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        //observe the city data
        viewModel.cityDataLiveData.observe(this, {
            //set city name
            viewModel.cityName.value = "${getString(R.string.city)}${it.name}"
            //get weather data base on city lati and logi
            viewModel.getWeatherData(it.lat.toString(), it.lon.toString())
        })

        //observe weather data
        viewModel.weatherDataLiveData.observe(this, {
            setDataOnUI(it.list)
        })

        getCityData()
    }

    //get city data of Belgrade, Serbia using pincode
    private fun getCityData() {
        //here i put the zip code of Belgrade (11000)
        viewModel.getCityData("11000,RS")
    }

    //read data from the Json file for offline mode
    private fun readOfflineData() {
        val data: WeatherRes = viewModel.getOfflineData(applicationContext, "weather.json")
        setDataOnUI(data.list)
    }

    //set weather data on UI
    private fun setDataOnUI(list: List<WeatherRes.Weather>) {
        //get the 1st date of the list: Need to get first date to get next 5days date
        val firstDate = list.get(0).dtTxt

        //set date title for next 5 days
        viewModel.day1.value = viewModel.getDate(1, firstDate)
        viewModel.day2.value = viewModel.getDate(2, firstDate)
        viewModel.day3.value = viewModel.getDate(3, firstDate)
        viewModel.day4.value = viewModel.getDate(4, firstDate)
        viewModel.day5.value = viewModel.getDate(5, firstDate)
        viewModel.day6.value = viewModel.getDate(6, firstDate)

        //filter the data base on the days
        val day1List = viewModel.getDataByDay(list, 1)
        val day2List = viewModel.getDataByDay(list, 2)
        val day3List = viewModel.getDataByDay(list, 3)
        val day4List = viewModel.getDataByDay(list, 4)
        val day5List = viewModel.getDataByDay(list, 5)
        val day6List = viewModel.getDataByDay(list, 6)

        //update the list
        binding.viewPagerDay1.adapter = WeatherViewPagerAdapter(day1List as ArrayList<WeatherRes.Weather>)
        binding.viewPagerDay2.adapter = WeatherViewPagerAdapter(day2List as ArrayList<WeatherRes.Weather>)
        binding.viewPagerDay3.adapter = WeatherViewPagerAdapter(day3List as ArrayList<WeatherRes.Weather>)
        binding.viewPagerDay4.adapter = WeatherViewPagerAdapter(day4List as ArrayList<WeatherRes.Weather>)
        binding.viewPagerDay5.adapter = WeatherViewPagerAdapter(day5List as ArrayList<WeatherRes.Weather>)
        binding.viewPagerDay6.adapter = WeatherViewPagerAdapter(day6List as ArrayList<WeatherRes.Weather>)
    }

}