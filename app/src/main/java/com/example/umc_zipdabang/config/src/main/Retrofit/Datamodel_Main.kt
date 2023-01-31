package com.example.umc_zipdabang.config.src.main.Retrofit

import com.google.gson.annotations.SerializedName



data class Main_Response(

    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data : DataArray?,

   @SerializedName("error")
    var error: Any?

)

data class Delete(

    @SerializedName("target")
    var target : Array<Int>?


)






data class DataArray(

    @SerializedName("myScrapOverView")
   val myScrapOverView : ArrayList<Data>?,
    @SerializedName("coffeeCategoryOverView")
    val coffeeCategoryOverView : ArrayList<Data>?,
    @SerializedName("beverageCategoryOverView")
    val beverageCategoryOverView : ArrayList<Data>?,
    @SerializedName("teaCategoryOverView")
    val teaCategoryOverView : ArrayList<Data>?,
    @SerializedName("adeCategoryOverView")
    val adeCategoryOverView : ArrayList<Data>?,
    @SerializedName("smoothieCategoryOverView")
    val smoothieCategoryOverView : ArrayList<Data>?,
    @SerializedName("healthCategoryOverView")
    val healthCategoryOverView : ArrayList<Data>?

    )


data class Data(


    @SerializedName("recipeId")
    var recipeid : Int?,

    @SerializedName("likes")
    var likes : Int?,

    @SerializedName("image")
    var image : String?,

    @SerializedName("name")
    var name : String?,




    )

