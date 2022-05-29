package com.openweather.ui.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.github.islamkhsh.CardSliderAdapter
import com.openweather.R
import com.openweather.ui.weather.model.WeatherRes
import com.openweather.utils.*


class WeatherViewPagerAdapter(list: ArrayList<WeatherRes.Weather>) : CardSliderAdapter<WeatherRes.Weather>(
    list) {

    override fun getItemContentLayout(position: Int): Int {
        return R.layout.weather_fragment
    }

    override fun bindView(position: Int, view: View, item: WeatherRes.Weather?) {
        val tvDate = view.findViewById<AppCompatTextView>(R.id.tvDate)
        val tvTime = view.findViewById<AppCompatTextView>(R.id.tvTime)
        val tvTemprature = view.findViewById<AppCompatTextView>(R.id.tvTemprature)
        val imgUser = view.findViewById<AppCompatImageView>(R.id.imgWeatherIcon)

        item?.let {
            tvDate.setText("${view.context.getString(R.string.date)} ${
                getConvertedDate(it.dtTxt, FORMAT_YYYYMMDD_HHMMSS, FORMAT_DDMMMYYYY)
            }")
            tvTime.setText("${view.context.getString(R.string.time)} ${
                getConvertedDate(it.dtTxt, FORMAT_YYYYMMDD_HHMMSS, FORMAT_HHMMAA)
            }")
            tvTemprature.setText("${view.context.getString(R.string.temperature)} ${it.main.temp}")
            imgUser.loadImage("$BAES_IMG_URL${it.weather.get(0).icon}$BAES_PNG")
        }
    }

}