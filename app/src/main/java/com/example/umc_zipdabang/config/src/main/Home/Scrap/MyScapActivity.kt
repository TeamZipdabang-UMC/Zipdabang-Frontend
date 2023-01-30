package com.example.umc_zipdabang.config.src.main.Home.Scrap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.config.src.main.Home.edit.EditScrapActivity
import com.example.umc_zipdabang.config.src.main.Retrofit.Retrofit
import com.example.umc_zipdabang.config.src.main.Retrofit.RetrofitMainService
import com.example.umc_zipdabang.config.src.main.Retrofit.Scrap_Response
import com.example.umc_zipdabang.config.src.main.Retrofit.Search_Response
import com.example.umc_zipdabang.databinding.ActivityMyscrapBinding
import com.example.umc_zipdabang.src.main.roomDb.TokenDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyScapActivity : AppCompatActivity() {

    private var scraps: ArrayList<My_Scrapp> = arrayListOf()

    val service = Retrofit.retrofit.create(RetrofitMainService::class.java)
    var token1: String? = null

    private lateinit var binding: ActivityMyscrapBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyscrapBinding.inflate(layoutInflater)


        GlobalScope.launch(Dispatchers.IO) {


            val tokenDb = TokenDatabase.getTokenDatabase(this@MyScapActivity)
     //       token1 = tokenDb.tokenDao().getToken().token.toString()
            token1="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsQG5hdmVyLmNvbSIsImlhdCI6MTY3NDYyNDA5OCwiZXhwIjoxNjc3MjE2MDk4LCJzdWIiOiJ1c2VySW5mbyJ9.ZEl388-pGKg02xaVO5fq3nVGBtn0QfgTiWEeX3laRl0"


            //통신
            service.get_Scrap_List(token1).enqueue(object :
                Callback<Scrap_Response> {

                override fun onResponse(
                    call: Call<Scrap_Response>,
                    response: Response<Scrap_Response>

                ) {
                    // 정상적으로 통신이 성공된 경우
                    val result = response.body()
                    var i = 0

                    Log.d("통신","${result}")

                    while (true) {
                        if (scraps.size == result?.data?.myScrap?.size) break
                        scraps.add(
                            My_Scrapp(
                                result?.data?.myScrap?.get(i)?.recipeid,
                                result?.data?.myScrap?.get(i)?.likes,
                                result?.data?.myScrap?.get(i)?.image,
                                result?.data?.myScrap?.get(i)?.name
                            )
                        )
                        i++
                    }


                    binding.myscrapRv.layoutManager =
                        GridLayoutManager(applicationContext, 2)
                    val adapter = MyScrapAdapter(this@MyScapActivity, scraps)

                    binding.myscrapRv.adapter = adapter
                    binding.scrapSize.text=scraps.size.toString()
                    adapter.notifyDataSetChanged()


                }

                override fun onFailure(call: Call<Scrap_Response>, t: Throwable) {
                }

            })


        }



        binding.myscrapIvBack.setOnClickListener {
            onBackPressed()
        }


        binding.srapTvEdit.setOnClickListener {

            val intent = Intent(applicationContext, EditScrapActivity::class.java)
            intent.putExtra("array", scraps)
            startActivity(intent)


        }



        setContentView(binding.root)
    }
}