package com.openweather.di

import com.openweather.data.network.connectivity.ConnectivityInterceptor
import com.openweather.data.network.connectivity.ConnectivityInterceptorImpl
import com.openweather.data.network.services.networkModule
import org.koin.dsl.module

val managerModule = module {
    single<ConnectivityInterceptor> { ConnectivityInterceptorImpl(get()) }
    single { networkModule.getRetrofitApiClient(get()) }
}