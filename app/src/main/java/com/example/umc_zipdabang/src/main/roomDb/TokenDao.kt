package com.example.umc_zipdabang.src.main.roomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface TokenDao {
    @Insert(onConflict = REPLACE)
    fun addToken(token: Token)

//    @Query ("DELETE FROM tokenDb WHERE id = 0")
//    fun deleteToken(token: Token): List<Token>

    @Query("Select * from tokenDb")
    fun getToken(): List<Token>

    @Delete
    suspend fun delete(token: Token)

    @Query("SELECT * FROM tokenDb WHERE id LIKE :id LIMIT 1")
    suspend fun findById(id: Int): Token

    @Query ("DELETE FROM tokenDb")
    suspend fun deleteAll()

}