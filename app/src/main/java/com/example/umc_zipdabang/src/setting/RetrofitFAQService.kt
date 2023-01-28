package com.example.umc_zipdabang.src.setting

import retrofit2.Call
import retrofit2.http.*

interface RetrofitFAQService {



    @GET("/notice/faq")
    fun get_faq_list(
        @Header("x-access-token") token: String?
    ): Call<FAQ_Response>


    @GET("/notice/faq/{faqId}")
    fun getFAQInfo(
        @Path("faqId") faqId: Int?,
        @Header("x-access-token") token: String?,
    ): Call<FAQ_Detail_Response>



}