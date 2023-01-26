package com.example.umc_zipdabang.src.my

import retrofit2.Call
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface APIS {

    @POST("/users/user-data")
    fun post_signup_newuser(
        @Body userInfo: PostNewuserBody
    ): Call<PostNewuserBodyResponse>

    @GET("/users/exist-nickname?")
    //@Headers("Autorization:token 발급받은 키")
    fun get_signup_existnickname(
        @Query("nickname") nickname : String?
    ): Call<GetNicknameExistResponse>


    companion object{
        private const val BASE_URL = "http://zipdabang.store:3000"
        fun create():APIS{
            val gson: Gson = GsonBuilder().setLenient().create();

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(APIS::class.java)
        }
    }
}