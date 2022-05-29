package com.openweather.data.network.services

import android.os.Build
import androidx.annotation.RequiresApi
import com.openweather.BuildConfig
import com.openweather.data.network.connectivity.ConnectivityInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext

/**
 * NetworkModule.
 */
const val CONNECT_TIMEOUT = 30L
const val WRITE_TIMEOUT = 30L
const val READ_TIMEOUT = 30L

object networkModule {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun getRetrofitApiClient(connectivityInterceptor: ConnectivityInterceptor): ApiService {

        val requestInterceptor = Interceptor { chain ->
            var request = chain.request()
            val builder = request.newBuilder()
            request = setAuthHeader(builder).build()
            return@Interceptor chain.proceed(request)
        }

        val sc = SSLContext.getInstance("TLSv1.2")
        sc.init(null, null, null)
        val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_2).build()
        val specs = mutableListOf<ConnectionSpec>()
        specs.add(cs)
        specs.add(ConnectionSpec.COMPATIBLE_TLS)
        specs.add(ConnectionSpec.CLEARTEXT)

        val okHttpClient =
            OkHttpClient().newBuilder().connectionSpecs(specs).addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor).addInterceptor(HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                }).connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build()

        return Retrofit.Builder().client(okHttpClient).baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create())).build()
            .create(ApiService::class.java)
    }


    private fun setAuthHeader(builder: Request.Builder): Request.Builder {
        builder.addHeader("accept", "application/json")
        return builder
    }
}