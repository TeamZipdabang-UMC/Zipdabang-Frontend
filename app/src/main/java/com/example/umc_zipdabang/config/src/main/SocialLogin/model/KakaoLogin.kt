package com.example.umc_zipdabang.config.src.main.SocialLogin.model

import com.google.gson.annotations.SerializedName

data class KakaoLogin(
    @SerializedName("userEmail") val userEmail: String,
    @SerializedName("userProfile") val userProfile: String
)
