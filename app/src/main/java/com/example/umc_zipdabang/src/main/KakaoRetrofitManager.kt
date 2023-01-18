package com.example.umc_zipdabang.src.main

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KakaoRetrofitManager {
    companion object {
        val kakaoRetrofit = Retrofit.Builder()
            .baseUrl("http://zipdabang.store:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}