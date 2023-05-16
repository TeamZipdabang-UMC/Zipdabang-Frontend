package com.UMC.umc_zipdabang.config.src.main.Jip.src.main.roomDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface TokenDao {
    @Insert(onConflict = REPLACE)
    fun addToken(token: Token)

    // 토큰 맨 위에 것 하나만 가져오기
    @Query("Select * from tokenDb Limit 1")
    fun getToken(): Token

    // 거의 안쓸듯. 하지만 혹시 몰라서 넣어둠.
    @Query("SELECT * FROM tokenDb WHERE id LIKE :id LIMIT 1")
    suspend fun findById(id: Int): Token

    // 전체 토큰을 삭제하는 코드 - 거의 사용하지 않음.
    @Delete
    suspend fun delete(token: Token)

    // 전체 토큰을 삭제하는 코드
    @Query ("DELETE FROM tokenDb")
    suspend fun deleteAll()

    // 토큰 덮어쓰는 코드. 토큰 열이 null인 칼럼을 찾아 그 열을 바꿔주기
    @Query ("UPDATE tokenDb SET token=:token WHERE token LIKE :nullString")
    suspend fun updateToken(nullString: String, token: String)

}