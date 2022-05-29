package com.openweather.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.openweather.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.HttpException
import java.net.SocketTimeoutException
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    val showLoading = MutableLiveData<Boolean>()
    val showError = SingleLiveEvent<String>()
    val errorMsg = "Something went wrong!"
    // Coroutine's background job
     val job = Job()

    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }

    fun getErrorMessage(exception: Throwable): String? {
        var message: String? = "Something went wrong! Please try again!"
        when (exception) {
            is HttpException -> {
                if (exception.code() != 500) {
                    message = "Something went wrong! Please try again!"
                }
                return message
            }
            // is NoConnectivityException -> return "Connection error. Please try again!"
            is SocketTimeoutException -> return "Something went wrong!"
            is Exception -> return "Please check your internet connection!"
            else -> return "Something went wrong! Please try again!"
        }
    }
}

