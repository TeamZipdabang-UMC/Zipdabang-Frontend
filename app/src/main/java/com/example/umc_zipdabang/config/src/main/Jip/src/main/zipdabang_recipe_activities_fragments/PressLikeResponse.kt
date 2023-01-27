package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.ObjectUtils.Null

data class PressLikeResponse(
    @SerializedName ("success") val success: Boolean?,
    @SerializedName ("data") val data: Likes?,
    @SerializedName ("error") val error: String?
)

data class Likes(
    @SerializedName ("likes") val likes: Int?
)