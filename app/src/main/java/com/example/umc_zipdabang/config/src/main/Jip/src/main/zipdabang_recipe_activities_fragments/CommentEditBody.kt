package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import com.google.gson.annotations.SerializedName

data class CommentEditBody(
    @SerializedName ("owner") val owner: Int?,
    @SerializedName ("commentId") val commentId: Int?,
    // 새로운 댓글의 내용
    @SerializedName ("newBody") val newBody: String?
)
