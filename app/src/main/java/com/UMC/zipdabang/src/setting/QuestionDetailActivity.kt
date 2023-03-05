package com.UMC.zipdabang.src.setting

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.UMC.umc_zipdabang.R
import com.UMC.umc_zipdabang.databinding.ActivityMyEditQuestionBinding
import com.UMC.zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionDetailActivity  :  AppCompatActivity(){
    private lateinit var viewbinding: ActivityMyEditQuestionBinding
    val questionarray : ArrayList<question> = arrayListOf()
    private lateinit var quest : Question_Post

    override fun onCreate(savedInstanceState: Bundle?) {
        viewbinding = ActivityMyEditQuestionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewbinding.root)

        var agree : Boolean = false
        var empty = true


        viewbinding.etEmail.addTextChangedListener {



            viewbinding.etEmail.addTextChangedListener{

                if(viewbinding.etEmail.text.isNotEmpty()) {
                    viewbinding.etEmail.setTag("1")

                    if(viewbinding.etContent.text.toString().length >= 1) {
                        viewbinding.btQuestion.setBackgroundResource(R.drawable.quest_bg_bt)
                        viewbinding.btQuestion.setTextColor(
                            (ContextCompat.getColor(
                                applicationContext,
                                R.color.black
                            ))
                        )
                        empty=false

                    }

                }else {
                    viewbinding.etEmail.setTag("0")
                    viewbinding.btQuestion.setBackgroundResource(R.drawable.quest_bg_bt_gray)
                    viewbinding.btQuestion.setTextColor((ContextCompat.getColor(applicationContext,R.color.jipdabang_login_edit_gray)))
                    empty=true

                }

            }

        viewbinding.etContent.addTextChangedListener {


            if (viewbinding.etContent.text.toString().length == 0) {
                viewbinding.btQuestion.setBackgroundResource(R.drawable.quest_bg_bt_gray)
                viewbinding.btQuestion.setTextColor((ContextCompat.getColor(applicationContext,R.color.jipdabang_login_edit_gray)))
                empty=true

            } else if(viewbinding.etEmail.getTag().equals("1") && viewbinding.etContent.text.toString().length > 0 ) {
                viewbinding.btQuestion.setBackgroundResource(R.drawable.quest_bg_bt)
                viewbinding.btQuestion.setTextColor((ContextCompat.getColor(applicationContext,R.color.black)))
                empty=false


            }
        }

            viewbinding.agreeBt.setOnClickListener {
                if(!agree){

                    viewbinding.agreeBt.setBackgroundResource(R.drawable.sign_roundbtn_yellow)
                    agree=true
                }
                else{
                    viewbinding.agreeBt.setBackgroundResource(R.drawable.sign_roundbtn_gray)
                    agree=false

                }
            }

            val dialog =  Dialog_question(this)
            viewbinding.btQuestion.setOnClickListener {
                if( agree && !empty){

                    quest = Question_Post(viewbinding.etEmail.text.toString(), viewbinding.etContent.text.toString())
                    Log.d("퀘스트","${quest}")
                    dialog.showDialog()
                    }
                }


            //      var token1  =
        //        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsQG5hdmVyLmNvbSIsImlhdCI6MTY3NDYyNDA5OCwiZXhwIjoxNjc3MjE2MDk4LCJzdWIiOiJ1c2VySW5mbyJ9.ZEl388-pGKg02xaVO5fq3nVGBtn0QfgTiWEeX3laRl0"


            GlobalScope.launch(Dispatchers.IO) {

                var token1: String? = null
                val tokenDb = TokenDatabase.getTokenDatabase(this@QuestionDetailActivity)
                token1 = tokenDb.tokenDao().getToken().token.toString()

                val service = Retrofit.retrofit.create(RetrofitQuestService::class.java)



            dialog.setOnClickListener(object : Dialog_question.ButtonClickListener {
                override fun onClicked() {

                    service.post_question(token1, quest).enqueue(object :
                        Callback<Question_Response> {
                        override fun onResponse(
                            call: Call<Question_Response>,
                            response: Response<Question_Response>
                        ) {
                            val result = response.body()
                            Log.d("성공", "${result}")

                            questionarray.add(
                                question(
                                    result?.data?.questid,
                                    result?.data?.questemail,
                                    result?.data?.questtext
                                )
                            )

                            val intent =
                                Intent(applicationContext, FirstQuestionActivity::class.java)
                                    .setFlags(FLAG_ACTIVITY_CLEAR_TOP)
                                    .addFlags( FLAG_ACTIVITY_NEW_TASK)
                            custom_toast.createToast(applicationContext, "1:1 문의가 접수되었어요")?.show()
                            startActivity(intent)

                        }

                        override fun onFailure(call: Call<Question_Response>, t: Throwable) {
                            Log.d("실패","실패2")
                        }


                        //백엔드에 문의 내용 전달

                    })


                }

            })

        viewbinding.myBackbtn.setOnClickListener {
            finish()


    }
        }
    }
}
}