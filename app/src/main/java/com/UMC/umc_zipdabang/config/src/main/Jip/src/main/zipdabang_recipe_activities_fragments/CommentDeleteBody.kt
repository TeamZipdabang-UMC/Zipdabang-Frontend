package com.UMC.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import com.google.gson.annotations.SerializedName

data class CommentDeleteBody(
    @SerializedName ("owner") val owner: Int?,
    @SerializedName ("commentId") val commentId: Int?
)