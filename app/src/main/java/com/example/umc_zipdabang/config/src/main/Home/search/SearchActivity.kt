package com.example.umc_zipdabang.config.src.main.Home.search

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_zipdabang.config.src.main.Home.HomeMainActivity
import com.example.umc_zipdabang.config.src.main.Home.Scrap.Main_Scrap
import com.example.umc_zipdabang.config.src.main.Home.Scrap.Search
import com.example.umc_zipdabang.config.src.main.Home.reciepe.Home_receipe
import com.example.umc_zipdabang.config.src.main.Retrofit.Main_Response
import com.example.umc_zipdabang.config.src.main.Retrofit.Retrofit
import com.example.umc_zipdabang.config.src.main.Retrofit.RetrofitMainService
import com.example.umc_zipdabang.config.src.main.Retrofit.Search_Response
import com.example.umc_zipdabang.databinding.ActivitySearchBinding
import com.example.umc_zipdabang.src.main.roomDb.TokenDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity: AppCompatActivity() {

    private var token1: String? = null
    val service = Retrofit.retrofit.create(RetrofitMainService::class.java)


    private lateinit var viewbinding: ActivitySearchBinding
    private var coffee: ArrayList<Search> = arrayListOf()
    private var beverage: ArrayList<Search> = arrayListOf()
    private var tea: ArrayList<Search> = arrayListOf()
    private var ade: ArrayList<Search> = arrayListOf()
    private var smoothie: ArrayList<Search> = arrayListOf()

    private var health: ArrayList<Search> = arrayListOf()


    private val category: ArrayList<Search_Receipe> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {


        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        super.onCreate(savedInstanceState)

        viewbinding = ActivitySearchBinding.inflate(layoutInflater)

        GlobalScope.launch(Dispatchers.IO) {

            var string : String?= null


            val renew = intent.getStringExtra("renew")

            if (renew!= null) {

                string = intent.getStringExtra("renew").toString()


            }else{
               string = intent.getStringExtra("search").toString()
            }

            viewbinding.etSearch.setText(string)

            val tokenDb = TokenDatabase.getTokenDatabase(this@SearchActivity)
            token1 = tokenDb.tokenDao().getToken().token.toString()

            //통신
            service.get_Search(string, token1).enqueue(object :
                Callback<Search_Response> {

                override fun onResponse(
                    call: Call<Search_Response>,
                    response: Response<Search_Response>

                ) {

                    // 정상적으로 통신이 성공된 경우
                    val result = response.body()
                    var i = 0
                    while (true) {
                        if (coffee.size == result?.data?.coffeeSearch?.size) break

                        coffee.add(
                            Search(
                                result?.data?.coffeeSearch?.get(i)?.id,
                                result?.data?.coffeeSearch?.get(i)?.image_url,
                                result?.data?.coffeeSearch?.get(i)?.name,
                                result?.data?.coffeeSearch?.get(i)?.likes
                            )
                        )

                        i++
                    }

                    if (coffee.size != 0) category.add(Search_Receipe("커피", coffee))
                    i = 0

                    while (true) {
                        if (beverage.size == result?.data?.beverageSearch?.size) break
                        beverage.add(
                            Search(
                                result?.data?.beverageSearch?.get(i)?.id,
                                result?.data?.beverageSearch?.get(i)?.image_url,
                                result?.data?.beverageSearch?.get(i)?.name,
                                result?.data?.beverageSearch?.get(i)?.likes
                            )
                        )

                        i++
                    }
                    if (beverage.size != 0) category.add(Search_Receipe("beverage", beverage))

                    i = 0

                    while (true) {
                        if (tea.size == result?.data?.teaSearch?.size) break
                        tea.add(
                            Search(
                                result?.data?.teaSearch?.get(i)?.id,
                                result?.data?.teaSearch?.get(i)?.image_url,
                                result?.data?.teaSearch?.get(i)?.name,
                                result?.data?.teaSearch?.get(i)?.likes
                            )
                        )

                        i++
                    }
                    if (tea.size != 0) category.add(Search_Receipe("티", tea))

                    i = 0
                    while (true) {

                        if (ade.size == result?.data?.adeSearch?.size) break
                        ade.add(
                            Search(
                                result?.data?.adeSearch?.get(i)?.id,
                                result?.data?.adeSearch?.get(i)?.image_url,
                                result?.data?.adeSearch?.get(i)?.name,
                                result?.data?.adeSearch?.get(i)?.likes
                            )
                        )


                        i++
                    }
                    if (ade.size != 0) category.add(Search_Receipe("에이드", ade))

                    i = 0
                    Log.d("사이즈",coffee.size.toString())

                    while (true) {
                        if (smoothie.size == result?.data?.smoothieSearch?.size) break
                        smoothie.add(
                            Search(
                                result?.data?.smoothieSearch?.get(i)?.id,
                                result?.data?.smoothieSearch?.get(i)?.image_url,
                                result?.data?.smoothieSearch?.get(i)?.name,
                                result?.data?.smoothieSearch?.get(i)?.likes
                            )
                        )

                        i++
                    }
                    if (smoothie.size != 0) category.add(Search_Receipe("스무디/주스", smoothie))

                    i = 0
                    while (true) {
                        if (
                            health.size == result?.data?.healthSearch?.size) break
                        health.add(
                            Search(
                                result?.data?.healthSearch?.get(i)?.id,
                                result?.data?.healthSearch?.get(i)?.image_url,
                                result?.data?.healthSearch?.get(i)?.name,
                                result?.data?.healthSearch?.get(i)?.likes
                            )
                        )

                        i++
                    }
                    if (health.size != 0) category.add(Search_Receipe("건강음료", health))

                    viewbinding.searchRv.layoutManager = LinearLayoutManager(
                        this@SearchActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )

                    val adapter = SearchAdpater_1(this@SearchActivity, category)
                    viewbinding.searchRv.adapter = adapter

                    //처음 검색

                }


                override fun onFailure(call: Call<Search_Response>, t: Throwable) {

                    Log.d("error", "onFailure 에러: " + t.message.toString());

                }

            })
        }


            viewbinding.etSearch.setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(view: View?, i: Int, keyEvent: KeyEvent?): Boolean {
                    when (i) {
                        KeyEvent.KEYCODE_ENTER -> {
                            val search = Intent(applicationContext, SearchActivity::class.java)
                                .setFlags(FLAG_ACTIVITY_CLEAR_TOP)
                            search.putExtra("renew", viewbinding.etSearch.text.toString())
                            startActivity(search)
                        }
                    }
                    return false
                }
            })




            viewbinding.myscrapIvBack.setOnClickListener {
                onBackPressed()
            }





            setContentView(viewbinding.root)





    }
}