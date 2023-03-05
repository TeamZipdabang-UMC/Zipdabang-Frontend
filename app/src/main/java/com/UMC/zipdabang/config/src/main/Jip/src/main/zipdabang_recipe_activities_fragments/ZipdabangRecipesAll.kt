package com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.ObjectUtils

data class ZipdabangRecipesAll(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: List<RecipeInfoAll>,
    @SerializedName("error") val error: ObjectUtils.Null
)

data class RecipeInfoAll(
    @SerializedName("recipeId") val id: Int,
    @SerializedName("likes") val likes: Int,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("name") val name: String
)
