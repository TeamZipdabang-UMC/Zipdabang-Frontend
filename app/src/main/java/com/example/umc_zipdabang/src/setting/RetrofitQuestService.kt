package com.example.umc_zipdabang.src.setting

import retrofit2.Call
import retrofit2.http.*

interface RetrofitQuestService {

    @POST("/notice/question")
    fun post_question(
        @Header("x-access-token") token: String?, @Body quest: Question_Post?
    ): Call<Question_Response>


    @GET("/notice/questionlist")
    fun get_quest_list(
        @Header("x-access-token") token: String?
    ): Call<Questionlist_Response>


    @GET("/notice/questiondetail/{questionId}")
    fun getQuestionInfo(
        @Path("questionId") questionId: Int?,
        @Header("x-access-token") token: String?,
    ): Call<QuestionDetail_Response>



}