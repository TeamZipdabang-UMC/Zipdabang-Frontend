package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.config.src.main.Home.HomeMainActivity
import com.example.umc_zipdabang.databinding.FragmentZipdabangRecipeBinding
import com.example.umc_zipdabang.config.src.main.Jip.src.main.AssetLoader
import com.example.umc_zipdabang.config.src.main.Jip.src.main.MainActivity
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.Token
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.AllRecipesData
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.CategoriesData
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.CoffeeRecipesData
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.CategoriesRVAdapter
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.CoffeeLoadingRVAdapter
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.LoadingRVAdapter
import com.example.umc_zipdabang.config.src.main.SocialLogin.InitialActivity
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Runnable

class ZipdabangRecipeFragment: Fragment() {
    private lateinit var viewBinding: FragmentZipdabangRecipeBinding

    lateinit var adapter: LoadingRVAdapter
    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    val allRecipesList: ArrayList<AllRecipesData> = arrayListOf()
    private lateinit var layoutManager: GridLayoutManager
    var isLoading = false
    var grid = 2
    private var clickedRecipeId: Int? = null



    lateinit var mainActivity: HomeMainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as HomeMainActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentZipdabangRecipeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeRetrofit = Retrofit.Builder()
            .baseUrl("http://zipdabang.store:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val recipeService = recipeRetrofit.create(RecipeService::class.java)
//        setData()
//        initAdapter()
//        initScrollListener()
        val tokenDb = TokenDatabase.getTokenDatabase(requireContext())

        val goToLogin = Intent(requireContext(), InitialActivity::class.java)


        layoutManager = GridLayoutManager(requireContext(), 2)

        val categoriesList: ArrayList<CategoriesData> = arrayListOf()
        val categoriesRVAdapter = CategoriesRVAdapter(requireContext(), categoriesList)

        // 카테고리
        categoriesList.apply {
            // add(CategoriesData(사진, 카테고리명)
            add(
                CategoriesData(
                    "https://user-images.githubusercontent.com/101035437/215327945-2cf69a9c-a97a-46cd-8a06-298ff44b1813.png",
                    "커피"
                )
            )
            add(
                CategoriesData(
                    "https://user-images.githubusercontent.com/101035437/215328104-097040ea-7d75-437e-9f64-57e599c5f2c7.png",
                    "Beverage"
                )
            )
            add(
                CategoriesData(
                    "https://user-images.githubusercontent.com/101035437/215327987-3adfd999-b5ea-494d-892b-544580e8c2ab.png",
                    "티"
                )
            )
            add(
                CategoriesData(
                    "https://user-images.githubusercontent.com/101035437/215328029-7658292d-2e08-43ea-8e9a-3baae727dfcc.png",
                    "에이드"
                )
            )
            add(
                CategoriesData(
                    "https://user-images.githubusercontent.com/101035437/215328059-b0d1e4b5-08a1-4867-97c2-571dfe0ed58d.png",
                    "스무디/주스"
                )
            )
            add(
                CategoriesData(
                    "https://user-images.githubusercontent.com/101035437/215328082-98084379-b54f-43fc-9617-0a38f55ab9a6.png",
                    "건강음료"
                )
            )
        }

        viewBinding.rvZipdabangRecipeCategories.layoutManager =
            GridLayoutManager(requireContext(), 3)
        viewBinding.rvZipdabangRecipeCategories.adapter = categoriesRVAdapter

        GlobalScope.launch(Dispatchers.IO) {
            val token: Token = tokenDb.tokenDao().getToken()
            if (token.token == "") {
                startActivity(goToLogin)
            }
            val tokenNum = token.token
            Log.d("토큰 넘버", "${tokenNum}")
//            recipeService.getAllZipdabangRecipes(tokenNum).enqueue(object : Callback<ZipdabangRecipesAll> {
//                override fun onResponse(
//                    call: Call<ZipdabangRecipesAll>,
//                    response: Response<ZipdabangRecipesAll>
//                ) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onFailure(call: Call<ZipdabangRecipesAll>, t: Throwable) {
//                    TODO("Not yet implemented")
//                }
//
//            })

            recipeService.getAllZipdabangRecipes(tokenNum).enqueue(object : Callback<ZipdabangRecipesAll> {
                override fun onResponse(
                    call: Call<ZipdabangRecipesAll>,
                    response: Response<ZipdabangRecipesAll>
                ) {
                    val result = response.body()
                    Log.d("집다방 레시피 초기 Get 성공", "${result}")
                    var firstResultArray = arrayListOf<RecipeInfoAll?>()
                    for (i in 0 until result?.data!!.size) {
                        val firstResult = result?.data?.get(i)
                        firstResultArray.add(firstResult)
                        Log.d("첫번째 배열", "${firstResultArray}")
                    }

                    var firstResultIdArray = arrayListOf<Int?>()
                    var firstResultNameArray = arrayListOf<String?>()
                    var firstResultImgUrlArray = ArrayList<String?>()
                    var firstResultLikesArray = ArrayList<Int?>()

                    for (i in 0 until firstResultArray.size) {
                        firstResultIdArray.add(firstResultArray[i]?.id)
                        firstResultNameArray.add(firstResultArray[i]?.name)
                        firstResultImgUrlArray.add(firstResultArray[i]?.imageUrl)
                        firstResultLikesArray.add(firstResultArray[i]?.likes)
                        Log.d("${i}번째 아이디", "${firstResultArray[i]?.id}")
                        Log.d("${i}번째 이름", "${firstResultArray[i]?.name}")
                        Log.d("${i}번째 이미지", "${firstResultArray[i]?.imageUrl}")
                        Log.d("${i}번째 좋아요", "${firstResultArray[i]?.likes}")
                        allRecipesList.add(
                            AllRecipesData(
                                firstResultArray[i]?.imageUrl,
                                firstResultArray[i]?.name,
                                firstResultArray[i]?.likes
                            )
                        )
                    }
                    Log.d("Id 목록", "${firstResultIdArray}")
                    adapter = LoadingRVAdapter(activity as HomeMainActivity, allRecipesList, firstResultIdArray)
                    viewBinding.rvZipdabangRecipeAll.setLayoutManager(layoutManager)
                    viewBinding.rvZipdabangRecipeAll.setAdapter(adapter)
                    layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {

                            if (position == 0) {

                                return 1


                            } else if ((position % 12 == 0) && position == (allRecipesList.size - 1)) {

                                return 2
                            } else {

                                return 1
                            }

                        }
                    })

                    viewBinding.rvZipdabangRecipeAll.setOnScrollListener(object : RecyclerView.OnScrollListener() {

                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            if (!isLoading) {
                                if (viewBinding.rvZipdabangRecipeAll.layoutManager != null && (viewBinding.rvZipdabangRecipeAll.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (allRecipesList.size - 1)) {
                                    //리스트 마지막o
//                                    moreItems()

                                    val runnable = Runnable {

                                        allRecipesList.add(AllRecipesData(null, null, null))
                                        Log.d("insert before", "msg")
                                        adapter.notifyItemInserted(allRecipesList.size - 1)

                                    }

                                    viewBinding.rvZipdabangRecipeAll.post(runnable)

                                    GlobalScope.launch {
                                        delay(2000)
                                        withContext(Dispatchers.Main) {
                                            allRecipesList.removeAt(allRecipesList.size - 1)
                                            val scrollToPosition = allRecipesList.size
                                            adapter.notifyItemRemoved(scrollToPosition)
                                            recipeService.getAllZipdabangRecipesScrolled(tokenNum, firstResultIdArray.get(firstResultIdArray.size-1)).enqueue(object: Callback<ZipdabangRecipesAll> {
                                                override fun onResponse(
                                                    call: Call<ZipdabangRecipesAll>,
                                                    response: Response<ZipdabangRecipesAll>
                                                ) {
                                                    var moreResult = response.body()
                                                    firstResultArray = ArrayList<RecipeInfoAll?>()
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
                                                            allRecipesList.add(
                                                                AllRecipesData(
                                                                    firstResultArray[i]?.imageUrl,
                                                                    firstResultArray[i]?.name,
                                                                    firstResultArray[i]?.likes
                                                                )
                                                            )
                                                            Log.d("아이디 배열 결과", "${firstResultIdArray}")
                                                            adapter.notifyDataSetChanged()
                                                            isLoading = false
                                                        }
                                                    }

                                                }

                                                override fun onFailure(
                                                    call: Call<ZipdabangRecipesAll>,
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
                }

                override fun onFailure(call: Call<ZipdabangRecipesAll>, t: Throwable) {
                    Log.d("전체 카테고리 레시피 Get", "실패")
                }

            })
        }

//
//        val allRecipesList: ArrayList<AllRecipesData> = arrayListOf()
//        allRecipesList.apply {
//            //add(AllRecipesData(사진, 음료명, 좋아요 수)
//            add(AllRecipesData("https://user-images.githubusercontent.com/101035437/212458353-a0e2e377-03d3-4be1-b5e1-d4e234c086b6.png", "sik-k", 150))
//            add(AllRecipesData("https://user-images.githubusercontent.com/101035437/212458353-a0e2e377-03d3-4be1-b5e1-d4e234c086b6.png", "식혜를 마시는데 글자수가 20자정도는 들어가야지", 150))
//            add(AllRecipesData("https://user-images.githubusercontent.com/101035437/212458353-a0e2e377-03d3-4be1-b5e1-d4e234c086b6.png", "식혜를 마시는데 글자수가 20자정도는 들어가야지", 150))
//            add(AllRecipesData("https://user-images.githubusercontent.com/101035437/212458353-a0e2e377-03d3-4be1-b5e1-d4e234c086b6.png", "식혜를 마시는데 글자수가 20자정도는 들어가야지", 150))
//            add(AllRecipesData("https://user-images.githubusercontent.com/101035437/212458353-a0e2e377-03d3-4be1-b5e1-d4e234c086b6.png", "식혜를 마시는데 글자수가 20자정도는 들어가야지", 150))
//            add(AllRecipesData("https://user-images.githubusercontent.com/101035437/212458353-a0e2e377-03d3-4be1-b5e1-d4e234c086b6.png", "식혜를 마시는데 글자수가 20자정도는 들어가야지", 150))
//            add(AllRecipesData("https://user-images.githubusercontent.com/101035437/212458353-a0e2e377-03d3-4be1-b5e1-d4e234c086b6.png", "식혜를 마시는데 글자수가 20자정도는 들어가야지", 150))
//
//        }
//
//        val allRecipesRVAdapter = AllRecipesRVAdapter(allRecipesList)
//
//        viewBinding.rvZipdabangRecipeAll.adapter = allRecipesRVAdapter
//        viewBinding.rvZipdabangRecipeAll.layoutManager = GridLayoutManager(requireContext(), 2)

    }

//    private fun setData() {
//        allRecipesList.add(
//            AllRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        allRecipesList.add(
//            AllRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                1233
//            )
//        )
//        allRecipesList.add(
//            AllRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                124
//            )
//        )
//        allRecipesList.add(
//            AllRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                127
//            )
//        )
//        allRecipesList.add(
//            AllRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                12
//            )
//        )
//        allRecipesList.add(
//            AllRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                1233
//            )
//        )
//        allRecipesList.add(
//            AllRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                124
//            )
//        )
//        allRecipesList.add(
//            AllRecipesData(
//                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                127
//            )
//        )
//
//
//    }
//
//    private fun initAdapter() {
////        viewbinding = ActivityMainBinding.inflate(layoutInflater)
//        adapter = LoadingRVAdapter(activity as HomeMainActivity, allRecipesList)
//        val layoutManager = GridLayoutManager(activity as HomeMainActivity, 2)
//        viewBinding.rvZipdabangRecipeAll.setLayoutManager(layoutManager)
//        viewBinding.rvZipdabangRecipeAll.setAdapter(adapter)
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
//                else if ((position % 8 == 0) && position == (allRecipesList.size-1))
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
//        viewBinding.rvZipdabangRecipeAll.setOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (!isLoading) {
//                    if (viewBinding.rvZipdabangRecipeAll.layoutManager != null && (viewBinding.rvZipdabangRecipeAll.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (allRecipesList.size - 1)) {
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
//    private fun moreItems() {
//        val runnable = Runnable {
//
//            allRecipesList.add(AllRecipesData(null, null, null))
//            Log.d("insert before", "msg")
//            adapter.notifyItemInserted(allRecipesList.size - 1)
//
//        }
//        viewBinding.rvZipdabangRecipeAll.post(runnable)
//
//        CoroutineScope(mainDispatcher).launch {
//            delay(2000)
//            val runnable2 = Runnable {
//
//                allRecipesList.removeAt(allRecipesList.size - 1)
//                val scrollToPosition = allRecipesList.size
//                adapter.notifyItemRemoved(scrollToPosition)
//
//
//                allRecipesList.add(
//                    AllRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                allRecipesList.add(
//                    AllRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                allRecipesList.add(
//                    AllRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                allRecipesList.add(
//                    AllRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                allRecipesList.add(
//                    AllRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                allRecipesList.add(
//                    AllRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                allRecipesList.add(
//                    AllRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//                allRecipesList.add(
//                    AllRecipesData(
//                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
//                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
//                        12
//                    )
//                )
//
//                adapter.notifyDataSetChanged()
//                isLoading = false
//
//
//
//
//            }
//            runnable2.run()
//
//        }
//    }

    // 무한스크롤 함수들




}