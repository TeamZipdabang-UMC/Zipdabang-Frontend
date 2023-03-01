package com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.UMC.umc_zipdabang.R
import com.UMC.umc_zipdabang.databinding.ActivityZipdabangRecipeCommentReportBinding
import com.UMC.zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.CommentReportBody
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.CommentReportResponse
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.RecipeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class ZipdabangRecipeCommentReportActivity: AppCompatActivity() {
    lateinit var viewBinding: ActivityZipdabangRecipeCommentReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeCommentReportBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val moveIntent = Intent(this, ZipdabangRecipeDetailCommentActivity::class.java)

        val tokenDb = TokenDatabase.getTokenDatabase(this)
        val retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("http://zipdabang.store:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RecipeService::class.java)

        var reportCrimeId: Int? = null
        val reportCommentId = intent.getStringExtra("reportCommentId")?.toInt()
        Log.d("신고할 댓글의 아이디", "${reportCommentId}")
        val recipeId = intent.getStringExtra("recipeId")?.toInt()
        Log.d("신고할 댓글이 있는 레시피 번호", "${recipeId}")


        val reportRadioGroup = viewBinding.radioReasonsForReport
        val reportRadioPorn = viewBinding.btnReportReasonPorn
        val reportRadioIllegalInfo = viewBinding.btnReportReasonIllegalInfo
        val reportRadioPromotion = viewBinding.btnReportReasonPromotionPlaster
        val reportRadioPersonalInfo = viewBinding.btnReportReasonPersonalInfo

        reportRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.btn_report_reason_porn -> {
                    reportCrimeId = 1
                    Log.d("댓글 크라임 아이디", "${reportCrimeId}")
                    viewBinding.btnReportInactive.visibility = View.GONE
                    viewBinding.btnReportActive.visibility = View.VISIBLE
                }
                R.id.btn_report_reason_illegal_info -> {
                    reportCrimeId = 2
                    Log.d("댓글 크라임 아이디", "${reportCrimeId}")
                    viewBinding.btnReportInactive.visibility = View.GONE
                    viewBinding.btnReportActive.visibility = View.VISIBLE
                }
                R.id.btn_report_reason_promotion_plaster -> {
                    reportCrimeId = 3
                    Log.d("댓글 크라임 아이디", "${reportCrimeId}")
                    viewBinding.btnReportInactive.visibility = View.GONE
                    viewBinding.btnReportActive.visibility = View.VISIBLE
                }
                R.id.btn_report_reason_personal_info -> {
                    reportCrimeId = 4
                    Log.d("댓글 크라임 아이디", "${reportCrimeId}")
                    viewBinding.btnReportInactive.visibility = View.GONE
                    viewBinding.btnReportActive.visibility = View.VISIBLE
                }
            }
        }

//        if (reportCrimeId == null) {
//            viewBinding.btnReportInactive.visibility = View.VISIBLE
//            viewBinding.btnReportActive.visibility = View.GONE
//        } else {
//
//        }

        viewBinding.btnReportActive.setOnClickListener {
            if (reportCommentId != null && reportCrimeId != null) {
                GlobalScope.launch(Dispatchers.IO) {
                    val token = tokenDb.tokenDao().getToken()
                    val tokenNum = token.token
                    Log.d("토큰 넘버", "${tokenNum}")
                    service.reportComment(tokenNum, CommentReportBody(reportCommentId, reportCrimeId))
                        .enqueue(object: Callback<CommentReportResponse> {
                            override fun onResponse(
                                call: Call<CommentReportResponse>,
                                response: Response<CommentReportResponse>
                            ) {
                                val result = response.body()
                                if (response.code() == 200) {
                                    Log.d("댓글 신고 성공", result?.success.toString())
                                    Toast(this@ZipdabangRecipeCommentReportActivity).apply {
                                        duration = Toast.LENGTH_SHORT
                                        setGravity(Gravity.BOTTOM, 0, 0)
                                        val layout: View = layoutInflater.inflate(R.layout.toast_comment_report_layout, findViewById(R.id.toast_report_comment))
                                        view = layout
                                    }.show()
                                } else {
                                    Log.d("댓글 신고 실패", "다른 원인")
                                }

                                finish()
//                                moveIntent.putExtra("recipeId", recipeId.toString())
//                                startActivity(moveIntent)
                            }

                            override fun onFailure(
                                call: Call<CommentReportResponse>,
                                t: Throwable
                            ) {
                                Log.d("댓글 신고 실패", t.message.toString())
                                finish()
//                                moveIntent.putExtra("recipeId", recipeId.toString())
//                                startActivity(moveIntent)
                            }
                        })
                }
            }
        }

        viewBinding.toolbarExit.setOnClickListener {
            finish()
//            moveIntent.putExtra("recipeId", recipeId.toString())
//            startActivity(moveIntent)
        }

    }
}