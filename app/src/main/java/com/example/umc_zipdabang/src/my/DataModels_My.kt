package com.example.umc_zipdabang.src.my

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Multipart

//DTO 클래스 선언
data class PostNewRecipeBody(
    @SerializedName("recipe") var recipe: PostNewRecipeList?,
    @SerializedName("ingredient") var ingredient: ArrayList<PostNewRecipeIngredient>,
    @SerializedName("steps") var steps:ArrayList<PostNewRecipeSteps>
)
data class PostNewRecipeList(
    @SerializedName("category") var category : Int?,
    @SerializedName("name") var name: String?,
    @SerializedName("intro") var intro : String?,
    @SerializedName("review") var review: String?,
    @SerializedName("take_time") var take_time : String?,
    @SerializedName("image_url") var image_url: String?
)
data class PostNewRecipeIngredient(
    @SerializedName("name") var name : String?,
    @SerializedName("quantity") var quantity: String?
)
data class PostNewRecipeSteps(
    @SerializedName("step") var step : String?,
    @SerializedName("detail") var detail: String?,
    @SerializedName("step_image_url") var step_image_url: String?
)
data class PostNewRecipeBodyResponse(
    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var data : String,
    @SerializedName("error") var error : String
)



data class PostNewRecipeImageBody(
    @SerializedName("img") var img: MultipartBody
)
data class PostNewRecipeImageBodyResponse(
    @SerializedName("success") var success : Boolean,
    @SerializedName("data") var data : ArrayList<PostNewRecipeImageList>,
    @SerializedName("error") var error : String
)
data class PostNewRecipeImageList(
    @SerializedName("image") var image : String
)


data class PostNewuserBody(
    @SerializedName("name") var name : String?,
    @SerializedName("nickname") var nickname : String?,
    @SerializedName("phoneNum") var phoneNum : String?,
    @SerializedName("birth") var birth : String?,
    @SerializedName("email") var email : String?
)
data class PostNewuserBodyResponse(
    @SerializedName("success") var success : Boolean,
    @SerializedName("user") var user : ArrayList<PostNewuserBodyUser>
)
data class PostNewuserBodyUser(
    @SerializedName("email") var email : String?,
    @SerializedName("name") var name : String?,
    @SerializedName("birth") var birth : String?,
    @SerializedName("phoneNum") var phoneNum : String?
)
data class GetNicknameExistResponse(
    @SerializedName("exist") var exist : Boolean,
    @SerializedName("nickname") var nickname : String
)