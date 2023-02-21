package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments


import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.Ingredient
import com.google.gson.annotations.SerializedName

data class RecipeDetailResponse(
    @SerializedName ("data")
    val recipeDataClass: RecipeDataClass?,
    @SerializedName ("error")
    val error: Any?,
    @SerializedName ("success")
    val success: Boolean?
)

data class RecipeDataClass(
    @SerializedName ("ingredient")
    val ingredient: List<IngredientDetail?>?,
    @SerializedName ("liked")
    val liked: Boolean?,
    @SerializedName ("recipe")
    val recipe: List<Recipe?>?,
    @SerializedName ("scraped")
    val scraped: Boolean?,
    @SerializedName ("challenger")
    val challenger: Int?,
    @SerializedName ("comments")
    val comments: Int?,
    @SerializedName ("scraps")
    val scraps: Int?,
    @SerializedName ("isChallenge")
    val isChallenge: Int?,
    @SerializedName ("steps")
    val steps: List<Step>?
)

data class IngredientDetail(
    @SerializedName ("name")
    val name: String?,
    @SerializedName ("quantity")
    val quantity: String?
)

data class Recipe(
    @SerializedName ("nickname")
    val nickname: String?,
    @SerializedName ("created_at")
    val createdAt: String?,
    @SerializedName ("image_url")
    val imageUrl: String?,
    @SerializedName ("intro")
    val intro: String?,
    @SerializedName ("likes")
    val likes: Int?,
    @SerializedName ("name")
    val name: String?,
    @SerializedName ("owner")
    val owner: Int?,
    @SerializedName ("recipeId")
    val recipeId: Int?,
    @SerializedName ("review")
    val review: String?,
    @SerializedName ("take_time")
    val takeTime: String?
)

data class Step(
    @SerializedName ("step")
    val step: Int?,
    @SerializedName ("step_description")
    val stepDescription: String?,
    @SerializedName ("step_image_url")
    val stepImageUrl: String?
)