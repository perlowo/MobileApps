package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MagicApiService {
    private const val BASE_URL = "https://api.magicthegathering.io/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val magicApi: MagicApi = retrofit.create(MagicApi::class.java)
}