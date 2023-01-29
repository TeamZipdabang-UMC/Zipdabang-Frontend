package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.AdeRecipesData
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.AllRecipesData
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.CoffeeRecipesData
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.AdeLoadingRVAdapter
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.BeverageLoadingRVAdapter
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeAdeBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Runnable

class ZipdabangRecipeAdeActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeAdeBinding

    private var isLoading = false
    var grid = 2
    val adeRecipesList: ArrayList<AdeRecipesData> = arrayListOf()

    private lateinit var adeRecipesRVAdapter: AdeLoadingRVAdapter

    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeAdeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val recipeRetrofit = Retrofit.Builder()
            .baseUrl("http://zipdabang.store:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val recipeService = recipeRetrofit.create(RecipeService::class.java)

        val tokenDb = TokenDatabase.getTokenDatabase(this)

        lateinit var firstResult: List<RecipeInfo>

        val firstResultIdArray = arrayListOf<Int?>()
        val firstResultNameArray = arrayListOf<String?>()
        val firstResultImgUrlArray = ArrayList<String?>()
        val firstResultLikesArray = ArrayList<Int?>()



        GlobalScope.launch(Dispatchers.IO) {
            val token = tokenDb.tokenDao().getToken()
            val tokenNum = token.token
            Log.d("토큰 넘버", "${tokenNum}")
            recipeService.getCategoryRecipes(tokenNum, 4, 0, 1).enqueue(object :
                Callback<ZipdabangRecipes> {
                override fun onResponse(
                    call: Call<ZipdabangRecipes>,
                    response: Response<ZipdabangRecipes>
                ) {
                    val result = response.body()
                    Log.d("Ade 카테고리 레시피 Get 성공", "${result}")
                    var firstResultArray = arrayListOf<RecipeInfo?>()
                    for (i in 0 until result?.data!!.size) {
                        val firstResult = result?.data?.get(i)
                        firstResultArray.add(firstResult)
                        Log.d("첫번째 배열", "${firstResultArray}")
                    }



                    for (i in 0 until firstResultArray.size) {
                        firstResultIdArray.add(firstResultArray[i]?.id)
                        firstResultNameArray.add(firstResultArray[i]?.name)
                        firstResultImgUrlArray.add(firstResultArray[i]?.imageUrl)
                        firstResultLikesArray.add(firstResultArray[i]?.likes)
                        Log.d("${i}번째 아이디", "${firstResultArray[i]?.id}")
                        Log.d("${i}번째 이름", "${firstResultArray[i]?.name}")
                        Log.d("${i}번째 이미지", "${firstResultArray[i]?.imageUrl}")
                        Log.d("${i}번째 좋아요", "${firstResultArray[i]?.likes}")
                        adeRecipesList.add(
                            AdeRecipesData(
                                firstResultArray[i]?.imageUrl,
                                firstResultArray[i]?.name,
                                firstResultArray[i]?.likes
                            )
                        )


                    }
                    Log.d("Id 목록", "${firstResultIdArray}")
                    adeRecipesRVAdapter = AdeLoadingRVAdapter(this@ZipdabangRecipeAdeActivity, adeRecipesList, firstResultIdArray)
                    layoutManager = GridLayoutManager(this@ZipdabangRecipeAdeActivity, 2)
                    viewBinding.rvZipdabangRecipeAde.setLayoutManager(layoutManager)
                    viewBinding.rvZipdabangRecipeAde.setAdapter(adeRecipesRVAdapter)
                    layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {

                            if (position == 0)
                            {

                                return 1


                            }
                            else if ((position % 12 == 0) && position == (adeRecipesList.size-1))
                            {

                                return 2
                            }
                            else
                            {

                                return 1
                            }

                        }
                    })

                    // 시작
                    viewBinding.rvZipdabangRecipeAde.setOnScrollListener(object : RecyclerView.OnScrollListener() {

                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            if (!isLoading) {
                                if (viewBinding.rvZipdabangRecipeAde.layoutManager != null && (viewBinding.rvZipdabangRecipeAde.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (adeRecipesList.size - 1)) {
                                    //리스트 마지막o
//                                    moreItems()

                                    val runnable = Runnable {

                                        adeRecipesList.add(AdeRecipesData(null, null, null))
                                        Log.d("insert before", "msg")
                                        adeRecipesRVAdapter.notifyItemInserted(adeRecipesList.size - 1)

                                    }

                                    viewBinding.rvZipdabangRecipeAde.post(runnable)

                                    GlobalScope.launch {
                                        delay(2000)
                                        withContext(Dispatchers.Main) {
                                            adeRecipesList.removeAt(adeRecipesList.size - 1)
                                            val scrollToPosition = adeRecipesList.size
                                            adeRecipesRVAdapter.notifyItemRemoved(scrollToPosition)

                                            recipeService.getCategoryRecipesScroll(tokenNum, 4, firstResultIdArray.get(firstResultIdArray.size-1), 0, 1).enqueue(object: Callback<ZipdabangRecipes> {
                                                override fun onResponse(
                                                    call: Call<ZipdabangRecipes>,
                                                    response: Response<ZipdabangRecipes>
                                                ) {

                                                    var moreResult = response.body()
                                                    firstResultArray = ArrayList<RecipeInfo?>()
                                                    Log.d("more result 결과", "${moreResult}")

                                                    if (moreResult != null) {
                                                        for (i in 0 until moreResult?.data!!.size) {
                                                            val moreResultData = moreResult?.data?.get(i)
                                                            firstResultArray.add(moreResultData)
                                                        }

                                                        Log.d("last", "${firstResultIdArray.get(firstResultIdArray.size-1)}")
                                                        Log.d("다음 배열", "${firstResultArray}")

                                                        for (i in 0 until moreResult?.data!!.size) {
                                                            firstResultIdArray.add(firstResultArray[i]?.id)
                                                            firstResultNameArray.add(firstResultArray[i]?.name)
                                                            firstResultImgUrlArray.add(firstResultArray[i]?.imageUrl)
                                                            firstResultLikesArray.add(firstResultArray[i]?.likes)
                                                            Log.d("${i}번째 아이디", "${firstResultArray[i]?.id}")
                                                            Log.d("${i}번째 이름", "${firstResultArray[i]?.name}")
                                                            Log.d("${i}번째 이미지", "${firstResultArray[i]?.imageUrl}")
                                                            Log.d("${i}번째 좋아요", "${firstResultArray[i]?.likes}")
                                                            adeRecipesList.add(
                                                                AdeRecipesData(
                                                                    firstResultArray[i]?.imageUrl,
                                                                    firstResultArray[i]?.name,
                                                                    firstResultArray[i]?.likes
                                                                )
                                                            )
                                                            Log.d("아이디 배열 결과", "${firstResultIdArray}")
                                                            adeRecipesRVAdapter.notifyDataSetChanged()
                                                            isLoading = false
                                                        }
                                                    }


                                                }

                                                override fun onFailure(
                                                    call: Call<ZipdabangRecipes>,
                                                    t: Throwable
                                                ) {
                                                    Log.d("추가 레시피 불러오기", "실패")
                                                }
                                            })
                                        }
                                    }

                                    isLoading = true

                                }
                            }
                        }
                    })
                    // 끝





                }

                override fun onFailure(call: Call<ZipdabangRecipes>, t: Throwable) {
                    Log.d("Ade 카테고리 레시피 Get", "실패")
                }
            })
        }

//        setData()
//        initAdapter()
//        initScrollListener()


//        adeRecipesList.apply {
//            // add(AllRecipesData(사진, 커피명, 좋아요 수)
//            add(AdeRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
//            add(AdeRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
//            add(AdeRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
//            add(AdeRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
//            add(AdeRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
//            add(AdeRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
//            add(AdeRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
//            add(AdeRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
//        }


        viewBinding.toolbarBackarrow.setOnClickListener{
            // 툴바의 뒤로가기 버튼을 눌렀을 때 동작
            finish()
        }
    }

//    private fun setData() {
//        adeRecipesList.add(
//            AdeRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        adeRecipesList.add(
//            AdeRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        adeRecipesList.add(
//            AdeRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        adeRecipesList.add(
//            AdeRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        adeRecipesList.add(
//            AdeRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        adeRecipesList.add(
//            AdeRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        adeRecipesList.add(
//            AdeRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        adeRecipesList.add(
//            AdeRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        adeRecipesList.add(
//            AdeRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        adeRecipesList.add(
//            AdeRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        adeRecipesList.add(
//            AdeRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        adeRecipesList.add(
//            AdeRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//    }
//
//
//    private fun initAdapter() {
//        adeRecipesRVAdapter = AdeLoadingRVAdapter(this, adeRecipesList, )
//        layoutManager = GridLayoutManager(this, 2)
//        viewBinding.rvZipdabangRecipeAde.setLayoutManager(layoutManager)
//        viewBinding.rvZipdabangRecipeAde.setAdapter(adeRecipesRVAdapter)
//
//        layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//
//                if (position == 0)
//                {
//
//                    return 1
//
//
//                }
//                else if ((position % 12 == 0) && position == (adeRecipesList.size-1))
//                {
//
//                    return 2
//                }
//                else
//                {
//
//                    return 1
//                }
//
//            }
//        })
//    }
//
//    private fun initScrollListener() {
//
//        viewBinding.rvZipdabangRecipeAde.setOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (!isLoading) {
//                    if (viewBinding.rvZipdabangRecipeAde.layoutManager != null && (viewBinding.rvZipdabangRecipeAde.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (adeRecipesList.size - 1)) {
//                        //리스트 마지막o
//                        moreItems()
//                        isLoading = true
//
//                    }
//                }
//            }
//        })
//    }
//
//
//    private fun moreItems() {
//        val runnable = Runnable {
//
//            adeRecipesList.add(AdeRecipesData(null, null, null))
//
//            Log.d("insert before", "msg")
//
//            adeRecipesRVAdapter.notifyItemInserted(adeRecipesList.size - 1)
//
//
//
//
//
//        }
//        viewBinding.rvZipdabangRecipeAde.post(runnable)
//
//        CoroutineScope(mainDispatcher).launch {
//            delay(2000)
//            val runnable2 = Runnable {
//
//                adeRecipesList.removeAt(adeRecipesList.size - 1)
//                val scrollToPosition = adeRecipesList.size
//                adeRecipesRVAdapter.notifyItemRemoved(scrollToPosition)
//
//
//                adeRecipesList.add(
//                    AdeRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                adeRecipesList.add(
//                    AdeRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                adeRecipesList.add(
//                    AdeRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                adeRecipesList.add(
//                    AdeRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                adeRecipesList.add(
//                    AdeRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                adeRecipesList.add(
//                    AdeRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                adeRecipesList.add(
//                    AdeRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                adeRecipesList.add(
//                    AdeRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                adeRecipesList.add(
//                    AdeRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                adeRecipesList.add(
//                    AdeRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                adeRecipesList.add(
//                    AdeRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                adeRecipesList.add(
//                    AdeRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//
//                adeRecipesRVAdapter.notifyDataSetChanged()
//                isLoading = false
//
//            }
//            runnable2.run()
//        }
//    }
}