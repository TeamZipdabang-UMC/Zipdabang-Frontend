package com.example.umc_zipdabang.src.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_zipdabang.databinding.ActivityMyFqaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFAQActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMyFqaBinding
    var token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsQG5hdmVyLmNvbSIsImlhdCI6MTY3NDYyNDA5OCwiZXhwIjoxNjc3MjE2MDk4LCJzdWIiOiJ1c2VySW5mbyJ9.ZEl388-pGKg02xaVO5fq3nVGBtn0QfgTiWEeX3laRl0"
    val service = Retrofit.retrofit.create(RetrofitFAQService::class.java)
    var faqlist: ArrayList<FAQ> = arrayListOf()
    var adapter: FaqAdatper  ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyFqaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        service.get_faq_list(token).enqueue(object :
            Callback<FAQ_Response> {
            override fun onResponse(
                call: Call<FAQ_Response>,
                response: Response<FAQ_Response>
            ) {
                val result = response.body()
                var i = 0
                while (true) {

                    if (result?.data==null) {
                        break
                    }

                    if (faqlist.size == result?.data?.size) break

                    faqlist.add(
                        FAQ(
                            result?.data?.get(i)?.FAQid,
                            result?.data?.get(i)?.FAQquestion,
                            result?.data?.get(i)?.FAQanswer
                        )
                    )

                    i++
                }


                viewBinding.faqRv.layoutManager = LinearLayoutManager(
                    this@MyFAQActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                 adapter = FaqAdatper(this@MyFAQActivity, faqlist)
                viewBinding.faqRv.adapter = adapter


            }

            override fun onFailure(call: Call<FAQ_Response>, t: Throwable) {

            }


        })


        adapter?.setOnItemClickListener(object : FaqAdatper.OnItemClickListener {
            override fun onItemClick(v: View?, pos: Int) {


            }

        })


        viewBinding.myBackbtn.setOnClickListener {
            finish()
        }
    }
}


