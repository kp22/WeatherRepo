package com.openweather.ui.base

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.openweather.R


open class BaseActivity : AppCompatActivity() {

    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
    }

    fun showToast(msg:String){
        runOnUiThread(Runnable {
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
        })
    }
}