package com.openweather.ui.base

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseResponse(
    @field:SerializedName("message")
    val message: String? = null,

    @SerializedName("cod")
    val cod: Int? = null,
)