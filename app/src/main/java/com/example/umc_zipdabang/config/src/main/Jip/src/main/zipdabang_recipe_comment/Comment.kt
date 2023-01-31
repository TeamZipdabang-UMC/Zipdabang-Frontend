package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment

import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date

data class Comment(
    val profileImageUrl: String?,
    val nickname: String?,
    val date: String?,
    val time: String?,
    val content: String?,
    val commentId: Int?,
    val commentOwner: Int?
)
