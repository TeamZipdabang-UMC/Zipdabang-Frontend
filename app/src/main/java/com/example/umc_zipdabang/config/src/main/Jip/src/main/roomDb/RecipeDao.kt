package com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {
    // 레시피 Id, Owner 넣기
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecipe(recipeDbClass: RecipeDbClass)

    // 맨 위에 것 하나만 가져오기 - 잘 안쓸듯?
    @Query("Select * from recipeDb Limit 1")
    fun getRecipe(): RecipeDbClass

    // 해당 레시피 아이디에 맞는 레코드 하나를 가져오기
    @Query("Select * from recipeDb where recipeId like :recipeId LIMIT 1")
    fun getRecipeById(recipeId: Int): RecipeDbClass

    // 싹 다 지우기 - 화면 이동시 무조건 필요.
    @Query("DELETE FROM recipeDb")
    suspend fun deleteAllRecipes()
}