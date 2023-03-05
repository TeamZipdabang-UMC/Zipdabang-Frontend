package com.UMC.zipdabang.config.src.main.Home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.UMC.zipdabang.config.src.main.Home.Scrap.MainScrapAdapter
import com.UMC.zipdabang.config.src.main.Home.Scrap.Main_Scrap
import com.UMC.zipdabang.config.src.main.Home.Scrap.MyScapActivity
import com.UMC.zipdabang.config.src.main.Home.category.*
import com.UMC.zipdabang.config.src.main.Home.reciepe.Home_receipe
import com.UMC.zipdabang.config.src.main.Home.reciepe.ReceipeAdapter
import com.UMC.zipdabang.config.src.main.Home.search.SearchActivity
import com.UMC.zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailActivity
import com.UMC.zipdabang.config.src.main.Retrofit.Main_Response
import com.UMC.zipdabang.config.src.main.Retrofit.Retrofit
import com.UMC.zipdabang.config.src.main.Retrofit.RetrofitMainService
import com.UMC.zipdabang.databinding.FragmentHomeBinding
import com.UMC.zipdabang.src.setting.MySettingActivity
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    val service = Retrofit.retrofit.create(RetrofitMainService::class.java)

    private var scraps: ArrayList<Main_Scrap> = arrayListOf()
    private var coffee: ArrayList<Main_Scrap> = arrayListOf()
    private var beverage: ArrayList<Main_Scrap> = arrayListOf()
    private var tea: ArrayList<Main_Scrap> = arrayListOf()
    private var ade: ArrayList<Main_Scrap> = arrayListOf()
    private var smootie_juice: ArrayList<Main_Scrap> = arrayListOf()
    private var health: ArrayList<Main_Scrap> = arrayListOf()
    private lateinit var adapter2: ReceipeAdapter
    private var resume : Boolean = false

    private var category: ArrayList<Home_receipe> = arrayListOf()


    private var scraplist: ArrayList<ArrayList<Main_Scrap>> = arrayListOf()

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
            Log.d("토큰", token1!!)



            //통신
            service.get_Main(token1).enqueue(object :
                Callback<Main_Response> {


                override fun onResponse(
                    call: Call<Main_Response>,
                    response: Response<Main_Response>

                ) {
                    Log.d("성공", "SUCCESS")
                    // 정상적으로 통신이 성공된 경우
                    val result = response.body()
                    Log.d("오버뷰성공", "${result}")
                    if (result != null) {


                        if(scraps.size==1) scraps.removeAt(0)
                        else if(scraps.size==2) {
                            scraps.removeAt(0)
                            scraps.removeAt(0)
                        }
                        Log.d("사이즈","${scraps.size}")

                        if(scraplist.size!=0) scraplist.removeAt(0)
                        Log.d("사이즈","${scraplist.size}")


                        if (result?.data?.myScrapOverView?.size != 0) {

                            scraps.add(
                                Main_Scrap(
                                    result.data?.myScrapOverView?.get(0)?.recipeid,
                                    result.data?.myScrapOverView?.get(0)?.likes,
                                    result.data?.myScrapOverView?.get(0)?.image,
                                    result.data?.myScrapOverView?.get(0)?.name
                                )
                            )
                            Log.d("성공성공", scraps.size.toString())

                            if (result?.data?.myScrapOverView?.size != 1) {
                                scraps.add(
                                    Main_Scrap(
                                        result.data?.myScrapOverView?.get(1)?.recipeid,
                                        result.data?.myScrapOverView?.get(1)?.likes,
                                        result.data?.myScrapOverView?.get(1)?.image,
                                        result.data?.myScrapOverView?.get(1)?.name
                                    )
                                )
                            }
                        }
                        if (result?.data?.coffeeCategoryOverView?.size != 0) {
                            coffee.add(
                                Main_Scrap(
                                    result.data?.coffeeCategoryOverView?.get(0)?.recipeid,
                                    result.data?.coffeeCategoryOverView?.get(0)?.likes,
                                    result.data?.coffeeCategoryOverView?.get(0)?.image,
                                    result.data?.coffeeCategoryOverView?.get(0)?.name
                                )
                            )

                            Log.d("성공성공", coffee.size.toString())

                            if (result?.data?.coffeeCategoryOverView?.size != 1) {
                                coffee.add(
                                    Main_Scrap(
                                        result.data?.coffeeCategoryOverView?.get(1)?.recipeid,
                                        result.data?.coffeeCategoryOverView?.get(1)?.likes,
                                        result.data?.coffeeCategoryOverView?.get(1)?.image,
                                        result.data?.coffeeCategoryOverView?.get(1)?.name
                                    )
                                )
                            }
                        }
                        Log.d("성공성공", coffee.size.toString())
                        if (result?.data?.beverageCategoryOverView?.size != 0) {


                            beverage.add(
                                Main_Scrap(
                                    result.data?.beverageCategoryOverView?.get(0)?.recipeid,
                                    result.data?.beverageCategoryOverView?.get(0)?.likes,
                                    result.data?.beverageCategoryOverView?.get(0)?.image,
                                    result.data?.beverageCategoryOverView?.get(0)?.name
                                )
                            )
                            if (result?.data?.beverageCategoryOverView?.size != 1) {
                                beverage.add(
                                    Main_Scrap(
                                        result.data?.beverageCategoryOverView?.get(1)?.recipeid,
                                        result.data?.beverageCategoryOverView?.get(1)?.likes,
                                        result.data?.beverageCategoryOverView?.get(1)?.image,
                                        result.data?.beverageCategoryOverView?.get(1)?.name
                                    )
                                )

                            }
                        }

                        if (result?.data?.teaCategoryOverView?.size != 0) {
                            tea.add(
                                Main_Scrap(
                                    result.data?.teaCategoryOverView?.get(0)?.recipeid,
                                    result.data?.teaCategoryOverView?.get(0)?.likes,
                                    result.data?.teaCategoryOverView?.get(0)?.image,
                                    result.data?.teaCategoryOverView?.get(0)?.name
                                )
                            )
                            if (result?.data?.teaCategoryOverView?.size != 1) {
                                tea.add(
                                    Main_Scrap(
                                        result.data?.teaCategoryOverView?.get(1)?.recipeid,
                                        result.data?.teaCategoryOverView?.get(1)?.likes,
                                        result.data?.teaCategoryOverView?.get(1)?.image,
                                        result.data?.teaCategoryOverView?.get(1)?.name
                                    )
                                )
                            }
                        }
                        Log.d("사이즈", result?.data?.adeCategoryOverView?.size.toString())

                        if (result?.data?.adeCategoryOverView?.size != 0) {
                            ade.add(
                                Main_Scrap(
                                    result.data?.adeCategoryOverView?.get(0)?.recipeid,
                                    result.data?.adeCategoryOverView?.get(0)?.likes,
                                    result.data?.adeCategoryOverView?.get(0)?.image,
                                    result.data?.adeCategoryOverView?.get(0)?.name
                                )
                            )
                            if (result?.data?.adeCategoryOverView?.size != 1) {

                                ade.add(
                                    Main_Scrap(
                                        result.data?.adeCategoryOverView?.get(1)?.recipeid,
                                        result.data?.adeCategoryOverView?.get(1)?.likes,
                                        result.data?.adeCategoryOverView?.get(1)?.image,
                                        result.data?.adeCategoryOverView?.get(1)?.name
                                    )
                                )
                            }
                        }
                        if (result?.data?.smoothieCategoryOverView?.size != 0) {
                            smootie_juice.add(
                                Main_Scrap(
                                    result.data?.smoothieCategoryOverView?.get(0)?.recipeid,
                                    result.data?.smoothieCategoryOverView?.get(0)?.likes,
                                    result.data?.smoothieCategoryOverView?.get(0)?.image,
                                    result.data?.smoothieCategoryOverView?.get(0)?.name
                                )
                            )
                            if (result?.data?.smoothieCategoryOverView?.size != 1) {
                                smootie_juice.add(
                                    Main_Scrap(
                                        result.data?.smoothieCategoryOverView?.get(1)?.recipeid,
                                        result.data?.smoothieCategoryOverView?.get(1)?.likes,
                                        result.data?.smoothieCategoryOverView?.get(1)?.image,
                                        result.data?.smoothieCategoryOverView?.get(1)?.name
                                    )
                                )
                            }
                        }
                        if (result?.data?.healthCategoryOverView?.size != 0) {
                            health.add(
                                Main_Scrap(
                                    result.data?.healthCategoryOverView?.get(0)?.recipeid,
                                    result.data?.healthCategoryOverView?.get(0)?.likes,
                                    result.data?.healthCategoryOverView?.get(0)?.image,
                                    result.data?.healthCategoryOverView?.get(0)?.name
                                )
                            )
                            if (result?.data?.healthCategoryOverView?.size != 1) {
                                health.add(
                                    Main_Scrap(
                                        result.data?.healthCategoryOverView?.get(1)?.recipeid,
                                        result.data?.healthCategoryOverView?.get(1)?.likes,
                                        result.data?.healthCategoryOverView?.get(1)?.image,
                                        result.data?.healthCategoryOverView?.get(1)?.name
                                    )
                                )
                            }
                        }
                        scraplist.add(scraps)
                        category.add(Home_receipe("커피", coffee))
                        category.add(Home_receipe("beverage", beverage))
                        category.add(Home_receipe("티", tea))
                        category.add(Home_receipe("에이드", ade))
                        category.add(Home_receipe("스무디/주스", smootie_juice))
                        category.add(Home_receipe("건강음료", health))
                    }
                    //마이스크랩 부분
                    viewBinding.homeRvMyScrap.layoutManager = LinearLayoutManager(
                        activity as HomeMainActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    val adapter1 = MainScrapAdapter(activity as HomeMainActivity, scraplist)
                    viewBinding.homeRvMyScrap.adapter = adapter1
                    Log.d("사이즈", scraps.size.toString())

                    adapter1.setOnItemClickListener1(object : MainScrapAdapter.OnItemClickListener {

                        override fun onItemClick(v: View?, pos: Int) {
                            var intent = Intent(
                                context,
                                ZipdabangRecipeDetailActivity::class.java
                            )
                            intent.putExtra("recipeId", scraps[0].recipeid.toString())
                            startActivity(intent)
                        }
                    })

                    adapter1.setOnItemClickListener2(object : MainScrapAdapter.OnItemClickListener {

                        override fun onItemClick(v: View?, pos: Int) {
                            var intent = Intent(
                                context,
                                ZipdabangRecipeDetailActivity::class.java
                            )
                            intent.putExtra("recipeId", scraps[1].recipeid.toString())
                            startActivity(intent)
                        }
                    })



                    requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

                    viewBinding.btTomyscrap.setOnClickListener {
                        val intent = Intent(activity, MyScapActivity::class.java)
                        startActivity(intent)
                    }

                    //카테고리별 음료
                    viewBinding.homeRvReceipe.layoutManager = LinearLayoutManager(
                        activity as HomeMainActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )

                    adapter2 = ReceipeAdapter(activity as HomeMainActivity, category)
                    viewBinding.homeRvReceipe.adapter = adapter2
                    adapter2.notifyDataSetChanged()

                    viewBinding.mainTvMy.setOnClickListener {
                        val intent = Intent(context, MySettingActivity::class.java)
                        startActivity(intent)


                    }



                    adapter2.setOnItemClickListener1(object : ReceipeAdapter.OnItemClickListener {

                        override fun onItemClick(v: View?, pos: Int, category: String) {
                            var intent = Intent(
                                context,
                                ZipdabangRecipeDetailActivity::class.java
                            )
                            if (category == "커피") intent.putExtra(
                                "recipeId",
                                coffee[0].recipeid.toString()
                            )
                            if (category == "beverage") intent.putExtra(
                                "recipeId",
                                beverage[0].recipeid.toString()
                            )
                            if (category == "티") intent.putExtra(
                                "recipeId",
                                tea[0].recipeid.toString()
                            )
                            if (category == "에이드") intent.putExtra(
                                "recipeId",
                                ade[0].recipeid.toString()
                            )
                            if (category == "스무디/주스") intent.putExtra(
                                "recipeId",
                                smootie_juice[0].recipeid.toString()
                            )
                            if (category == "건강음료") intent.putExtra(
                                "recipeId",
                                health[0].recipeid.toString()
                            )

                            startActivity(intent)
                        }
                    })

                    adapter2.setOnItemClickListener2(object : ReceipeAdapter.OnItemClickListener {

                        override fun onItemClick(v: View?, pos: Int, category: String) {
                            var intent = Intent(
                                context,
                                ZipdabangRecipeDetailActivity::class.java
                            )
                            if (category == "커피") intent.putExtra(
                                "recipeId",
                                coffee[1].recipeid.toString()
                            )
                            if (category == "beverage") intent.putExtra(
                                "recipeId",
                                beverage[1].recipeid.toString()
                            )
                            if (category == "티") intent.putExtra(
                                "recipeId",
                                tea[1].recipeid.toString()
                            )
                            if (category == "에이드") intent.putExtra(
                                "recipeId",
                                ade[1].recipeid.toString()
                            )
                            if (category == "스무디/주스") intent.putExtra(
                                "recipeId",
                                smootie_juice[1].recipeid.toString()
                            )
                            if (category == "건강음료") intent.putExtra(
                                "recipeId",
                                health[1].recipeid.toString()
                            )


                            startActivity(intent)
                        }
                    })

                    viewBinding.homeRvReceipe.isNestedScrollingEnabled = false
                    adapter2.setOnItemClickListener(object : ReceipeAdapter.OnItemClickListener {
                        override fun onItemClick(v: View?, pos: Int, category: String) {

                            when (pos) {

                                0 -> {
                                    val intent = Intent(context, CategoryCoffeeActivity::class.java)
                                    startActivity(intent)
                                }
                                1 -> {
                                    val intent =
                                        Intent(context, CategoryBeverageActivity::class.java)
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
                                    val intent =
                                        Intent(context, CategorySmoothieActivity::class.java)
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
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d("error", "onFailure 에러: " + t.message.toString());
                }


            })
        }


        //검색(엔터키 사용)
        viewBinding.etSearch.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(view: View?, i: Int, keyEvent: KeyEvent?): Boolean {
                when (i) {
                    KeyEvent.KEYCODE_ENTER -> {
                        //다음 액티비티에 정보 넘기기
                        val search = Intent(context, SearchActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        var string = viewBinding.etSearch.text.toString()
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


        //검색(검색 이미지 누름)
        viewBinding.ivSearch.setOnClickListener {

            val intent = Intent(context, SearchActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            var string = viewBinding.etSearch.text.toString()
            intent.putExtra("search", string)
            startActivity(intent)
            viewBinding.etSearch.setText("")
        }


        //카테고리별 목록 이동


        return viewBinding.root
    }

 override fun onResume() {

     super.onResume()
     if (resume == true) {
         GlobalScope.launch(Dispatchers.IO) {
             delay(0)
             viewBinding.etSearch.setText("")
             val tokenDb = TokenDatabase.getTokenDatabase(activity as HomeMainActivity)
             token1 = tokenDb.tokenDao().getToken().token.toString()
             Log.d("토큰", token1!!)


             //통신
             service.get_Main(token1).enqueue(object :
                 Callback<Main_Response> {


                 override fun onResponse(
                     call: Call<Main_Response>,
                     response: Response<Main_Response>

                 ) {
                     Log.d("성공", "SUCCESS")
                     // 정상적으로 통신이 성공된 경우
                     val result = response.body()
                     Log.d("오버뷰성공", "${result}")
                     if (result != null) {
                         if (scraps.size == 1) scraps.removeAt(0)
                         else if (scraps.size == 2) {
                             scraps.removeAt(0)
                             scraps.removeAt(0)
                         }
                         Log.d("사이즈", "${scraps.size}")

                         if (scraplist.size != 0) scraplist.removeAt(0)
                         Log.d("사이즈", "${scraplist.size}")

                         if (result?.data?.myScrapOverView?.size != 0) {

                             scraps.add(
                                 Main_Scrap(
                                     result.data?.myScrapOverView?.get(0)?.recipeid,
                                     result.data?.myScrapOverView?.get(0)?.likes,
                                     result.data?.myScrapOverView?.get(0)?.image,
                                     result.data?.myScrapOverView?.get(0)?.name
                                 )
                             )
                             Log.d("성공성공", scraps.size.toString())

                             if (result?.data?.myScrapOverView?.size != 1) {
                                 scraps.add(
                                     Main_Scrap(
                                         result.data?.myScrapOverView?.get(1)?.recipeid,
                                         result.data?.myScrapOverView?.get(1)?.likes,
                                         result.data?.myScrapOverView?.get(1)?.image,
                                         result.data?.myScrapOverView?.get(1)?.name
                                     )
                                 )
                             }
                         }






                         if (beverage.size != 0) {

                             beverage.get(0).recipeid =
                                 result.data?.beverageCategoryOverView?.get(0)?.recipeid
                             beverage.get(0).heart =
                                 result.data?.beverageCategoryOverView?.get(0)?.likes
                             beverage.get(0).ImageUrl =
                                 result.data?.beverageCategoryOverView?.get(0)?.image
                             beverage.get(0).title =
                                 result.data?.beverageCategoryOverView?.get(0)?.name

                             if (result?.data?.beverageCategoryOverView?.size != 1) {
                                 beverage.get(1).recipeid =
                                     result.data?.beverageCategoryOverView?.get(1)?.recipeid
                                 beverage.get(1).heart =
                                     result.data?.beverageCategoryOverView?.get(1)?.likes
                                 beverage.get(1).ImageUrl =
                                     result.data?.beverageCategoryOverView?.get(1)?.image
                                 beverage.get(1).title =
                                     result.data?.beverageCategoryOverView?.get(1)?.name
                             }
                         }
                         Log.d("성공성공", coffee.size.toString())
                         if (coffee.size != 0) {

                             if (result?.data?.coffeeCategoryOverView?.size != 0) {

                                 coffee.get(0).recipeid =
                                     result.data?.coffeeCategoryOverView?.get(0)?.recipeid
                                 coffee.get(0).heart =
                                     result.data?.coffeeCategoryOverView?.get(0)?.likes
                                 coffee.get(0).ImageUrl =
                                     result.data?.coffeeCategoryOverView?.get(0)?.image
                                 coffee.get(0).title =
                                     result.data?.coffeeCategoryOverView?.get(0)?.name

                                 if (result?.data?.coffeeCategoryOverView?.size != 1) {
                                     coffee.get(1).recipeid =
                                         result.data?.coffeeCategoryOverView?.get(1)?.recipeid
                                     coffee.get(1).heart =
                                         result.data?.coffeeCategoryOverView?.get(1)?.likes
                                     coffee.get(1).ImageUrl =
                                         result.data?.coffeeCategoryOverView?.get(1)?.image
                                     coffee.get(1).title =
                                         result.data?.coffeeCategoryOverView?.get(1)?.name
                                 }
                             }
                         }

                         if (tea.size != 0) {
                             if (result?.data?.teaCategoryOverView?.size != 0) {

                                 tea.get(0).recipeid =
                                     result.data?.teaCategoryOverView?.get(0)?.recipeid
                                 tea.get(0).heart =
                                     result.data?.teaCategoryOverView?.get(0)?.likes
                                 tea.get(0).ImageUrl =
                                     result.data?.teaCategoryOverView?.get(0)?.image
                                 tea.get(0).title =
                                     result.data?.teaCategoryOverView?.get(0)?.name

                                 if (result?.data?.teaCategoryOverView?.size != 1) {
                                     tea.get(1).recipeid =
                                         result.data?.teaCategoryOverView?.get(1)?.recipeid
                                     tea.get(1).heart =
                                         result.data?.teaCategoryOverView?.get(1)?.likes
                                     tea.get(1).ImageUrl =
                                         result.data?.teaCategoryOverView?.get(1)?.image
                                     tea.get(1).title =
                                         result.data?.teaCategoryOverView?.get(1)?.name
                                 }
                             }
                         }
                         Log.d("사이즈", result?.data?.adeCategoryOverView?.size.toString())
                         if (ade.size != 0) {

                             if (result?.data?.adeCategoryOverView?.size != 0) {

                                 ade.get(0).recipeid =
                                     result.data?.adeCategoryOverView?.get(0)?.recipeid
                                 ade.get(0).heart =
                                     result.data?.adeCategoryOverView?.get(0)?.likes
                                 ade.get(0).ImageUrl =
                                     result.data?.adeCategoryOverView?.get(0)?.image
                                 ade.get(0).title =
                                     result.data?.adeCategoryOverView?.get(0)?.name

                                 if (result?.data?.teaCategoryOverView?.size != 1) {
                                     ade.get(1).recipeid =
                                         result.data?.adeCategoryOverView?.get(1)?.recipeid
                                     ade.get(1).heart =
                                         result.data?.adeCategoryOverView?.get(1)?.likes
                                     ade.get(1).ImageUrl =
                                         result.data?.adeCategoryOverView?.get(1)?.image
                                     ade.get(1).title =
                                         result.data?.adeCategoryOverView?.get(1)?.name
                                 }
                             }
                         }
                         if (smootie_juice.size != 0) {

                             if (result?.data?.smoothieCategoryOverView?.size != 0) {

                                 smootie_juice.get(0).recipeid =
                                     result.data?.smoothieCategoryOverView?.get(0)?.recipeid
                                 smootie_juice.get(0).heart =
                                     result.data?.smoothieCategoryOverView?.get(0)?.likes
                                 smootie_juice.get(0).ImageUrl =
                                     result.data?.smoothieCategoryOverView?.get(0)?.image
                                 smootie_juice.get(0).title =
                                     result.data?.smoothieCategoryOverView?.get(0)?.name

                                 if (result?.data?.teaCategoryOverView?.size != 1) {
                                     smootie_juice.get(1).recipeid =
                                         result.data?.smoothieCategoryOverView?.get(1)?.recipeid
                                     smootie_juice.get(1).heart =
                                         result.data?.smoothieCategoryOverView?.get(1)?.likes
                                     smootie_juice.get(1).ImageUrl =
                                         result.data?.smoothieCategoryOverView?.get(1)?.image
                                     smootie_juice.get(1).title =
                                         result.data?.smoothieCategoryOverView?.get(1)?.name
                                 }
                             }
                         }
                         if (health.size != 0) {

                             if (result?.data?.healthCategoryOverView?.size != 0) {

                                 health.get(0).recipeid =
                                     result.data?.healthCategoryOverView?.get(0)?.recipeid
                                 health.get(0).heart =
                                     result.data?.healthCategoryOverView?.get(0)?.likes
                                 health.get(0).ImageUrl =
                                     result.data?.healthCategoryOverView?.get(0)?.image
                                 health.get(0).title =
                                     result.data?.healthCategoryOverView?.get(0)?.name

                                 if (result?.data?.healthCategoryOverView?.size != 1) {

                                     health.get(1).recipeid =
                                         result.data?.healthCategoryOverView?.get(1)?.recipeid
                                     health.get(1).heart =
                                         result.data?.healthCategoryOverView?.get(1)?.likes
                                     health.get(1).ImageUrl =
                                         result.data?.healthCategoryOverView?.get(1)?.image
                                     health.get(1).title =
                                         result.data?.healthCategoryOverView?.get(1)?.name
                                 }
                             }
                         }

                         scraplist.add(scraps)
                         if (category.size != 0) {
                             category[0] = Home_receipe("커피", coffee)
                             category[1] = Home_receipe("beverage", beverage)
                             category[2] = Home_receipe("티", tea)
                             category[3] = Home_receipe("에이드", ade)
                             category[4] = Home_receipe("스무디/주스", smootie_juice)
                             category[5] = Home_receipe("건강음료", health)
                         }
                     }
                     //마이스크랩 부분
                     viewBinding.homeRvMyScrap.layoutManager =
                         LinearLayoutManager(
                             activity as HomeMainActivity,
                             LinearLayoutManager.VERTICAL,
                             false
                         )
                     val adapter1 = MainScrapAdapter(
                         activity as HomeMainActivity,
                         scraplist
                     )
                     viewBinding.homeRvMyScrap.adapter = adapter1
                     Log.d("사이즈", scraps.size.toString())

                     //카테고리별 음료

                     adapter1.setOnItemClickListener1(object :
                         MainScrapAdapter.OnItemClickListener {

                         override fun onItemClick(v: View?, pos: Int) {
                             var intent = Intent(
                                 context,
                                 ZipdabangRecipeDetailActivity::class.java
                             )
                             intent.putExtra(
                                 "recipeId",
                                 scraps[0].recipeid.toString()
                             )
                             startActivity(intent)
                         }
                     })

                     adapter1.setOnItemClickListener2(object :
                         MainScrapAdapter.OnItemClickListener {

                         override fun onItemClick(v: View?, pos: Int) {
                             var intent = Intent(
                                 context,
                                 ZipdabangRecipeDetailActivity::class.java
                             )
                             intent.putExtra(
                                 "recipeId",
                                 scraps[1].recipeid.toString()
                             )
                             startActivity(intent)
                         }


                     })

                     viewBinding.homeRvReceipe.layoutManager = LinearLayoutManager(
                         activity as HomeMainActivity,
                         LinearLayoutManager.VERTICAL,
                         false
                     )

                     adapter2 = ReceipeAdapter(activity as HomeMainActivity, category)
                     viewBinding.homeRvReceipe.adapter = adapter2
                     adapter2.notifyDataSetChanged()


                     adapter2.setOnItemClickListener1(object : ReceipeAdapter.OnItemClickListener {

                         override fun onItemClick(v: View?, pos: Int, category: String) {
                             var intent = Intent(
                                 context,
                                 ZipdabangRecipeDetailActivity::class.java
                             )
                             if (category == "커피") intent.putExtra(
                                 "recipeId",
                                 coffee[0].recipeid.toString()
                             )
                             if (category == "beverage") intent.putExtra(
                                 "recipeId",
                                 beverage[0].recipeid.toString()
                             )
                             if (category == "티") intent.putExtra(
                                 "recipeId",
                                 tea[0].recipeid.toString()
                             )
                             if (category == "에이드") intent.putExtra(
                                 "recipeId",
                                 ade[0].recipeid.toString()
                             )
                             if (category == "스무디/주스") intent.putExtra(
                                 "recipeId",
                                 smootie_juice[0].recipeid.toString()
                             )
                             if (category == "건강음료") intent.putExtra(
                                 "recipeId",
                                 health[0].recipeid.toString()
                             )

                             startActivity(intent)
                         }
                     })

                     adapter2.setOnItemClickListener2(object : ReceipeAdapter.OnItemClickListener {

                         override fun onItemClick(v: View?, pos: Int, category: String) {
                             var intent = Intent(
                                 context,
                                 ZipdabangRecipeDetailActivity::class.java
                             )
                             if (category == "커피") intent.putExtra(
                                 "recipeId",
                                 coffee[1].recipeid.toString()
                             )
                             if (category == "beverage") intent.putExtra(
                                 "recipeId",
                                 beverage[1].recipeid.toString()
                             )
                             if (category == "티") intent.putExtra(
                                 "recipeId",
                                 tea[1].recipeid.toString()
                             )
                             if (category == "에이드") intent.putExtra(
                                 "recipeId",
                                 ade[1].recipeid.toString()
                             )
                             if (category == "스무디/주스") intent.putExtra(
                                 "recipeId",
                                 smootie_juice[1].recipeid.toString()
                             )
                             if (category == "건강음료") intent.putExtra(
                                 "recipeId",
                                 health[1].recipeid.toString()
                             )


                             startActivity(intent)
                         }
                     })

                     viewBinding.homeRvReceipe.isNestedScrollingEnabled = false
                     adapter2.setOnItemClickListener(object : ReceipeAdapter.OnItemClickListener {
                         override fun onItemClick(v: View?, pos: Int, category: String) {

                             when (pos) {

                                 0 -> {
                                     val intent = Intent(context, CategoryCoffeeActivity::class.java)
                                     startActivity(intent)
                                 }
                                 1 -> {
                                     val intent =
                                         Intent(context, CategoryBeverageActivity::class.java)
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
                                     val intent =
                                         Intent(context, CategorySmoothieActivity::class.java)
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

                 override fun onFailure(
                     call: Call<Main_Response>,
                     t: Throwable
                 ) {
                     // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                     Log.d(
                         "error",
                         "onFailure 에러: " + t.message.toString()
                     );
                 }

             })


         }


     } else resume = true



 }
}








