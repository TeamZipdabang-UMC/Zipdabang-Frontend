package com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class

import java.sql.Time
import java.util.Date

data class Comment(
    val profileImageUrl: String,
    val nickname: String,
    val date: Date,
    val time: Time,
    val content: String
)
