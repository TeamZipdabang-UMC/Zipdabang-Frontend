package com.example.umc_zipdabang.src.main.roomDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "recipeDb")
data class RecipeDbClass(
    @PrimaryKey val recipeId: Int?,
    @ColumnInfo(name = "recipeOwner") val recipeOwner: Int?
)
