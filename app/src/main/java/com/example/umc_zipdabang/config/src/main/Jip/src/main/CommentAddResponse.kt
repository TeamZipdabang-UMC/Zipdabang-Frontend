package com.example.umc_zipdabang.config.src.main.Jip.src.main

import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.ObjectUtils.Null

data class CommentAddResponse(
    @SerializedName ("success") val success: Boolean,
    @SerializedName ("data") val data: WrittenComment,
    @SerializedName ("error") val error: Null
)

data class WrittenComment(
    @SerializedName("ownerId") val ownerId: Int,
    @SerializedName("createdCommentId") val createdComment: Int
)
