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
import com.example.umc_zipdabang.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivitySplashBinding
    private var token: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val tokenDb = TokenDatabase.getTokenDatabase(this)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                token = tokenDb.tokenDao().getToken().token.toString()
                Log.d("룸디비에 토큰 있음", "${token}")
            } catch (e: java.lang.NullPointerException) {
                Log.d("룸디비에 토큰 비어있음", "${e.message}")
                token = ""
            }

        }

        Log.d("시작 토큰", "${token}")

        Handler(Looper.getMainLooper()).postDelayed({
            if (token != "") {
                val intent = Intent( this, HomeMainActivity::class.java)
                startActivity(intent)
                // 이전 키를 눌렀을 때 스플래스 스크린 화면으로 이동을 방지하기 위해 finish 처리
                finish()
            } else {
                val intent = Intent( this, InitialActivity::class.java)
                startActivity(intent)
                finish()
            }


        }, 1000) // 시간 0.5초 이후 실행

    }
}