package com.example.myapplication

import com.example.myapplication.model.ApiResponse
import com.example.myapplication.model.MagicCard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MagicRepository {

    private val magicApi: MagicApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.magicthegathering.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        magicApi = retrofit.create(MagicApi::class.java)
    }

    fun getMagicCards(
        onSuccess: (List<MagicCard>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        magicApi.getCards().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it.cards) }
                } else {
                    onFailure(Throwable("API Error: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}