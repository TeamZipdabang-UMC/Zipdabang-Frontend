package com.UMC.zipdabang.src.setting

import retrofit2.Call
import retrofit2.http.*

interface RetrofitQuitService {




    @PATCH("/users/my-page/quit")
    fun quit(
        @Header("x-access-token") token: String?,
    ): Call<QUit_Respsonse>



}