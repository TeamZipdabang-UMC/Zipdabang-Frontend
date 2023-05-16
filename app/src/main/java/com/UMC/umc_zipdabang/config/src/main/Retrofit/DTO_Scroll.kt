package com.UMC.umc_zipdabang.config.src.main.Retrofit

import com.google.gson.annotations.SerializedName

data class DTO_Scroll_Response(

    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data : ArrayList<Scroll>?,

    @SerializedName("error")
    var error: Any?

)


data class Scroll(

    @SerializedName("Id")
    var recipeid: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("likes")
    var likes: Int?,


)
data class DTO_Scroll_Response2(

    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data : ArrayList<Scroll2>?,

    @SerializedName("error")
    var error: Any?

)


data class Scroll2(

    @SerializedName("id")
    var recipeid: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("image")
    var image: String?,
    @SerializedName("likes")
    var likes: Int?,


    )