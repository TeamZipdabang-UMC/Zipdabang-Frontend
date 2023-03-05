package com.UMC.zipdabang.config.src.main.SocialLogin.model

import com.google.gson.annotations.SerializedName

data class GoogleResponse(
    @SerializedName ("status") val status: String,
    @SerializedName ("email") val email: String,
    @SerializedName ("token") val token: String
)
