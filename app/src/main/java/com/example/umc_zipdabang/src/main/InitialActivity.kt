package com.example.umc_zipdabang.src.main

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.databinding.ActivityInitialBinding
import com.example.umc_zipdabang.src.main.roomDb.TokenDatabase
import com.kakao.sdk.common.util.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InitialActivity: AppCompatActivity() {

    lateinit var viewBinding: ActivityInitialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityInitialBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val tokenDb = TokenDatabase.getTokenDatabase(this)
        val token = tokenDb.tokenDao().getToken()
        val tokenNum = token.token.toString()

     //  GlobalScope.launch(Dispatchers.IO) {
       //  tokenDb.tokenDao().deleteAll()
        //}
        viewBinding.btnSignIn.setOnClickListener {
            val intent = Intent(this, JoinInitialActivity::class.java)
            startActivity(intent)
        }

        // 카카오 키 해시값 확인 목적
        Log.d(ContentValues.TAG, "keyhash:${Utility.getKeyHash(this)}")

        // 로그인 화면이 만들어지면 구현 예정
        viewBinding.tvLoginUnderline.setOnClickListener {

        }

//        val handler = Handler(Looper.getMainLooper())
//        handler.postDelayed({
//            // 만약 로그인이 안되어 있다면, LoginJoinActivity로 이동하고, 로그인 상태라면 홈화면으로 이동
//            // if (로그인 == true) { LoginJoinActivity 이동 } else { MainActivity 이동 }
//            val intent = Intent(this, LoginJoinActivity::class.java)
//            startActivity(intent)
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//            finish()
//        }, 1500)
    }
}