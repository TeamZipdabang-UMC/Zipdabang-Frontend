package com.UMC.umc_zipdabang.src.setting

import com.google.gson.annotations.SerializedName

data class FAQ_Response(

@SerializedName("success")
var success: Boolean?,

@SerializedName("data")
var data : Array<DFAQ>?,

@SerializedName("error")
var error: Any?

)

data class DFAQ(

    @SerializedName("id")
    var FAQid : Int? ,

    @SerializedName("question")
    var FAQquestion:  String?,

    @SerializedName("answer")
    var FAQanswer:  String?,

    )





data class FAQ_Detail_Response(

    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data : Array<DDFAQ>?,

    @SerializedName("error")
    var error: Any?

)

data class DDFAQ(


    @SerializedName("answer")
    var FAQanswer:  String?,

    )

