package com.example.umc_zipdabang.src.main.model

import com.google.gson.annotations.SerializedName

data class KakaoResponse(
    @SerializedName ("status") val status: String,
    @SerializedName ("email") val email: String,
    @SerializedName ("token") val token: String
)
