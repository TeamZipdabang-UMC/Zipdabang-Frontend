package com.UMC.zipdabang.config.src.main.Jip.src.main.roomDb

import androidx.room.*

@Dao
interface CommentDao {
    // 사용한다. 커멘트 하나를 넣는 작업
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addComment(commentDbClass: CommentDbClass)

    // 맨 위에 것 하나만 가져오기 - 쓸일이 있을지 의문...
    @Query("Select * from commentDb Limit 1")
    fun getComment(): CommentDbClass

    // 해당 레시피 아이디에 맞는 레코드 하나를 가져오기
    @Query("Select * from commentDb where commentId like :commentId LIMIT 1")
    fun getCommentById(commentId: Int): CommentDbClass

    // 사용 안함.
    @Delete
    suspend fun deleteComment(commentDbClass: CommentDbClass)

    // 전체 코멘트 삭제에 사용할 것.
    @Query("DELETE FROM commentDb")
    suspend fun deleteAllComments()
}