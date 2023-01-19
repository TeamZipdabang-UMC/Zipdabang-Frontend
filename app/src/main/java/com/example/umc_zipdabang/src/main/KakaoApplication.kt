package com.example.umc_zipdabang.src.main

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KakaoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "8300d879d66a5f65861ac2de40a4234c")
    }
}