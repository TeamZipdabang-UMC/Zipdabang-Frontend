package com.UMC.zipdabang.config.src.main.Jip.src.main

import android.content.Context

class AssetLoader {

    // 예외처리를 위하여...
    fun getJsonString(context: Context, fileName: String): String? {
        return kotlin.runCatching { // 성공/실패케이스를 나눠야 하는 경우 runCatching 함수 이용
            loadAsset(context, fileName)
        }.getOrNull() // Exception 발생 시 null 반환, 성공 시 원하는 값 반환
    }

    private fun loadAsset(context: Context, fileName: String): String {
        return context.assets.open("home.json").use { inputStream ->
            val size = inputStream.available()
            val bytes = ByteArray(size)
            inputStream.read(bytes)
            String(bytes) // 반환값(마지막줄)

        }
    }
}