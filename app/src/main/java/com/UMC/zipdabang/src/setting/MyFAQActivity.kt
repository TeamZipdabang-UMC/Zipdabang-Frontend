package com.UMC.zipdabang.src.setting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.UMC.umc_zipdabang.databinding.ActivityMyFqaBinding
import com.UMC.zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFAQActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMyFqaBinding

    //  var token =
    //    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsQG5hdmVyLmNvbSIsImlhdCI6MTY3NDYyNDA5OCwiZXhwIjoxNjc3MjE2MDk4LCJzdWIiOiJ1c2VySW5mbyJ9.ZEl388-pGKg02xaVO5fq3nVGBtn0QfgTiWEeX3laRl0"
    val service = Retrofit.retrofit.create(RetrofitFAQService::class.java)
    var faqlist: ArrayList<FAQ> = arrayListOf()
    var adapter: FaqAdatper? = null
    var token1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyFqaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        GlobalScope.launch(Dispatchers.IO) {


            val tokenDb = TokenDatabase.getTokenDatabase(this@MyFAQActivity)
            token1 = tokenDb.tokenDao().getToken().token.toString()

            service.get_faq_list(token1).enqueue(object :
                Callback<FAQ_Response> {
                override fun onResponse(
                    call: Call<FAQ_Response>,
                    response: Response<FAQ_Response>
                ) {
                    val result = response.body()
                    var i = 0
                    while (true) {

                        if (result?.data == null) {
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

}

