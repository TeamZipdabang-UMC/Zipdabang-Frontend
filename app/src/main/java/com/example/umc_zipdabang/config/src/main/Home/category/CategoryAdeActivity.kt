package com.example.umc_zipdabang.config.src.main.Home.category

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.config.src.main.Home.Scrap.My_Scrap
import com.example.umc_zipdabang.config.src.main.Home.Scrap.My_Scrapp
import com.example.umc_zipdabang.config.src.main.Retrofit.DTO_Scroll_Response
import com.example.umc_zipdabang.config.src.main.Retrofit.DTO_Scroll_Response2
import com.example.umc_zipdabang.config.src.main.Retrofit.RetrofitMainService
import com.example.umc_zipdabang.databinding.ActivityCategory1Binding
import com.example.umc_zipdabang.src.main.roomDb.TokenDatabase
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Runnable

class CategoryAdeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategory1Binding
    var token1: String? = null
    val service = com.example.umc_zipdabang.config.src.main.Retrofit.Retrofit.retrofit.create(
        RetrofitMainService::class.java
    )

    var a=1
        private var isLoading = false
        private var scraps: ArrayList<My_Scrapp> = arrayListOf()
        private lateinit var adapter: CategoryAdeAdapter
        val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
        private lateinit var layoutManager: GridLayoutManager


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            CoroutineScope(mainDispatcher).launch {
                delay(2000)
                setData()
            }
                initAdapter()
                initScrollListener()

            binding.tvCategory.text="에이드"


            binding.myscrapIvBack.setOnClickListener{

                onBackPressed()

            }

        }

        private fun setData() {



                val tokenDb = TokenDatabase.getTokenDatabase(this@CategoryAdeActivity)
                //   token1 = tokenDb.tokenDao().getToken().token.toString()

                token1 =
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsQG5hdmVyLmNvbSIsImlhdCI6MTY3NDYyNDA5OCwiZXhwIjoxNjc3MjE2MDk4LCJzdWIiOiJ1c2VySW5mbyJ9.ZEl388-pGKg02xaVO5fq3nVGBtn0QfgTiWEeX3laRl0"

                var categoryId= 4
                var is_Main=1
                var is_Official=0

                service.get_Category(categoryId,is_Main,is_Official,token1)?.enqueue(object :
                    Callback<DTO_Scroll_Response> {

                    override fun onResponse(
                        call: Call<DTO_Scroll_Response>,
                        response: Response<DTO_Scroll_Response>

                    ) {
                        // 정상적으로 통신이 성공된 경우
                        val result = response.body()
                        Log.d("마지막","${result}")
                        for(i : Int in 0 .. 11){

                            scraps.add(My_Scrapp(result?.data?.get(i)?.recipeid, result?.data?.get(i)?.likes, result?.data?.get(i)?.image
                            ,result?.data?.get(i)?.name))

                        }


                    }


                    override fun onFailure(
                        call: Call<DTO_Scroll_Response>,
                        t: Throwable
                    ) {
                    }
                })

            }







        private fun initAdapter() {
            binding = ActivityCategory1Binding.inflate(layoutInflater)
            setContentView(binding.root)
            adapter = CategoryAdeAdapter(this, scraps)
            layoutManager = GridLayoutManager(this, 2)
            binding.categoryRv.setLayoutManager(layoutManager)
            binding.categoryRv.setAdapter(adapter)

            layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {

                    if (position == 0)
                    {

                        return 1


                    }
                    else if ((position % 12 == 0) && position == (scraps.size-1))
                    {

                        return 2
                    }
                    else
                    {

                        return 1
                    }

                }
            })
        }

        private fun initScrollListener() {

            binding.categoryRv.setOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!isLoading) {
                        if (binding.categoryRv.layoutManager != null && (binding.categoryRv.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (scraps.size - 1)) {
                            //리스트 마지막o
                            Log.d("ssssaa","$a")
                            if(a==1) moreItems()
                            isLoading = true

                        }
                    }
                }
            })
        }


        fun moreItems() {
            val runnable = Runnable {
                Log.d("ssssnull","??")

                scraps.add(My_Scrapp(null, null, null,null))

                adapter.notifyItemInserted(scraps.size - 1)


            }
            binding.categoryRv.post(runnable)

            CoroutineScope(mainDispatcher).launch {
                delay(2000)
                val runnable2 = Runnable {
                    Log.d("ssss스크랩_bofore drop","${scraps}")
                    scraps.removeAt(scraps.size - 1)
                    val scrollToPosition = scraps.size
                    Log.d("ssss스크랩_after drop","${scraps}")
                    adapter.notifyItemRemoved(scrollToPosition)

                    var categoryId= 4
                    var is_Main=1
                    var is_Official=0
                    Log.d("ssss전송 id","${scraps[scraps.size-1].id}")
                    service.get_Scroll_Category(categoryId,scraps[scraps.size-1].id,is_Main,is_Official,token1)?.enqueue(object :
                        Callback<DTO_Scroll_Response2> {

                        override fun onResponse(
                            call: Call<DTO_Scroll_Response2>,
                            response: Response<DTO_Scroll_Response2>

                        ) {
                            // 정상적으로 통신이 성공된 경우
                            val result = response.body()
                            if(result?.data?.size!! < 12) a=0
                            Log.d("ssssa","${a}")

                            Log.d("ssssscrapsremovebefore","${scraps}")
                            if(result!=null)scraps.removeAt(scraps.size-1)
                            val size= result?.data?.size?.minus(1)
                            //Log.d("ssssid","${scraps[scraps.size-1].id}")
                            Log.d("ssss_받은_result","${result}")
                            Log.d("ssssresultremoveaft","${scraps}")


                                 for (i: Int in 0..size!!) {
                                     scraps.add(
                                         My_Scrapp(
                                             result?.data?.get(i)?.recipeid,
                                             result?.data?.get(i)?.likes,
                                             result?.data?.get(i)?.image,
                                             result?.data?.get(i)?.name
                                         )
                                     )

                                 }




                            Log.d("ssssresultaft2","${scraps}")
                            Log.d("ssss######","----------------")



                        }


                        override fun onFailure(
                            call: Call<DTO_Scroll_Response2>,
                            t: Throwable
                        ) {
                        }
                    })








                }
               if(a==1) runnable2.run()
                adapter.notifyDataSetChanged()
                isLoading = false

            }
            }

        }

