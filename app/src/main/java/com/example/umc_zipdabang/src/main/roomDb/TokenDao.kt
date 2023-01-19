package com.example.umc_zipdabang.src.main.roomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface TokenDao {
    @Insert(onConflict = REPLACE)
    fun insertToken(token: Token)

    @Query ("DELETE FROM tokenDB WHERE token = :token")
    fun deleteToken(token: Token)

    @Query ("SELECT FROM tokenDB WHERE token = :token")
    fun getToken(token: Token)

    @Delete
    fun deleteAllToken()

}