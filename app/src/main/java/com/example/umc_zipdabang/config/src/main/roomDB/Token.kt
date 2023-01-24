package com.example.umc_zipdabang.src.main.roomDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokenDb")
data class Token(

    @PrimaryKey (autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "token") val token: String?
)
