package com.UMC.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.ObjectUtils.Null


data class ZipdabangRecipes(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: List<RecipeInfo>,
    @SerializedName("error") val error: Null
)

data class RecipeInfo(
    @SerializedName("Id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("likes") val likes: Int
)