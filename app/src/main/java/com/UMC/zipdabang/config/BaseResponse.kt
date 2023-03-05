package com.UMC.zipdabang.config

import com.google.gson.annotations.SerializedName

class BaseResponse (
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("code") val code: Int=0,
    @SerializedName("message") val message: String? = null
)