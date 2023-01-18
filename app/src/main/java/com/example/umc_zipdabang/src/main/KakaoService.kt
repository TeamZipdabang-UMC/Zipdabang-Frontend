package com.example.umc_zipdabang.src.main

import com.example.umc_zipdabang.src.main.model.KakaoLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface KakaoService {

    @POST ("/users/kakao/login")
    fun addKaKaoUser(@Body kakaoLogin: KakaoLogin): Call<KakaoLogin>
}