package com.example.umc_zipdabang.config.src.main.Retrofit

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body

import retrofit2.http.HTTP

data class Scrap_Delete_Response (

    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data : Any?,

    @SerializedName("error")
    var error: Any?

)


data class Scrap_Response(

    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data : DataScrap,

    @SerializedName("error")
    var error: Any?

)

data class DataScrap(

    @SerializedName("myScrap")
    var myScrap: ArrayList<SData>


)

data class SData(

    @SerializedName("recipeId")
    var recipeid: Int?,
    @SerializedName("likes")
    var likes: Int?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("name")
    var name: String?

)
