package com.UMC.umc_zipdabang.config.src.main.SocialLogin.model

import com.google.gson.annotations.SerializedName

data class KakaoResponse(
    @SerializedName ("status") val status: String,
    @SerializedName ("email") val email: String,
    @SerializedName ("token") val token: String
)
