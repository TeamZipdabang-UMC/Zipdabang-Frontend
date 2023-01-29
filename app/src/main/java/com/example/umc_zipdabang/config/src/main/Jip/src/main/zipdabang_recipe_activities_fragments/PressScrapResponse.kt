package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import com.google.gson.annotations.SerializedName

data class PressScrapResponse(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("data") val data: Any?,
    @SerializedName("error") val error: String?
)
