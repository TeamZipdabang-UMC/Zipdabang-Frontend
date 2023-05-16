package com.UMC.umc_zipdabang.src.setting

import com.google.gson.annotations.SerializedName

data class QUit_Respsonse (

    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data : Any?,

    @SerializedName("error")
    var error: Any?

)



