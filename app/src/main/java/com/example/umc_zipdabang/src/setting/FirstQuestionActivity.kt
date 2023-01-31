package com.example.umc_zipdabang.src.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.example.umc_zipdabang.databinding.ActivityMyNoticeDetailBinding
import com.example.umc_zipdabang.databinding.ActivityMyQuestonBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstQuestionActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityMyQuestonBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val questionarray: ArrayList<question> = arrayListOf()


        viewBinding = ActivityMyQuestonBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.btQuestion.setOnClickListener {
            val intent = Intent(applicationContext, QuestionDetailActivity::class.java)
            startActivity(intent)

        }

        // var token =
        //   "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsQG5hdmVyLmNvbSIsImlhdCI6MTY3NDYyNDA5OCwiZXhwIjoxNjc3MjE2MDk4LCJzdWIiOiJ1c2VySW5mbyJ9.ZEl388-pGKg02xaVO5fq3nVGBtn0QfgTiWEeX3laRl0"
        val service = Retrofit.retrofit.create(RetrofitQuestService::class.java)
        var token1: String? = null

        val questlist: ArrayList<question> = arrayListOf()

        GlobalScope.launch(Dispatchers.IO) {


            val tokenDb = TokenDatabase.getTokenDatabase(this@FirstQuestionActivity)
            token1 = tokenDb.tokenDao().getToken().token.toString()



            service.get_quest_list(token1).enqueue(object :
                Callback<Questionlist_Response> {
                override fun onResponse(
                    call: Call<Questionlist_Response>,
                    response: Response<Questionlist_Response>
                ) {
                    val result = response.body()
                    Log.d("성공", "${result}")
                    var i = 0
                    while (true) {

                        if (result?.data == null) {
                            break
                        }

                        if (result.data?.size == questlist.size) {
                            break
                        }

                        var splitarray: List<String>? =
                            result?.data?.get(i)?.created_at?.split("T")
                        var dates = splitarray?.get(0).toString()
                        var splitarray2 = dates.split("-")
                        var date =
                            (splitarray2?.get(0) + "년" + splitarray2?.get(1) + "월" + splitarray2?.get(
                                2
                            ) + "일")
                        questlist.add(
                            question(
                                result?.data?.get(i)?.id,
                                result?.data?.get(i)?.body,
                                date
                            )
                        )

                        i++
                        Log.d("끝", i.toString())
                    }

                    if (questlist.size != 0) {
                        val layoutManager = LinearLayoutManager(
                            this@FirstQuestionActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        viewBinding.questRv.layoutManager = layoutManager

                        val adapter = QuestionAdapter(this@FirstQuestionActivity, questlist)
                        viewBinding.questRv.adapter = adapter





                        adapter.setOnItemClickListener(object :
                            QuestionAdapter.OnItemClickListener {
                            override fun onItemClick(v: View?, pos: Int) {

                                service.getQuestionInfo(questlist[pos].id, token1).enqueue(object :
                                    Callback<QuestionDetail_Response> {
                                    override fun onResponse(
                                        call: Call<QuestionDetail_Response>,
                                        response: Response<QuestionDetail_Response>
                                    ) {
                                        Log.d("성공", "왜")
                                        var result1 = response.body()
                                        Log.d("성공", "${result1}")
                                        var splitarray: List<String>? =
                                            result1?.data?.get(0)?.created_at?.split("T")
                                        var dates = splitarray?.get(0).toString()
                                        var splitarray2 = dates.split("-")
                                        Log.d("성공", "${splitarray}")


                                        var date =
                                            (splitarray2?.get(0) + "년 " + splitarray2?.get(1) + "월 " + splitarray2?.get(
                                                2
                                            ) + "일")
                                        var email = result1?.data?.get(0)?.contact_email
                                        var body = result1?.data?.get(0)?.body
                                        val intent =
                                            Intent(
                                                applicationContext,
                                                QuestionLookupActivity::class.java
                                            )
                                        intent.putExtra("이메일", email)
                                        intent.putExtra("날짜", date)
                                        intent.putExtra("바디", body)
                                        startActivity(intent)


                                    }

                                    override fun onFailure(
                                        call: Call<QuestionDetail_Response>,
                                        t: Throwable
                                    ) {

                                    }
                                })

                            }


                        })
                    }

                }

                override fun onFailure(call: Call<Questionlist_Response>, t: Throwable) {
                    Log.d("실패", "실패2")
                }


                //id넘겨주기.


            })


            viewBinding.myBackbtn.setOnClickListener {
                finish()
            }

        }

    }
}



