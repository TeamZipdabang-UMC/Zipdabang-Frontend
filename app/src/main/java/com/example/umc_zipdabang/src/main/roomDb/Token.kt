package com.example.umc_zipdabang.src.main.roomDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Token(
    @PrimaryKey val token: String?
)
