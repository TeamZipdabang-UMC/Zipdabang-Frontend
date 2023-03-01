package com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import com.google.gson.annotations.SerializedName

data class CommentsResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: CommentsData?,
    @SerializedName("error") val error: Any?
)

data class CommentsData(
    @SerializedName("comments") val comments: List<AllComments?>
//    List<ThreeComments>
)

data class AllComments(
    @SerializedName("commentId") val commentId: Int,
    @SerializedName("owner") val owner: Int,
    @SerializedName("body") val body: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("nickname") val nickname: String?,
    @SerializedName("profile") val profile: String?
)

