package com.example.umc_zipdabang.src.main.roomDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "commentDb")
data class CommentDbClass(
    @PrimaryKey val commentId: Int?,
    @ColumnInfo(name = "commentOwner") val commentOwner: Int?
)
