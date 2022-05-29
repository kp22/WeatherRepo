package com.openweather.di

import com.openweather.data.repository.AppRepository
import com.openweather.data.repository.AppRepositoryImpl
import org.koin.dsl.module

val remoteModule = module {
    factory<AppRepository> { AppRepositoryImpl(get()) }
}