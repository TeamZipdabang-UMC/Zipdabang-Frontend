package com.example.umc_zipdabang.config.src.main.SocialLogin

import com.example.umc_zipdabang.config.src.main.SocialLogin.model.GoogleResponse
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