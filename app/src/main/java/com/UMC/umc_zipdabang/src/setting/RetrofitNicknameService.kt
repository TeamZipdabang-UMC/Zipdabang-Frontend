package com.UMC.umc_zipdabang.src.setting

import retrofit2.Call
import retrofit2.http.*

interface RetrofitNicknameService {


    @PATCH("/users/my-page/new-nickname")
    fun change_nickname(
        @Header("x-access-token") token: String?,
        @Body nickname : NickName_Patch?
    ): Call<NickName_Respsonse>


}