package com.example.umc_zipdabang.config.src.main.signup

import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.Token
import com.google.gson.annotations.SerializedName

//DTO 클래스 선언
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