package com.UMC.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import com.google.gson.annotations.SerializedName

data class RunnerReportResponse(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("data") val data: Boolean?,
    @SerializedName("error") val error: String?
)
