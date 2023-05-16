package com.UMC.umc_zipdabang.src.my

import retrofit2.Call
import okhttp3.MultipartBody
import retrofit2.http.*

interface APIS_My {

    //레시피 업로드
    @POST("/recipes/new-recipe")
    fun post_newrecipe(
        @Header("x-access-token") token: String?,
        @Body newrecipe: PostNewRecipeBody,
    ): Call<PostNewRecipeBodyResponse>

    //썸네일과 각 스텝별 이미지 보내기
    @Multipart
    @POST("/recipes/thumb-picture")
    fun post_newrecipe_image(
        @Header("x-access-token") token: String,
        @Part img: MultipartBody.Part,
    ): Call<PostNewRecipeImageBodyResponse>

    //임시저장한 이미지 보내기
    @POST("/recipes/temp-recipe")
    fun post_newrecipe_saveimage(
        @Header("x-access-token") token: String?,
        @Body newrecipe: PostNewRecipeSaveImage,
    ): Call<PostNewRecipeBodyResponse>

    //임시저장한 이미지 받기
    @GET("/recipes/temp-recipe")
    fun get_newrecipe_saveimage(
        @Header("x-access-token") token: String?,
    ): Call<GetNewRecipeSaveImageResponse>


    //도전중 레시피
    @GET("/users/my-page/my-challenging")
    fun get_challengingrecipe(
        @Header("x-access-token") token: String?,
    ): Call<GetChallengingRecipeResponse>

    //도전완료 레시피
    @GET("/users/my-page/my-complete")
    fun get_challengedonerecipe(
        @Header("x-access-token") token: String?,
    ): Call<GetChallengedoneRecipeResponse>

    //내레시피
    @GET("/recipes/my-recipes")
    fun get_myrecipe(
        @Header("x-access-token") token: String?,
    ): Call<GetMyRecipeResponse>

    //내레시피 삭제
    @HTTP(method = "DELETE", path = "http://zipdabang.store:3000/recipes/my-recipes/delete", hasBody = true)
    fun deleteScrap(
        @Header("x-access-token") token: String?,
        @Body target: Delete
    ): Call<Scrap_Delete_Response>

    //내 스크랩 도전중 도전완료 2개씩 보여줌
    @GET("/users/my-page")
    fun get_recipe_two(
        @Header("x-access-token") token: String?,
    ) : Call<GetRecipeTwoResponse>

    @GET("/users/my-page/my-info")
    fun get_nickname_email(
        @Header("x-access-token") token: String?,
    ) : Call<GetNicknameEmailResponse>
}