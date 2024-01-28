package com.example.myapplication

import com.example.myapplication.model.ApiResponse
import com.example.myapplication.model.MagicCard
import retrofit2.Call
import retrofit2.http.GET

interface MagicApi {
    @GET("cards")
    fun getCards(): Call<ApiResponse>
}
//fdfdfs