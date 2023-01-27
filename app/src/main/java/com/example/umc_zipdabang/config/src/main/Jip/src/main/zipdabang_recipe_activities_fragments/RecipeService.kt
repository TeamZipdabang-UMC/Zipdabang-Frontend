package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RecipeService {
    // 집다방 레시피와 우리들의 레시피 모두 지원하는 인터페이스임.
    // 물음표 없으면 초기에 받아오는것.
    @GET ("/recipes/category")
    fun getCategoryRecipes(
        @Header("x-access-token") token: String?,
        @Query("categoryId") categoryId: Int,
        @Query("main_page") mainPage: Int,
        @Query("is_official") isOfficial: Int): Call<ZipdabangRecipes>

    // 물음표 있으면 스크롤 이후 받아오는 것.
    @GET ("/recipes/category/paging")
    fun getCategoryRecipesScroll(
        @Header("x-access-token") token: String?,
        @Query("categoryId") categoryId: Int,
        @Query("main_page") mainPage: Int, // 나는 무조건 0
        @Query("is_official") isOfficial: Int): Call<ZipdabangRecipes>

    // 집다방 레시피 전체 불러오기 - 초기
    @GET ("/recipes/official-recipe")
    fun getAllZipdabangRecipes(
        @Header("x-access-token") token: String?,
    ): Call<ZipdabangRecipes>

    // 집다방 레시피 전체 불러오기 - 스크롤
    @GET ("/recipes/official-recipe")
    fun getAllZipdabangRecipesScrolled(
        @Header("x-access-token") token: String?,
        @Query("last") last: Int?
    ): Call<ZipdabangRecipes>

    // 우리들의 레시피 전체 불러오기
    @GET ("/recipes/user-recipe")
    fun getAllOurRecipes(
        @Header("x-access-token") token: String?,
    ): Call<ZipdabangRecipes>

    // 우리들의 레시피 전체 불러오기 - 스크롤
    @GET ("/recipes/user-recipe")
    fun getAllUserRecipesScrolled(
        @Header("x-access-token") token: String?,
        @Query("last") last: Int?
    ): Call<ZipdabangRecipes>


    // 개별 레시피 get
    @GET ("/recipes/info")
    fun getDetailRecipe(
        @Header("x-access-token") token: String?,
        @Query("recipe") recipe: Int?): Call<RecipeDetailResponse>

    // 댓글쓰기
    @POST ("/comments/new-comment")
    fun addComment(
        @Header("x-access-token") token: String?,
        @Field("target") target: Int,
        @Field("body") body: String): Call<CommentAddResponse>

    // 댓글 3개 보여주기
    @GET ("/comments/comments-overview")
    fun getThreeComments(
        @Header("x-access-token") token: String?,
        @Query("recipe") recipeId: Int): Call<ThreeCommentsResponse>

    // 댓글 조회 처음
    @GET ("/comments/comments-view-first")
    fun getRecipeComments(
        @Header("x-access-token") token: String?,
        @Query("recipe") recipeId: Int): Call<CommentsResponse>

    // 댓글 스크롤
    @GET ("/comments/comments-view-more")
    fun getMoreRecipeComments(
        @Header("x-access-token") token: String?,
        @Query("recipe") recipeId: Int?,
        @Query("last") last: Int?): Call<CommentsResponse>

    // 댓글 수정
    @PATCH ("/comments/comments-update")
    fun editComment(
        @Header ("x-access-token") token: String?,
        @Field ("owner") owner: Int?,
        @Field ("commentId") commentId: Int?,
        // 새로운 댓글의 내용
        @Field ("newBody") newBody: String?
    ): Call<CommentEditResponse>

    @Headers("Content-Type: application/json")
    @HTTP(method = "DELETE", path="/comments/comments-delete", hasBody = true)
    fun deleteComment(
        @Header ("x-access-token") token: String?,
        @Body commentDeleteBody: CommentDeleteBody?
    ): Call<CommentEditResponse>


//    @GET ("/recipes/")


}