package com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.UMC.umc_zipdabang.databinding.ActivityZipdabangRecipeWellbeingBinding
import com.UMC.zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.WellbeingRecipesData
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.WellbeingLoadingRVAdapter
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Runnable

class ZipdabangRecipeWellbeingActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeWellbeingBinding

    private var isLoading = false
    var grid = 2
    val wellbeingRecipesList: ArrayList<WellbeingRecipesData> = arrayListOf()

    private lateinit var wellbeingRecipesRVAdapter: WellbeingLoadingRVAdapter

    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeWellbeingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

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
            recipeService.getCategoryRecipes(tokenNum, 6, 0, 1).enqueue(object :
                Callback<ZipdabangRecipes> {
                override fun onResponse(
                    call: Call<ZipdabangRecipes>,
                    response: Response<ZipdabangRecipes>
                ) {
                    val result = response.body()
                    Log.d("웰빙 카테고리 레시피 Get 성공", "${result}")
                    var firstResultArray = arrayListOf<RecipeInfo?>()

                    if (result?.data != null) {
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
                            wellbeingRecipesList.add(
                                WellbeingRecipesData(
                                    firstResultArray[i]?.imageUrl,
                                    firstResultArray[i]?.name,
                                    firstResultArray[i]?.likes
                                )
                            )
                        }

                        wellbeingRecipesRVAdapter = WellbeingLoadingRVAdapter(this@ZipdabangRecipeWellbeingActivity, wellbeingRecipesList, firstResultIdArray)
                        layoutManager = GridLayoutManager(this@ZipdabangRecipeWellbeingActivity, 2)
                        viewBinding.rvZipdabangRecipeWellbeing.setLayoutManager(layoutManager)
                        viewBinding.rvZipdabangRecipeWellbeing.setAdapter(wellbeingRecipesRVAdapter)
                        layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {

                                if (position == 0)
                                {

                                    return 1


                                }
                                else if ((position % 12 == 0) && position == (wellbeingRecipesList.size-1))
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
                        viewBinding.rvZipdabangRecipeWellbeing.setOnScrollListener(object : RecyclerView.OnScrollListener() {

                            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                                super.onScrolled(recyclerView, dx, dy)
                                if (!isLoading) {
                                    if (viewBinding.rvZipdabangRecipeWellbeing.layoutManager != null && (viewBinding.rvZipdabangRecipeWellbeing.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (wellbeingRecipesList.size - 1)) {
                                        //리스트 마지막o
//                                    moreItems()

                                        val runnable = Runnable {

                                            wellbeingRecipesList.add(WellbeingRecipesData(null, null, null))
                                            Log.d("insert before", "msg")
                                            wellbeingRecipesRVAdapter.notifyItemInserted(wellbeingRecipesList.size - 1)

                                        }

                                        viewBinding.rvZipdabangRecipeWellbeing.post(runnable)

                                        GlobalScope.launch {
                                            delay(2000)
                                            withContext(Dispatchers.Main) {
                                                wellbeingRecipesList.removeAt(wellbeingRecipesList.size - 1)
                                                val scrollToPosition = wellbeingRecipesList.size
                                                wellbeingRecipesRVAdapter.notifyItemRemoved(scrollToPosition)

                                                recipeService.getCategoryRecipesScroll(tokenNum, 6, firstResultIdArray.get(firstResultIdArray.size-1), 0, 1).enqueue(object: Callback<ZipdabangRecipes> {
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
                                                                wellbeingRecipesList.add(
                                                                    WellbeingRecipesData(
                                                                        firstResultArray[i]?.imageUrl,
                                                                        firstResultArray[i]?.name,
                                                                        firstResultArray[i]?.likes
                                                                    )
                                                                )
                                                                Log.d("아이디 배열 결과", "${firstResultIdArray}")
                                                                wellbeingRecipesRVAdapter.notifyDataSetChanged()
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



                    // initScrollListener()
//                    viewBinding.rvZipdabangRecipeWellbeing.setOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                            super.onScrolled(recyclerView, dx, dy)
//                            if (!isLoading) {
//                                if (viewBinding.rvZipdabangRecipeWellbeing.layoutManager != null && (viewBinding.rvZipdabangRecipeWellbeing.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (wellbeingRecipesList.size - 1)) {
//                                    //리스트 마지막o - moreItems() 시작
//                                    val runnable = Runnable {
//
//                                        wellbeingRecipesList.add(WellbeingRecipesData(null, null, null))
//
//                                        Log.d("insert before", "msg")
//
//                                        wellbeingRecipesRVAdapter.notifyItemInserted(wellbeingRecipesList.size - 1)
//
//                                    }
//
//                                    viewBinding.rvZipdabangRecipeWellbeing.post(runnable)
//
//                                    CoroutineScope(mainDispatcher).launch {
//                                        delay(2000)
//                                        val runnable2 = Runnable {
//
//                                            wellbeingRecipesList.removeAt(wellbeingRecipesList.size - 1)
//                                            val scrollToPosition = wellbeingRecipesList.size
//                                            wellbeingRecipesRVAdapter.notifyItemRemoved(scrollToPosition)
//
//                                            // 데이터 가져오기
//
//                                            // 데이터 할당하기
//
//
//                                            wellbeingRecipesList.add(
//                                                WellbeingRecipesData(
//                                                    "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                                                    "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                                                    12
//                                                )
//                                            )
//                                            wellbeingRecipesList.add(
//                                                WellbeingRecipesData(
//                                                    "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                                                    "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                                                    12
//                                                )
//                                            )
//                                            wellbeingRecipesList.add(
//                                                WellbeingRecipesData(
//                                                    "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                                                    "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                                                    12
//                                                )
//                                            )
//                                            wellbeingRecipesList.add(
//                                                WellbeingRecipesData(
//                                                    "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                                                    "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                                                    12
//                                                )
//                                            )
//                                            wellbeingRecipesList.add(
//                                                WellbeingRecipesData(
//                                                    "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                                                    "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                                                    12
//                                                )
//                                            )
//                                            wellbeingRecipesList.add(
//                                                WellbeingRecipesData(
//                                                    "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                                                    "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                                                    12
//                                                )
//                                            )
//                                            wellbeingRecipesList.add(
//                                                WellbeingRecipesData(
//                                                    "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                                                    "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                                                    12
//                                                )
//                                            )
//                                            wellbeingRecipesList.add(
//                                                WellbeingRecipesData(
//                                                    "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                                                    "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                                                    12
//                                                )
//                                            )
//                                            wellbeingRecipesList.add(
//                                                WellbeingRecipesData(
//                                                    "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                                                    "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                                                    12
//                                                )
//                                            )
//                                            wellbeingRecipesList.add(
//                                                WellbeingRecipesData(
//                                                    "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                                                    "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                                                    12
//                                                )
//                                            )
//                                            wellbeingRecipesList.add(
//                                                WellbeingRecipesData(
//                                                    "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                                                    "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                                                    12
//                                                )
//                                            )
//                                            wellbeingRecipesList.add(
//                                                WellbeingRecipesData(
//                                                    "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                                                    "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                                                    12
//                                                )
//                                            )
//
//
//                                            wellbeingRecipesRVAdapter.notifyDataSetChanged()
//                                            isLoading = false
//
//                                        }
//                                        runnable2.run()
//                                    }
//                                    // moreitems() 끝
//                                    isLoading = true
//
//                                }
//                            }
//                        }
//                    })

                }

                override fun onFailure(call: Call<ZipdabangRecipes>, t: Throwable) {
                    Log.d("웰빙 카테고리 레시피 Get", "실패")
                }
            })


        }

//        setData()
//        initAdapter()
//        initScrollListener()

        viewBinding.toolbarBackarrow.setOnClickListener{
            // 툴바의 뒤로가기 버튼을 눌렀을 때 동작

            finish()
        }
    }

//    private fun setData() {
//        wellbeingRecipesList.add(
//            WellbeingRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        wellbeingRecipesList.add(
//            WellbeingRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        wellbeingRecipesList.add(
//            WellbeingRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        wellbeingRecipesList.add(
//            WellbeingRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        wellbeingRecipesList.add(
//            WellbeingRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        wellbeingRecipesList.add(
//            WellbeingRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        wellbeingRecipesList.add(
//            WellbeingRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        wellbeingRecipesList.add(
//            WellbeingRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        wellbeingRecipesList.add(
//            WellbeingRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        wellbeingRecipesList.add(
//            WellbeingRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        wellbeingRecipesList.add(
//            WellbeingRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        wellbeingRecipesList.add(
//            WellbeingRecipesData(
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
//        wellbeingRecipesRVAdapter = WellbeingLoadingRVAdapter(this, wellbeingRecipesList)
//        layoutManager = GridLayoutManager(this, 2)
//        viewBinding.rvZipdabangRecipeWellbeing.setLayoutManager(layoutManager)
//        viewBinding.rvZipdabangRecipeWellbeing.setAdapter(wellbeingRecipesRVAdapter)
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
//                else if ((position % 12 == 0) && position == (wellbeingRecipesList.size-1))
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
//        viewBinding.rvZipdabangRecipeWellbeing.setOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (!isLoading) {
//                    if (viewBinding.rvZipdabangRecipeWellbeing.layoutManager != null && (viewBinding.rvZipdabangRecipeWellbeing.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (wellbeingRecipesList.size - 1)) {
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
//
//        val runnable = Runnable {
//
//            wellbeingRecipesList.add(WellbeingRecipesData(null, null, null))
//
//            Log.d("insert before", "msg")
//
//            wellbeingRecipesRVAdapter.notifyItemInserted(wellbeingRecipesList.size - 1)
//
//
//
//
//
//        }
//        viewBinding.rvZipdabangRecipeWellbeing.post(runnable)
//
//        CoroutineScope(mainDispatcher).launch {
//            delay(2000)
//            val runnable2 = Runnable {
//
//                wellbeingRecipesList.removeAt(wellbeingRecipesList.size - 1)
//                val scrollToPosition = wellbeingRecipesList.size
//                wellbeingRecipesRVAdapter.notifyItemRemoved(scrollToPosition)
//
//
//                wellbeingRecipesList.add(
//                    WellbeingRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                wellbeingRecipesList.add(
//                    WellbeingRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                wellbeingRecipesList.add(
//                    WellbeingRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                wellbeingRecipesList.add(
//                    WellbeingRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                wellbeingRecipesList.add(
//                    WellbeingRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                wellbeingRecipesList.add(
//                    WellbeingRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                wellbeingRecipesList.add(
//                    WellbeingRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                wellbeingRecipesList.add(
//                    WellbeingRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                wellbeingRecipesList.add(
//                    WellbeingRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                wellbeingRecipesList.add(
//                    WellbeingRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                wellbeingRecipesList.add(
//                    WellbeingRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                wellbeingRecipesList.add(
//                    WellbeingRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//
//
//                wellbeingRecipesRVAdapter.notifyDataSetChanged()
//                isLoading = false
//
//            }
//            runnable2.run()
//        }
//    }
}