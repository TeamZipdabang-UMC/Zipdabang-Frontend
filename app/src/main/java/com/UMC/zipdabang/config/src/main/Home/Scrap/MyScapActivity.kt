package com.UMC.zipdabang.config.src.main.Home.Scrap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.UMC.umc_zipdabang.databinding.ActivityMyscrapBinding
import com.UMC.zipdabang.config.src.main.Home.edit.EditScrapActivity
import com.UMC.zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailActivity
import com.UMC.zipdabang.config.src.main.Retrofit.Retrofit
import com.UMC.zipdabang.config.src.main.Retrofit.RetrofitMainService
import com.UMC.zipdabang.config.src.main.Retrofit.Scrap_Response
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
            token1 = tokenDb.tokenDao().getToken().token.toString()


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
                    adapter.setOnItemClickListener(object : MyScrapAdapter.OnItemClickListener {
                        override fun onItemClick(v: View?, pos: Int) {
                            var intent= Intent(applicationContext, ZipdabangRecipeDetailActivity :: class.java )
                            intent.putExtra("recipeId",scraps[pos].id.toString())
                            startActivity(intent)
                        }


                    })


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