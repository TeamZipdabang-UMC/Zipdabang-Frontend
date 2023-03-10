package com.example.umc_zipdabang.config.src.main.Home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_zipdabang.config.src.main.Home.Scrap.MainScrapAdapter
import com.example.umc_zipdabang.config.src.main.Home.Scrap.Main_Scrap
import com.example.umc_zipdabang.config.src.main.Home.Scrap.MyScapActivity
import com.example.umc_zipdabang.config.src.main.Home.category.*
import com.example.umc_zipdabang.config.src.main.Home.reciepe.Home_receipe
import com.example.umc_zipdabang.config.src.main.Home.reciepe.ReceipeAdapter
import com.example.umc_zipdabang.config.src.main.Home.search.SearchActivity
import com.example.umc_zipdabang.config.src.main.Retrofit.Main_Response
import com.example.umc_zipdabang.config.src.main.Retrofit.Retrofit
import com.example.umc_zipdabang.config.src.main.Retrofit.RetrofitMainService
import com.example.umc_zipdabang.databinding.FragmentHomeBinding
import com.example.umc_zipdabang.src.main.JoinInitialActivity
import com.example.umc_zipdabang.src.main.roomDb.Token
import com.example.umc_zipdabang.src.main.roomDb.TokenDatabase
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {


    val service = Retrofit.retrofit.create(RetrofitMainService::class.java)

    private  var scraps: ArrayList<Main_Scrap> = arrayListOf()
    private var coffee: ArrayList<Main_Scrap> = arrayListOf()
    private var beverage: ArrayList<Main_Scrap> = arrayListOf()
    private var tea: ArrayList<Main_Scrap> = arrayListOf()
    private var ade: ArrayList<Main_Scrap> = arrayListOf()
    private var smootie_juice: ArrayList<Main_Scrap> = arrayListOf()
    private var health: ArrayList<Main_Scrap> = arrayListOf()
    private lateinit var adapter2: ReceipeAdapter

    private var category: ArrayList<Home_receipe> = arrayListOf()


//    private var  scraps : ArrayList<Main_Scrap> = arrayListOf(
//        Main_Scrap("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg","???????????? ?????????????????? ????????? ???????????? ?????????",1),
//        Main_Scrap("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg","???????????? ?????????????????? ????????? ???????????? ?????????",100000)
    //   )

    /*   private var coffee : ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","???????????? ?????????????????? ????????? ???????????? ?????????",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","????????? ?????????",12)
    )

    private var beverage: ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","????????? ?????????",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","????????? ?????????",12)
    )

    private var tea : ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","????????? ?????????",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","????????? ?????????",12)
    )

    private var ade : ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","????????? ?????? ?????? ?????? ???????????? ?????????",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","????????? ?????????",12)
    )

    private var smootie_juice : ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","????????? ?????????",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","????????? ?????????",12)
    )

    private var health : ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","??????????????? ???????????????",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","????????? ?????????",12)
    ) */


    private val scraplist: ArrayList<ArrayList<Main_Scrap>> = arrayListOf()

    private var token1: String? = null


    private lateinit var viewBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater)


        GlobalScope.launch(Dispatchers.IO) {
            viewBinding.etSearch.setText("")
            val tokenDb = TokenDatabase.getTokenDatabase(activity as HomeMainActivity)
            token1 = tokenDb.tokenDao().getToken().token.toString()
            Log.d("??????", token1!!)

            //??????
            service.get_Main(token1).enqueue(object :
                Callback<Main_Response> {


                override fun onResponse(
                    call: Call<Main_Response>,
                    response: Response<Main_Response>

                ) {
                    Log.d("??????", "SUCCESS")
                        // ??????????????? ????????? ????????? ??????
                        val result = response.body()
                        Log.d("??????", "${result}")
                        if (result != null) {
            /*                result.data?.myScrapOverView?.get(0)?.recipeid?.let {
                                result.data?.myScrapOverView?.get(0)?.likes?.let { it1 ->
                                    result.data?.myScrapOverView?.get(0)?.image?.let { it2 ->
                                        result.data?.myScrapOverView?.get(0)?.name?.let { it3 ->
                                            Main_Scrap(
                                                it, it1,
                                                it2, it3
                                            )
                                        }
                                    }
                                }
                            }?.let {
                                scraps.add(
                                    it,
                                )
                            }
                            result.data?.myScrapOverView?.get(1)?.recipeid?.let {
                                result.data?.myScrapOverView?.get(1)?.likes?.let { it1 ->
                                    result.data?.myScrapOverView?.get(1)?.image?.let { it2 ->
                                        result.data?.myScrapOverView?.get(1)?.name?.let { it3 ->
                                            Main_Scrap(
                                                it, it1,
                                                it2, it3
                                            )
                                        }
                                    }
                                }
                            }?.let {
                                scraps.add(
                                    it,
                                )
                            }

*/


                            coffee.add(Main_Scrap(result.data?.coffeeCategoryOverView?.get(0)?.recipeid,
                                result.data?.coffeeCategoryOverView?.get(0)?.likes,
                            result.data?.coffeeCategoryOverView?.get(0)?.image,
                            result.data?.coffeeCategoryOverView?.get(0)?.name)
                            )
                            Log.d("????????????",coffee.size.toString())

                            coffee.add(Main_Scrap(result.data?.coffeeCategoryOverView?.get(1)?.recipeid,
                                result.data?.coffeeCategoryOverView?.get(1)?.likes,
                                result.data?.coffeeCategoryOverView?.get(1)?.image,
                                result.data?.coffeeCategoryOverView?.get(1)?.name)
                            )
                            Log.d("????????????",coffee.size.toString())
                            beverage.add(Main_Scrap(result.data?.beverageCategoryOverView?.get(0)?.recipeid,
                                result.data?.beverageCategoryOverView?.get(0)?.likes,
                                result.data?.beverageCategoryOverView?.get(0)?.image,
                                result.data?.beverageCategoryOverView?.get(0)?.name)
                            )
                            beverage.add(Main_Scrap(result.data?.beverageCategoryOverView?.get(0)?.recipeid,
                                result.data?.beverageCategoryOverView?.get(1)?.likes,
                                result.data?.beverageCategoryOverView?.get(1)?.image,
                                result.data?.beverageCategoryOverView?.get(1)?.name)
                            )
                            tea.add(Main_Scrap(result.data?.teaCategoryOverView?.get(0)?.recipeid,
                                result.data?.teaCategoryOverView?.get(0)?.likes,
                                result.data?.teaCategoryOverView?.get(0)?.image,
                                result.data?.teaCategoryOverView?.get(0)?.name)
                            )
                            tea.add(Main_Scrap(result.data?.teaCategoryOverView?.get(1)?.recipeid,
                                result.data?.teaCategoryOverView?.get(1)?.likes,
                                result.data?.teaCategoryOverView?.get(1)?.image,
                                result.data?.teaCategoryOverView?.get(1)?.name)
                            )
                            ade.add(Main_Scrap(result.data?.adeCategoryOverView?.get(0)?.recipeid,
                                result.data?.adeCategoryOverView?.get(0)?.likes,
                                result.data?.adeCategoryOverView?.get(0)?.image,
                                result.data?.adeCategoryOverView?.get(0)?.name)
                            )
                            ade.add(Main_Scrap(result.data?.adeCategoryOverView?.get(1)?.recipeid,
                                result.data?.adeCategoryOverView?.get(1)?.likes,
                                result.data?.adeCategoryOverView?.get(1)?.image,
                                result.data?.adeCategoryOverView?.get(1)?.name)
                            )
                            smootie_juice.add(Main_Scrap(result.data?.smoothieCategoryOverView?.get(0)?.recipeid,
                                result.data?.smoothieCategoryOverView?.get(0)?.likes,
                                result.data?.smoothieCategoryOverView?.get(0)?.image,
                                result.data?.smoothieCategoryOverView?.get(0)?.name)
                            )
                            smootie_juice.add(Main_Scrap(result.data?.smoothieCategoryOverView?.get(1)?.recipeid,
                                result.data?.smoothieCategoryOverView?.get(1)?.likes,
                                result.data?.smoothieCategoryOverView?.get(1)?.image,
                                result.data?.smoothieCategoryOverView?.get(1)?.name)
                            )
                            health.add(Main_Scrap(result.data?.healthCategoryOverView?.get(0)?.recipeid,
                                result.data?.healthCategoryOverView?.get(0)?.likes,
                                result.data?.healthCategoryOverView?.get(0)?.image,
                                result.data?.healthCategoryOverView?.get(0)?.name)
                            )
                            health.add(Main_Scrap(result.data?.healthCategoryOverView?.get(1)?.recipeid,
                                result.data?.healthCategoryOverView?.get(1)?.likes,
                                result.data?.healthCategoryOverView?.get(1)?.image,
                                result.data?.healthCategoryOverView?.get(1)?.name)
                            )



                            scraplist.add(scraps)
                            category.add(Home_receipe("??????", coffee))
                            category.add(Home_receipe("beverage", beverage))
                            category.add(Home_receipe("???", tea))
                            category.add(Home_receipe("?????????", ade))
                            category.add(Home_receipe("?????????/??????", smootie_juice))
                            category.add(Home_receipe("????????????", health))
                        }
                        //??????????????? ??????
                        viewBinding.homeRvMyScrap.layoutManager = LinearLayoutManager(
                            activity as HomeMainActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        val adapter1 = MainScrapAdapter(activity as HomeMainActivity, scraplist)
                        viewBinding.homeRvMyScrap.adapter = adapter1
                        Log.d("?????????", scraps.size.toString())

                        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

                        viewBinding.btTomyscrap.setOnClickListener {
                            val intent = Intent(activity, MyScapActivity::class.java)
                            startActivity(intent)
                        }

                        //??????????????? ??????
                        viewBinding.homeRvReceipe.layoutManager = LinearLayoutManager(
                            activity as HomeMainActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )

                        adapter2 = ReceipeAdapter(activity as HomeMainActivity, category)
                        viewBinding.homeRvReceipe.adapter = adapter2
                        adapter2.notifyDataSetChanged()
                        viewBinding.homeRvReceipe.isNestedScrollingEnabled = false
                        adapter2.setOnItemClickListener(object : ReceipeAdapter.OnItemClickListener {
                            override fun onItemClick(v: View?, pos: Int) {

                                when (pos) {

                                    0 -> {
                                        val intent = Intent(context, CategoryCoffeeActivity::class.java)
                                        startActivity(intent)
                                    }
                                    1 -> {
                                        val intent = Intent(context, CategoryBeverageActivity::class.java)
                                        startActivity(intent)
                                    }
                                    2 -> {
                                        val intent = Intent(context, CategoryTeaActivity::class.java)
                                        startActivity(intent)
                                    }
                                    3 -> {
                                        val intent = Intent(context, CategoryAdeActivity::class.java)
                                        startActivity(intent)
                                    }
                                    4 -> {
                                        val intent = Intent(context, CategorySmoothieActivity::class.java)
                                        startActivity(intent)
                                    }
                                    5 -> {
                                        val intent = Intent(context, CategoryHealthActivity::class.java)
                                        startActivity(intent)
                                    }


                                }


                            }

                        })
                    }



                override fun onFailure(call: Call<Main_Response>, t: Throwable) {
                    // ?????? ?????? (????????? ??????, ?????? ?????? ??? ??????????????? ??????)
                    Log.d("error", "onFailure ??????: " + t.message.toString());
                }


            })
        }




            //??????(????????? ??????)
            viewBinding.etSearch.setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(view: View?, i: Int, keyEvent: KeyEvent?): Boolean {
                    when (i) {
                        KeyEvent.KEYCODE_ENTER -> {
                            //?????? ??????????????? ?????? ?????????
                            val search = Intent(context, SearchActivity::class.java)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            var string= viewBinding.etSearch.text.toString()
                            search.putExtra("key_search", string)
                            startActivity(search)
                            GlobalScope.launch {
                                delay(20)
                                viewBinding.etSearch.setText("")
                            }


                        }
                    }
                    return false
                }
            })


            //??????(?????? ????????? ??????)
            viewBinding.ivSearch.setOnClickListener {

                val intent = Intent(context, SearchActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                var string=viewBinding.etSearch.text.toString()
                intent.putExtra("search", string)
                startActivity(intent)
                viewBinding.etSearch.setText("")
            }



            //??????????????? ?????? ??????


            return viewBinding.root
        }

}
