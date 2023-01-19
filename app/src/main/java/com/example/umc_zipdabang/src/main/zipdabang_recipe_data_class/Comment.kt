package com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class

import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date

data class Comment(
    val profileImageUrl: String,
    val nickname: String,
    val date: LocalDate,
    val time: LocalTime,
    val content: String
)
