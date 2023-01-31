package com.example.umc_zipdabang.config.src.main.Retrofit

import retrofit2.Call
import retrofit2.http.*

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

    @GET("/users/my-page/my-scrap")
    fun get_Scrap_List(
        @Header("x-access-token") token: String?
    ): Call<Scrap_Response>


    @HTTP(method = "DELETE", path = "http://zipdabang.store:3000/users/my-page/my-scrap/delete", hasBody = true)
    fun deleteScrap(
        @Header("x-access-token") token: String?,
        @Body target: Delete
    ): Call<Scrap_Delete_Response>


    @GET("/recipes/category")
    fun get_Category(
        @Query("categoryId") categoryId : Int?,
        @Query("main_page") main_page : Int?,
        @Query("is_official")  is_official : Int?,
        @Header("x-access-token") token: String?,


    ): Call<DTO_Scroll_Response>


    @GET("/recipes/category/paging")
    fun get_Scroll_Category(
        @Query("categoryId") categoryId : Int?,
        @Query("last") last: Int?,
        @Query("isMain") main_page : Int?,
        @Query("isOfficial")  is_official : Int?,
        @Header("x-access-token") token: String?,


        ): Call<DTO_Scroll_Response2>

}