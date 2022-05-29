package com.openweather

import android.app.Application
import com.openweather.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        configureDI()
    }

    private fun configureDI() {
        //init koin DI
        startKoin {
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}