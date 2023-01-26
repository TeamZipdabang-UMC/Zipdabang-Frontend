package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.CoffeeRecipesData
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.SmoothieRecipesData
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.BeverageLoadingRVAdapter
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.SmoothieLoadingRVAdapter
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeSmoothieBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Runnable

class ZipdabangRecipeSmoothieActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeSmoothieBinding

    private var isLoading = false
    var grid = 2
    val smoothieRecipesList: ArrayList<SmoothieRecipesData> = arrayListOf()

    private lateinit var smoothieRecipesRVAdapter: SmoothieLoadingRVAdapter

    // private 맞는지 확인 필요
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeSmoothieBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val recipeRetrofit = Retrofit.Builder()
            .baseUrl("http://zipdabang.store:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val recipeService = recipeRetrofit.create(RecipeService::class.java)

        val tokenDb = TokenDatabase.getTokenDatabase(this)

        lateinit var firstResult: List<RecipeInfo>

        smoothieRecipesRVAdapter = SmoothieLoadingRVAdapter(this, smoothieRecipesList)
        layoutManager = GridLayoutManager(this, 2)

        GlobalScope.launch(Dispatchers.IO) {
            val token = tokenDb.tokenDao().getToken()
            val tokenNum = token.token
            Log.d("토큰 넘버", "${tokenNum}")
            recipeService.getCategoryRecipes(tokenNum, 5, 0, 1).enqueue(object :
                Callback<ZipdabangRecipes> {
                override fun onResponse(
                    call: Call<ZipdabangRecipes>,
                    response: Response<ZipdabangRecipes>
                ) {
                    val result = response.body()
                    Log.d("스무디 카테고리 레시피 Get 성공", "${result}")
                    val firstResultArray = arrayListOf<RecipeInfo?>()
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
                        smoothieRecipesList.add(
                            SmoothieRecipesData(
                                firstResultArray[i]?.imageUrl,
                                firstResultArray[i]?.name,
                                firstResultArray[i]?.likes
                            )
                        )
                    }

                    viewBinding.rvZipdabangRecipeSmoothie.setLayoutManager(layoutManager)
                    viewBinding.rvZipdabangRecipeSmoothie.setAdapter(smoothieRecipesRVAdapter)
                    layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {

                            if (position == 0)
                            {

                                return 1


                            }
                            else if ((position % 12 == 0) && position == (smoothieRecipesList.size-1))
                            {

                                return 2
                            }
                            else
                            {

                                return 1
                            }

                        }
                    })


//                    setData()
//                    initAdapter()
//                    initScrollListener()

                }

                override fun onFailure(call: Call<ZipdabangRecipes>, t: Throwable) {
                    Log.d("스무디 카테고리 레시피 Get", "실패")
                }
            })
        }

//        setData()
//        initAdapter()
//        initScrollListener()

        viewBinding.toolbarBackarrow.setOnClickListener{
            // 툴바의 뒤로가기 버튼을 눌렀을 때 동작

            viewBinding.toolbarBackarrow.setOnClickListener{
                // 툴바의 뒤로가기 버튼을 눌렀을 때 동작
                finish()
            }
        }
    }


    private fun setData() {
        smoothieRecipesList.add(
            SmoothieRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        smoothieRecipesList.add(
            SmoothieRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        smoothieRecipesList.add(
            SmoothieRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        smoothieRecipesList.add(
            SmoothieRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        smoothieRecipesList.add(
            SmoothieRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        smoothieRecipesList.add(
            SmoothieRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        smoothieRecipesList.add(
            SmoothieRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        smoothieRecipesList.add(
            SmoothieRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        smoothieRecipesList.add(
            SmoothieRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        smoothieRecipesList.add(
            SmoothieRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        smoothieRecipesList.add(
            SmoothieRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        smoothieRecipesList.add(
            SmoothieRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
    }

    private fun initAdapter() {
        smoothieRecipesRVAdapter = SmoothieLoadingRVAdapter(this, smoothieRecipesList)
        layoutManager = GridLayoutManager(this, 2)
        viewBinding.rvZipdabangRecipeSmoothie.layoutManager = layoutManager
        viewBinding.rvZipdabangRecipeSmoothie.adapter = smoothieRecipesRVAdapter

        layoutManager.setSpanSizeLookup(object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) {
                    return 1
                }
                else if ((position % 12 == 0) && position == (smoothieRecipesList.size-1)) {
                    return 2
                }
                else {
                    return 1
                }
            }
        })
    }

    private fun initScrollListener() {
        viewBinding.rvZipdabangRecipeSmoothie.setOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    if (viewBinding.rvZipdabangRecipeSmoothie.layoutManager != null && (viewBinding.rvZipdabangRecipeSmoothie.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (smoothieRecipesList.size-1)) {
                        moreItems()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun moreItems() {
        val runnable = Runnable {
            smoothieRecipesList.add(SmoothieRecipesData(null, null, null))
            Log.d("insert before", "msg")
            smoothieRecipesRVAdapter.notifyItemInserted(smoothieRecipesList.size - 1)

        }
        viewBinding.rvZipdabangRecipeSmoothie.post(runnable)

        CoroutineScope(mainDispatcher).launch {
            delay(2000)
            val runnable2 = Runnable {
                smoothieRecipesList.removeAt(smoothieRecipesList.size-1)
                val scrollToPosition = smoothieRecipesList.size
                smoothieRecipesRVAdapter.notifyItemRemoved(scrollToPosition)

                smoothieRecipesList.add(
                    SmoothieRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                smoothieRecipesList.add(
                    SmoothieRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                smoothieRecipesList.add(
                    SmoothieRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                smoothieRecipesList.add(
                    SmoothieRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                smoothieRecipesList.add(
                    SmoothieRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                smoothieRecipesList.add(
                    SmoothieRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                smoothieRecipesList.add(
                    SmoothieRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                smoothieRecipesList.add(
                    SmoothieRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                smoothieRecipesList.add(
                    SmoothieRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                smoothieRecipesList.add(
                    SmoothieRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                smoothieRecipesList.add(
                    SmoothieRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                smoothieRecipesList.add(
                    SmoothieRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )

                smoothieRecipesRVAdapter.notifyDataSetChanged()
                isLoading = false
            }
            runnable2.run()
        }
    }

}