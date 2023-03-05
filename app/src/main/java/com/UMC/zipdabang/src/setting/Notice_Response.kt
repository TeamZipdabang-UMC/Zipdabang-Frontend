package com.UMC.zipdabang.src.setting

import com.google.gson.annotations.SerializedName

data class Notice_Response(

@SerializedName("success")
var success: Boolean?,

@SerializedName("data")
var data : Array<DataArray>?,

@SerializedName("error")
var error: Any?

)

data class DataArray(

    @SerializedName("id")
    var noticeid : Int? ,

    @SerializedName("title")
    var noticetitle:  String?,

    @SerializedName("created_at")
    var created_at : String?

)

data class Notice_Detail_Response(

    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data : Array<DataNoticeArray>?,

    @SerializedName("error")
    var error: Any?

)

data class DataNoticeArray(

    @SerializedName("title")
    var title : String? ,

    @SerializedName("body")
    var body:  String?,

    @SerializedName("created_at")
    var created_at : String?



)

