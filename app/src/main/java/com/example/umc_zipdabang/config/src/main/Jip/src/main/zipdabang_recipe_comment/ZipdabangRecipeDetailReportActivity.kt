package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.*
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailReportBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class ZipdabangRecipeDetailReportActivity: AppCompatActivity() {
    lateinit var viewBinding: ActivityZipdabangRecipeDetailReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeDetailReportBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        var reportCrimeId: Int? = null
        val reportedRecipeId = intent.getStringExtra("recipeId")?.toInt()
        Log.d("신고할 레시피 아이디", reportedRecipeId.toString())
        var isTextAvailable = false

        val tokenDb = TokenDatabase.getTokenDatabase(this)
        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("http://zipdabang.store:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RecipeService::class.java)

        val editText = viewBinding.editDetailForReports
        val reportRadioGroup = viewBinding.radioReasonsForReport

        reportRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.btn_report_reason_porn -> {
                    reportCrimeId = 1
                    Log.d("레시피 크라임 아이디", "${reportCrimeId}")
                    if (isTextAvailable) {
                        viewBinding.btnReportInactive.visibility = View.GONE
                        viewBinding.btnReportActive.visibility = View.VISIBLE
                    }
                }
                R.id.btn_report_reason_illegal_info -> {
                    reportCrimeId = 2
                    Log.d("레시피 크라임 아이디", "${reportCrimeId}")
                    if (isTextAvailable) {
                        viewBinding.btnReportInactive.visibility = View.GONE
                        viewBinding.btnReportActive.visibility = View.VISIBLE
                    }
                }
                R.id.btn_report_reason_promotion_plaster -> {
                    reportCrimeId = 3
                    Log.d("레시피 크라임 아이디", "${reportCrimeId}")
                    if (isTextAvailable) {
                        viewBinding.btnReportInactive.visibility = View.GONE
                        viewBinding.btnReportActive.visibility = View.VISIBLE
                    }
                }
                R.id.btn_report_reason_personal_info -> {
                    reportCrimeId = 4
                    Log.d("레시피 크라임 아이디", "${reportCrimeId}")
                    if (isTextAvailable) {
                        viewBinding.btnReportInactive.visibility = View.GONE
                        viewBinding.btnReportActive.visibility = View.VISIBLE
                    }
                }
            }
        }

        editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isTextAvailable = editText?.text.toString().length >= 10
                viewBinding.tvTextSizeCounts.text = s?.length.toString()
                if (isTextAvailable && reportCrimeId != null) {
                    viewBinding.btnReportInactive.visibility = View.GONE
                    viewBinding.btnReportActive.visibility = View.VISIBLE
                } else {
                    viewBinding.btnReportInactive.visibility = View.VISIBLE
                    viewBinding.btnReportActive.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                isTextAvailable = editText?.text.toString().length >= 10
                viewBinding.tvTextSizeCounts.text = s?.length.toString()
                if (isTextAvailable && reportCrimeId != null) {
                    viewBinding.btnReportInactive.visibility = View.GONE
                    viewBinding.btnReportActive.visibility = View.VISIBLE
                } else {
                    viewBinding.btnReportInactive.visibility = View.VISIBLE
                    viewBinding.btnReportActive.visibility = View.GONE
                }
            }
        })

        // 레시피 신고버튼 누를 때
        viewBinding.btnReportActive.setOnClickListener {
            if (reportedRecipeId != null && reportCrimeId != null && isTextAvailable) {
                Log.d("레시피 신고 조건을 만족", "신고 가능")
                GlobalScope.launch(Dispatchers.IO) {
                    val token = tokenDb.tokenDao().getToken()
                    val tokenNum = token.token




                    Log.d("토큰 넘버", "${tokenNum}")
                    service.reportRecipe(tokenNum, RecipeReportBody(reportedRecipeId, reportCrimeId))
                        .enqueue(object : Callback<RecipeReportResponse> {
                            override fun onResponse(
                                call: Call<RecipeReportResponse>,
                                response: Response<RecipeReportResponse>
                            ) {
                                val result = response.body()
                                Log.d("레시피 신고 응답 코드", response.code().toString())
                                if (response.code() == 200) {
                                    Log.d("레시피 신고 성공", result?.success.toString())
                                    finish()
                                } else if (response.code() == 400) {
                                    Log.d("레시피 신고 실패", "필요한 데이터 안넘어옴")
                                } else if (response.code() == 401) {
                                    Log.d("레시피 신고 실패", "토큰 안넘어옴")
                                } else {
                                    Log.d("레시피 신고 실패", "기타 이유")
                                    Log.d("레시피 신고 실패 바디", response.body().toString())
                                }

                            }

                            override fun onFailure(call: Call<RecipeReportResponse>, t: Throwable) {
                                TODO("Not yet implemented")
                            }
                        })
                }
            } else {
                Log.d("레시피 신고 조건을 만족하지 못함", "신고 불가")
            }
        }

        viewBinding.ivZipdabangRecipeDetailCommentsBackarrow.setOnClickListener {
            finish()
        }
    }
}

