package com.openweather.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.openweather.R
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class NoConnectivityException : IOException()

//load image using glide
fun ImageView.loadImage(imageUrl: String) {
    Glide.with(this).load(imageUrl).centerCrop().error(R.color.gray).into(this)
}

//You can get the date in any format. just pass the date,dateformat and convertFormat.
fun getConvertedDate(date: String, dateFormat: String, convertFormat: String): String {
    var convertedDate = ""
    try {
        val sdf = SimpleDateFormat(dateFormat,  Locale.getDefault())
        val sdfConvert = SimpleDateFormat(convertFormat,  Locale.getDefault())
        convertedDate = sdfConvert.format(sdf.parse(date))
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return convertedDate
}