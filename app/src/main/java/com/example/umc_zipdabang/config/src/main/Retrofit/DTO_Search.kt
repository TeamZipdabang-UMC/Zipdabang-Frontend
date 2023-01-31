package com.example.umc_zipdabang.config.src.main.Retrofit

import com.google.gson.annotations.SerializedName


data class Search_Response (

    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data : SearchArray?,

    @SerializedName("error")
    var error: Any?

        )



data class SearchArray(

    @SerializedName("coffeeSearch")
    val coffeeSearch : ArrayList<SearchData>?,
    @SerializedName("beverageSearch")
    val beverageSearch : ArrayList<SearchData>?,
    @SerializedName("teaSearch")
    val teaSearch : ArrayList<SearchData>?,
    @SerializedName("adeSearch")
    val adeSearch : ArrayList<SearchData>?,
    @SerializedName("smoothieSearch")
    val smoothieSearch : ArrayList<SearchData>?,
    @SerializedName("healthSearch")
    val healthSearch : ArrayList<SearchData>?

)


data class SearchData(


    @SerializedName("id")
    var id : Int?,

    @SerializedName("image_url")
    var image_url : String?,

    @SerializedName("name")
    var name : String?,

    @SerializedName("likes")
    var likes : Int?,




    )