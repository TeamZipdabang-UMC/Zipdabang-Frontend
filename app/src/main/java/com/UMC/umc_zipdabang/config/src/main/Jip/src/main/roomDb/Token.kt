package com.UMC.umc_zipdabang.config.src.main.Jip.src.main.roomDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tokenDb")
data class Token(

    @PrimaryKey (autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "token") val token: String?
)
