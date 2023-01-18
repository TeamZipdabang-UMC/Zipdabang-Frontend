package com.example.umc_zipdabang.src.main

import com.example.umc_zipdabang.src.main.model.GoogleResponse
import com.example.umc_zipdabang.src.main.model.KakaoLogin
import com.example.umc_zipdabang.src.main.model.KakaoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface GoogleService {
    @FormUrlEncoded
    @POST ("/users/google/login")
    fun addGoogleUser(@Field("userEmail") userEmail: String, @Field("userProfile") userProfile: String): Call<GoogleResponse>
}