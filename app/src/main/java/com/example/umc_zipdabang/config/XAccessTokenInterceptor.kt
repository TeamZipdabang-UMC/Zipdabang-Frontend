/*
package com.example.umc_zipdabang.config

import com.example.umc_zipdabang.config.ApplicationClass.Companion.sSharedPreferences
import okhttp3.Interceptor
import java.io.IOException

class XAccessTokenInterceptor: Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain):Response{
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = sSharedPreferences.getString("X_ACCESS_TOKEN", null)
        if(jwtToken !=null){
            builder.addHeader("X-ACESS-TOKEN", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}
*/
