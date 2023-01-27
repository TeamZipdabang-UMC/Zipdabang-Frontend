package com.example.umc_zipdabang.config.src.main.Retrofit

import com.example.umc_zipdabang.src.main.roomDb.Token
import com.example.umc_zipdabang.src.signup.GetNicknameExistResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitMainService {

    @GET("/")
    fun get_Main(
        @Header("x-access-token") token: String?
    ): Call<Main_Response>


    @GET("/recipes/search")
    fun get_Search(
        @Query("keyword") keyword : String?,
        @Header("x-access-token") token: String?
    ): Call<Search_Response>





}