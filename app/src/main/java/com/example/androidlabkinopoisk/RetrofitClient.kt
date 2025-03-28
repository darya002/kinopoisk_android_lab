package com.example.androidlabkinopoisk

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "G3QPDPC-DXFMA1X-NX9QXZJ-PPKE60H"

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("X-API-KEY", API_KEY)
            .build()
        chain.proceed(request)
    }
    .build()


val retrofit = Retrofit.Builder()
    .baseUrl("https://api.kinopoisk.dev/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val kinopoiskApi = retrofit.create(KinopoiskApi::class.java)
