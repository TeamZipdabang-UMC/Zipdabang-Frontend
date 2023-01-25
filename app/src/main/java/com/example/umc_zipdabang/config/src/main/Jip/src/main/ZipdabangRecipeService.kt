package com.example.umc_zipdabang.config.src.main.Jip.src.main

import org.apache.commons.lang3.ObjectUtils.Null
import retrofit2.Call
import retrofit2.http.*

interface RecipeService {

    // 집다방 레시피와 우리들의 레시피 모두 지원하는 인터페이스임.
    // 물음표 없으면 초기에 받아오는것.
    @FormUrlEncoded
    @GET ("/recipes/category")
    fun getCategoryRecipes(
        @Header("x-access-token") token: String?,
        @Query("categoryId") categoryId: Int,
        @Query("main_page") mainPage: Int,
        @Query("is_official") isOfficial: Int): Call<ZipdabangRecipes>

    // 물음표 있으면 스크롤 이후 받아오는 것.
    @FormUrlEncoded
    @GET ("/recipes/category/paging")
    fun getCategoryRecipesScroll(
        @Header("x-access-token") token: String?,
        @Query("categoryId") categoryId: Int,
        @Query("main_page") mainPage: Int, // 나는 무조건 0
        @Query("is_official") isOfficial: Int): Call<ZipdabangRecipes>

    // 개별 레시피 get
//    @GET ("/recipes/:recipeId/info")
//    fun getDetailRecipe(@Query(""))

    @POST ("/comments/new-comment")
    fun addComment(
        @Header("x-access-token") token: String?,
        @Field("target") target: Int,
        @Field("body") body: String): Call<CommentAddResponse>

    @GET ("/comments/comments-overview?recipe={recipeId}")
    fun getThreeComments(@Query("recipe") recipeId: Int): Call<ThreeCommentsResponse>

    // 댓글 조회 처음
    @GET ("/comments/comments-view-first?recipe={recipeId}")
    fun getRecipeComments(
        @Header("x-access-token") token: String?,
        @Query("recipe") recipeId: Int): Call<CommentsResponse>

    // 댓글 스크롤
    @GET ("/comments/comments-view-more?recipe={recipeId}&last={commentId}")
    fun getMoreRecipeComments(
        @Header("x-access-token") token: String?,
        @Query("recipe") recipeId: Int,
        @Query("last") last: Int): Call<CommentsResponse>

    // 댓글 수정
    @PATCH ("/comments/comments-update")
    fun editComment(
        @Header ("x-access-token") token: String?,
        @Field ("owner") owner: Int?,
        @Field ("commentId") commentId: Int?,
        // 새로운 댓글의 내용
        @Field ("newBody") newBody: String?
    ): Call<CommentEditResponse>

    @DELETE ("/comments/comments-delete")
    fun deleteComment(
        @Header ("x-aceess-token") token: String?,
        @Field ("owner") owner: Int?,
        @Field ("commentId") commentId: Int?
    ): Call<CommentEditResponse>
}