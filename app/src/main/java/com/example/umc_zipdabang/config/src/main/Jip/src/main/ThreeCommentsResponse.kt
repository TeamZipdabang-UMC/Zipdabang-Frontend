package com.example.umc_zipdabang.config.src.main.Jip.src.main

import com.google.gson.annotations.SerializedName

data class ThreeCommentsResponse(
    @SerializedName ("success") val success: Boolean,
    @SerializedName ("data") val data: Data,
    @SerializedName ("error") val error: Any?
)

data class Data(
    @SerializedName ("comments") val comments: List<ThreeComments>
)

data class ThreeComments(
    @SerializedName ("body") val body: String?,
    @SerializedName ("created_at") val createdAt: String?,
    @SerializedName ("nickname") val nickname: String?,
    @SerializedName ("profile") val profile: String?
)