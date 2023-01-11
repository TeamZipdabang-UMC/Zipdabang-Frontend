package com.example.umc_zipdabang.src.main

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KakaoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "dcb4164b5c93356e86a41ecbdfc66262")
    }
}