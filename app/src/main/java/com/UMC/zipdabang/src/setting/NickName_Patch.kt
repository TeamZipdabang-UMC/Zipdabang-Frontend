package com.UMC.zipdabang.src.setting

import com.google.gson.annotations.SerializedName

data class NickName_Respsonse (

    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data : Any?,

    @SerializedName("error")
    var error: Any?

)



data class NickName_Patch(

     @SerializedName("nickname")
     var nickname : String?

 )