package com.UMC.zipdabang.config.src.main.Jip.src.main

import com.UMC.zipdabang.config.src.main.SocialLogin.model.KakaoResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface KakaoService {

    @FormUrlEncoded
    @POST ("/users/kakao/login")
    fun addKakaoUser(@Field("userEmail") userEmail: String, @Field("userProfile") userProfile: String): Call<KakaoResponse>

}