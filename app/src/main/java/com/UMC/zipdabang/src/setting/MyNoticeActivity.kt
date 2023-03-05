package com.UMC.zipdabang.src.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.UMC.umc_zipdabang.databinding.ActivityMyNoticeBinding
import com.UMC.zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyNoticeActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMyNoticeBinding
    var token1: String? = null

    var noticelist: ArrayList<Notice> = arrayListOf()
  //  var token  =
    //    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsQG5hdmVyLmNvbSIsImlhdCI6MTY3NDYyNDA5OCwiZXhwIjoxNjc3MjE2MDk4LCJzdWIiOiJ1c2VySW5mbyJ9.ZEl388-pGKg02xaVO5fq3nVGBtn0QfgTiWEeX3laRl0"


    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyNoticeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        GlobalScope.launch(Dispatchers.IO) {


            val tokenDb = TokenDatabase.getTokenDatabase(this@MyNoticeActivity)
            token1 = tokenDb.tokenDao().getToken().token.toString()


            val service = Retrofit.retrofit.create(RetrofitNoticeService::class.java)

            service.get_notice_list(token1).enqueue(object :
                Callback<Notice_Response> {
                override fun onResponse(
                    call: Call<Notice_Response>,
                    response: Response<Notice_Response>
                ) {
                    val result = response.body()
                    var i = 0
                    Log.d("성공", "${result}")
                    while (true) {

                        if (result?.data == null) {
                            break
                        }

                        if (result.data?.size == noticelist.size) {
                            break
                        }


                        var splitarray: List<String>? = result?.data?.get(i)?.created_at?.split("T")
                        var dates = splitarray?.get(0).toString()
                        var splitarray2 = dates.split("-")

                        Log.d("성공", "${splitarray}")


                        var date =
                            (splitarray2?.get(0) + "년" + splitarray2?.get(1) + "월" + splitarray2?.get(
                                2
                            ) + "일")
                        noticelist.add(
                            Notice(
                                result?.data?.get(i)?.noticeid,
                                result?.data?.get(i)?.noticetitle,
                                date
                            )
                        )

                        i++
                        Log.d("끝", i.toString())

                    }

                    if (noticelist.size != 0) {
                        viewBinding.noticeRv.layoutManager =
                            LinearLayoutManager(
                                applicationContext,
                                LinearLayoutManager.VERTICAL,
                                false
                            )

                        var adapter = NoticeAdapter(this@MyNoticeActivity, noticelist)

                        viewBinding.noticeRv.adapter = adapter

                        adapter.setOnItemClickListener(object : NoticeAdapter.OnItemClickListener {

                            override fun onItemClick(v: View?, pos: Int) {
                                Log.d("누름", "${noticelist[pos].id}")
                                //api 통신 필요  id배열 보내주면 intent로 id 전달/ detailactivity에서 id 받으면 백엔드로 id 보내줘서 상세 정보 받아와야함.
                                service.getNoticeInfo(noticelist[pos].id, token1).enqueue(object :
                                    Callback<Notice_Detail_Response> {
                                    override fun onResponse(
                                        call: Call<Notice_Detail_Response>,
                                        response: Response<Notice_Detail_Response>
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
                                        var title = result1?.data?.get(0)?.title
                                        var body = result1?.data?.get(0)?.body
                                        val intent =
                                            Intent(
                                                applicationContext,
                                                NoticeDetailActivity::class.java
                                            )
                                        intent.putExtra("타이틀", title)
                                        intent.putExtra("날짜", date)
                                        intent.putExtra("바디", body)
                                        startActivity(intent)


                                    }

                                    override fun onFailure(
                                        call: Call<Notice_Detail_Response>,
                                        t: Throwable
                                    ) {
                                    }
                                })


                            }


                        })

                    }
                }

                override fun onFailure(call: Call<Notice_Response>, t: Throwable) {
                }
            })




            viewBinding.myBackbtn.setOnClickListener {
                finish()

            }
        }
    }

    }




