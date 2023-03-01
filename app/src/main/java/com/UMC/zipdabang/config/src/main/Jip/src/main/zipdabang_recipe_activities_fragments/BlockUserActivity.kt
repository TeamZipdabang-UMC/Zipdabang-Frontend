package com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.UMC.umc_zipdabang.R
import com.UMC.umc_zipdabang.databinding.ActivityBlockUserBinding
import com.UMC.zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.UserBlockBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class BlockUserActivity: AppCompatActivity() {
    lateinit var viewBinding: ActivityBlockUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityBlockUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        var isAgreeChecked = false

        val tokenDb = TokenDatabase.getTokenDatabase(this)
        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("http://zipdabang.store:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RecipeService::class.java)

        val blockUserId = intent.getStringExtra("blockUserId")?.toInt()
        Log.d("차단할 유저의 아이디", "${blockUserId}")

        val blockRadioGroup = viewBinding.radioForBlock

        blockRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.btn_agree_block -> {
                    isAgreeChecked = true
                    Log.d("사용자 차단 라디오박스 체크됨", "${isAgreeChecked}")
                    viewBinding.btnBlockInactive.visibility = View.GONE
                    viewBinding.btnBlockActive.visibility = View.VISIBLE
                }
            }
        }

        viewBinding.btnBlockActive.setOnClickListener {
            if (isAgreeChecked == true && blockUserId != null) {
                GlobalScope.launch(Dispatchers.IO) {
                    val token = tokenDb.tokenDao().getToken()
                    val tokenNum = token.token
                    Log.d("토큰 넘버", "${tokenNum}")
                    service.blockUser(tokenNum, UserBlockBody(blockUserId))
                        .enqueue(object : Callback<UserBlockResponse> {
                            override fun onResponse(
                                call: Call<UserBlockResponse>,
                                response: Response<UserBlockResponse>
                            ) {
                                val result = response.body()
                                if (response.code() == 200) {
                                    Log.d("사용자 차단 성공", result?.success.toString())
                                    Toast(this@BlockUserActivity).apply {
                                        duration = Toast.LENGTH_SHORT
                                        setGravity(Gravity.BOTTOM, 0, 0)
                                        val layout: View = layoutInflater.inflate(R.layout.toast_user_block_layout, findViewById(R.id.toast_block_user))
                                        view = layout
                                    }.show()
                                } else {
                                    Log.d("사용자 차단 실패", "다른 원인")
                                }
                                finish()
                            }

                            override fun onFailure(call: Call<UserBlockResponse>, t: Throwable) {
                                Log.d("사용자 차단 실패", t.message.toString())
                                finish()
                            }
                        })
                }
            }
        }
        viewBinding.toolbarExit.setOnClickListener {
            finish()
        }
    }
}