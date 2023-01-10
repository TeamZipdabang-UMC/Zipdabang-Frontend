/*
package com.example.umc_zipdabang.config

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass: Application() {
    //템플릿 테스트용 서버 주소
    //val API_URL=""

    companion object{
        lateinit var sSharedPreferences: SharedPreferences

        val X_ACESS_TOKEN = "X-ACCESS-TOKEN"

        lateinit var sRetrofit: Retrofit
    }

    override fun onCreate(){
        super.onCreate()
        sSharedPreferences=
            applicationContext.getSharedPreferences("ZIPDABANG_APP", MODE_PRIVATE)
        initRetrofitInstance()
    }

    private fun initRetrofitInstance(){
        val client: OKHttpClient = OKHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connetTimeout(5000,TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor{message: String->
                Log.d("network_info",message)
            }.setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNeworkInterceptor(XAccessTokenInterceptor())
            .build()

        sRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }
}
 */