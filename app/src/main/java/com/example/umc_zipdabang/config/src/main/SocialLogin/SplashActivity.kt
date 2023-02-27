package com.example.umc_zipdabang.config.src.main.SocialLogin

import android.content.Intent
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.config.src.main.Home.HomeMainActivity
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.RecipeService
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.RunnerReportResponse
import com.example.umc_zipdabang.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SplashActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivitySplashBinding
    private var token: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val initialIntent = Intent(this, InitialActivity::class.java)
        val mainIntent = Intent(this, HomeMainActivity::class.java)
        var goToInitial: Boolean? = null

        val tokenDb = TokenDatabase.getTokenDatabase(this)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                token = tokenDb.tokenDao().getToken().token.toString()
                // api. 도망갔냐 아니냐
                // 응답 => 응답에 따라 join/login/run
                Log.d("룸디비에 토큰 있음", "${token}")
            } catch (e: java.lang.NullPointerException) {
                Log.d("룸디비에 토큰 비어있음", "${e.message}")
                token = ""
            }

        }

        Log.d("시작 토큰", "${token}")

        val runnerRetrofit = Retrofit.Builder()
            .baseUrl("http://zipdabang.store:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val runnerService = runnerRetrofit.create(RecipeService::class.java)

        if (token != "") {
            runnerService.isRunner(token).enqueue(object: Callback<RunnerReportResponse> {
                override fun onResponse(
                    call: Call<RunnerReportResponse>,
                    response: Response<RunnerReportResponse>
                ) {
                    if (response.code() == 200) {
                        val result = response.body()
                        Log.d("도망자 여부 GET 성공", "${result}")
                        goToInitial = result?.data
                        Log.d("도망자 여부", "${goToInitial}")
                        if (goToInitial == true) {
                            startActivity(initialIntent)
                            finish()
                        } else {
                            startActivity(mainIntent)
                            finish()
                        }
                    }
                }

                override fun onFailure(call: Call<RunnerReportResponse>, t: Throwable) {
                    Log.d("도망자 여부 색출 실패", "${t.message}")
                }

            })
        } else {
            goToInitial = true
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (goToInitial == true) {
                startActivity(initialIntent)
                // 이전 키를 눌렀을 때 스플래스 스크린 화면으로 이동을 방지하기 위해 finish 처리
                finish()
            } else {
                startActivity(mainIntent)
                finish()
            }
        }, 1000) // 시간 0.5초 이후 실행

    }
}