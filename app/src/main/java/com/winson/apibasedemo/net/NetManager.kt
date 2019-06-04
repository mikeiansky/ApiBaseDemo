package com.winson.apibasedemo.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetManager {

    fun buildApi(): Retrofit {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.readTimeout(10, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(10, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        return Retrofit.Builder()
            .baseUrl("http://172.16.1.191:8080")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClientBuilder.build())
            .build()
    }


}