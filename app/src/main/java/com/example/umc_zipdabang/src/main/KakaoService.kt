package com.example.umc_zipdabang.src.main

import com.example.umc_zipdabang.src.main.model.KakaoLogin
import retrofit2.Call
import retrofit2.http.GET

interface KakaoService {

    @GET("/users/kakao/start")
    fun getKakaoLoginStatus(): Call<KakaoLogin>
}