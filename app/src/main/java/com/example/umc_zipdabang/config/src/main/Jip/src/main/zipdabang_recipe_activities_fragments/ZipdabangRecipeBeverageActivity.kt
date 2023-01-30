package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.AllRecipesData
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.BeverageRecipesData
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.CoffeeRecipesData
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.BeverageLoadingRVAdapter
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.BeverageRecipesRVAdapter
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.CoffeeLoadingRVAdapter
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeBeverageBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Runnable

class ZipdabangRecipeBeverageActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeBeverageBinding

    private var isLoading = false
    var grid = 2
    val beverageRecipesList: ArrayList<BeverageRecipesData> = arrayListOf()

    private lateinit var beverageRecipesRVAdapter: BeverageLoadingRVAdapter

    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeBeverageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

//        val beverageRecipesList: ArrayList<BeverageRecipesData> = arrayListOf()
//        beverageRecipesList.apply {
//            // add(AllRecipesData(사진, 커피명, 좋아요 수)
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
//            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
//        }
//
//        val beverageRecipesRVAdapter = BeverageRecipesRVAdapter(beverageRecipesList)
//
//        viewBinding.rvZipdabangRecipeBeverage.adapter = beverageRecipesRVAdapter
//        viewBinding.rvZipdabangRecipeBeverage.layoutManager = GridLayoutManager(this, 2)

        val recipeRetrofit = Retrofit.Builder()
            .baseUrl("http://zipdabang.store:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val recipeService = recipeRetrofit.create(RecipeService::class.java)

        val tokenDb = TokenDatabase.getTokenDatabase(this)

        lateinit var firstResult: List<RecipeInfo>

        GlobalScope.launch(Dispatchers.IO) {
            val token = tokenDb.tokenDao().getToken()
            val tokenNum = token.token
            Log.d("토큰 넘버", "${tokenNum}")
            recipeService.getCategoryRecipes(tokenNum, 2, 0, 1).enqueue(object :
                Callback<ZipdabangRecipes> {
                override fun onResponse(
                    call: Call<ZipdabangRecipes>,
                    response: Response<ZipdabangRecipes>
                ) {
                    val result = response.body()
                    Log.d("Bev. 카테고리 레시피 Get 성공", "${result}")
                    var firstResultArray = arrayListOf<RecipeInfo?>()
                    for (i in 0 until result?.data!!.size) {
                        val firstResult = result?.data?.get(i)
                        firstResultArray.add(firstResult)
                        Log.d("첫번째 배열", "${firstResultArray}")
                    }

                    val firstResultIdArray = arrayListOf<Int?>()
                    val firstResultNameArray = arrayListOf<String?>()
                    val firstResultImgUrlArray = ArrayList<String?>()
                    val firstResultLikesArray = ArrayList<Int?>()

                    for (i in 0 until firstResultArray.size) {
                        firstResultIdArray.add(firstResultArray[i]?.id)
                        firstResultNameArray.add(firstResultArray[i]?.name)
                        firstResultImgUrlArray.add(firstResultArray[i]?.imageUrl)
                        firstResultLikesArray.add(firstResultArray[i]?.likes)
                        Log.d("${i}번째 아이디", "${firstResultArray[i]?.id}")
                        Log.d("${i}번째 이름", "${firstResultArray[i]?.name}")
                        Log.d("${i}번째 이미지", "${firstResultArray[i]?.imageUrl}")
                        Log.d("${i}번째 좋아요", "${firstResultArray[i]?.likes}")

                        beverageRecipesList.add(
                            BeverageRecipesData(
                                firstResultArray[i]?.imageUrl,
                                firstResultArray[i]?.name,
                                firstResultArray[i]?.likes
                            )
                        )
                    }
                    beverageRecipesRVAdapter = BeverageLoadingRVAdapter(this@ZipdabangRecipeBeverageActivity, beverageRecipesList, firstResultIdArray)
                    layoutManager = GridLayoutManager(this@ZipdabangRecipeBeverageActivity, 2)
                    viewBinding.rvZipdabangRecipeBeverage.setLayoutManager(layoutManager)
                    viewBinding.rvZipdabangRecipeBeverage.setAdapter(beverageRecipesRVAdapter)
                    layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {

                            if (position == 0)
                            {

                                return 1


                            }
                            else if ((position % 12 == 0) && position == (beverageRecipesList.size-1))
                            {

                                return 2
                            }
                            else
                            {

                                return 1
                            }

                        }
                    })

                    viewBinding.rvZipdabangRecipeBeverage.setOnScrollListener(object : RecyclerView.OnScrollListener() {

                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            if (!isLoading) {
                                if (viewBinding.rvZipdabangRecipeBeverage.layoutManager != null && (viewBinding.rvZipdabangRecipeBeverage.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (beverageRecipesList.size - 1)) {
                                    //리스트 마지막o
//                                    moreItems()

                                    val runnable = Runnable {

                                        beverageRecipesList.add(BeverageRecipesData(null, null, null))
                                        Log.d("insert before", "msg")
                                        beverageRecipesRVAdapter.notifyItemInserted(beverageRecipesList.size - 1)

                                    }

                                    viewBinding.rvZipdabangRecipeBeverage.post(runnable)

                                    GlobalScope.launch {
                                        delay(2000)
                                        withContext(Dispatchers.Main) {
                                            beverageRecipesList.removeAt(beverageRecipesList.size - 1)
                                            val scrollToPosition = beverageRecipesList.size
                                            beverageRecipesRVAdapter.notifyItemRemoved(scrollToPosition)

                                            recipeService.getCategoryRecipesScroll(tokenNum, 2, firstResultIdArray.get(firstResultIdArray.size-1), 0, 1).enqueue(object: Callback<ZipdabangRecipes> {
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
                                                            beverageRecipesList.add(
                                                                BeverageRecipesData(
                                                                    firstResultArray[i]?.imageUrl,
                                                                    firstResultArray[i]?.name,
                                                                    firstResultArray[i]?.likes
                                                                )
                                                            )
                                                            Log.d("아이디 배열 결과", "${firstResultIdArray}")
                                                            beverageRecipesRVAdapter.notifyDataSetChanged()
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

//                                    CoroutineScope(mainDispatcher).launch {
//                                        delay(2000)
//                                        val runnable2 = Runnable {
//
//
//
//                                        }
//                                        runnable2.run()
//
//                                    }


                                    isLoading = true

                                }
                            }
                        }
                    })




//                    setData()
//                    initAdapter()
//                    initScrollListener()

                }

                override fun onFailure(call: Call<ZipdabangRecipes>, t: Throwable) {
                    Log.d("Bev. 카테고리 레시피 Get", "실패")
                }
            })
        }


        viewBinding.toolbarBackarrow.setOnClickListener{
            // 툴바의 뒤로가기 버튼을 눌렀을 때 동작

            finish()
        }
    }

//    private fun setData() {
//        beverageRecipesList.add(
//            BeverageRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        beverageRecipesList.add(
//            BeverageRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        beverageRecipesList.add(
//            BeverageRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        beverageRecipesList.add(
//            BeverageRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        beverageRecipesList.add(
//            BeverageRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        beverageRecipesList.add(
//            BeverageRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        beverageRecipesList.add(
//            BeverageRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        beverageRecipesList.add(
//            BeverageRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        beverageRecipesList.add(
//            BeverageRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        beverageRecipesList.add(
//            BeverageRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        beverageRecipesList.add(
//            BeverageRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        beverageRecipesList.add(
//            BeverageRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//
//    }
//
//
//    private fun initAdapter() {
//        beverageRecipesRVAdapter = BeverageLoadingRVAdapter(this, beverageRecipesList)
//        layoutManager = GridLayoutManager(this, 2)
//        viewBinding.rvZipdabangRecipeBeverage.setLayoutManager(layoutManager)
//        viewBinding.rvZipdabangRecipeBeverage.setAdapter(beverageRecipesRVAdapter)
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
//                else if ((position % 12 == 0) && position == (beverageRecipesList.size-1))
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
//        viewBinding.rvZipdabangRecipeBeverage.setOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (!isLoading) {
//                    if (viewBinding.rvZipdabangRecipeBeverage.layoutManager != null && (viewBinding.rvZipdabangRecipeBeverage.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (beverageRecipesList.size - 1)) {
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
//            beverageRecipesList.add(BeverageRecipesData(null, null, null))
//
//            Log.d("insert before", "msg")
//
//            beverageRecipesRVAdapter.notifyItemInserted(beverageRecipesList.size - 1)
//
//
//
//
//
//        }
//        viewBinding.rvZipdabangRecipeBeverage.post(runnable)
//
//        CoroutineScope(mainDispatcher).launch {
//            delay(2000)
//            val runnable2 = Runnable {
//
//                beverageRecipesList.removeAt(beverageRecipesList.size - 1)
//                val scrollToPosition = beverageRecipesList.size
//                beverageRecipesRVAdapter.notifyItemRemoved(scrollToPosition)
//
//
//                beverageRecipesList.add(
//                    BeverageRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                beverageRecipesList.add(
//                    BeverageRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                beverageRecipesList.add(
//                    BeverageRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                beverageRecipesList.add(
//                    BeverageRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                beverageRecipesList.add(
//                    BeverageRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                beverageRecipesList.add(
//                    BeverageRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                beverageRecipesList.add(
//                    BeverageRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                beverageRecipesList.add(
//                    BeverageRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                beverageRecipesList.add(
//                    BeverageRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                beverageRecipesList.add(
//                    BeverageRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                beverageRecipesList.add(
//                    BeverageRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                beverageRecipesList.add(
//                    BeverageRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//
//
//                beverageRecipesRVAdapter.notifyDataSetChanged()
//                isLoading = false
//
//            }
//            runnable2.run()
//        }
//    }

}