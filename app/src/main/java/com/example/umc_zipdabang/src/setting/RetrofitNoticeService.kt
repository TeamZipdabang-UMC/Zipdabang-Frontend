package com.example.umc_zipdabang.src.setting
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query



interface RetrofitNoticeService {

        @GET("/notice")
        fun get_notice_list(
            @Header("x-access-token") token: String?
        ): Call<Notice_Response>


    @GET("/notice/{noticeId}")
    fun getNoticeInfo(
        @Path("noticeId") noticeId: Int?,
        @Header("x-access-token") token: String?,
    ): Call<Notice_Detail_Response>




}