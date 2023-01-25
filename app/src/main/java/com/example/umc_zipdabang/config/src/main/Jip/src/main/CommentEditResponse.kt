package com.example.umc_zipdabang.config.src.main.Jip.src.main

import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.ObjectUtils.Null

data class CommentEditResponse(
    @SerializedName ("success") val success: Boolean,
    @SerializedName ("data") val data: Null,
    @SerializedName ("error") val error: String?
)
