package com.example.umc_zipdabang.src.my

import retrofit2.Call
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface APIS {

    @POST("/recipes/new-recipe")
    fun post_newrecipe(
        @Header("x-access-token") token: String?,
        @Body newrecipe: PostNewRecipeBody
    ): Call<PostNewRecipeBodyResponse>


    @Multipart
    @POST("/recipes/thumb-picture")
    fun post_newrecipe_image(
        @Header("x-access-token") token: String,
        //@Body newrecipethumbnail: PostNewRecipeImageBody,
        @Part imageFile: MultipartBody.Part
    ): Call<PostNewRecipeImageBodyResponse>


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