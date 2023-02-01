package com.example.umc_zipdabang.config.src.main.SocialLogin

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.example.umc_zipdabang.databinding.ActivityInitialBinding
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

        viewBinding.btnSignIn.setOnClickListener {
            val intent = Intent(this, JoinInitialActivity::class.java)
            startActivity(intent)

        }

       val tokenDb = TokenDatabase.getTokenDatabase(this)
      GlobalScope.launch(Dispatchers.IO) {
           tokenDb.tokenDao().deleteAll()
      }


        // 카카오 키 해시값 확인 목적
        Log.d(ContentValues.TAG, "keyhash:${Utility.getKeyHash(this)}")



//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://zipdabang.store:3000")
//            .addConverterFactory(GsonConverterFactory.create()).build()
//        val retrofitService = retrofit.create(RecipeService::class.java)
//
//        retrofitService.test(Test(null)).enqueue(object : Callback<Success> {
//            override fun onResponse(call: Call<Success>, response: Response<Success>) {
//                val result = response.body()
//                Log.d("가져오기 성공", "${result}")
//            }
//
//            override fun onFailure(call: Call<Success>, t: Throwable) {
//                Log.d("가져오기", "실패")
//            }
//        })

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