package com.UMC.zipdabang.config.src.main.Our

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.UMC.zipdabang.R
import com.UMC.zipdabang.databinding.FragmentOurRecipeBinding
import com.UMC.zipdabang.config.src.main.Home.HomeMainActivity
import com.UMC.zipdabang.config.src.main.Jip.src.main.roomDb.Token
import com.UMC.zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.*
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.AllRecipesData
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.CategoriesData
import com.UMC.zipdabang.config.src.main.SocialLogin.InitialActivity
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Runnable

class OurRecipeFragment: Fragment() {
    private lateinit var viewBinding: FragmentOurRecipeBinding

    lateinit var adapter: LoadingOurRVAdapter
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
        viewBinding = FragmentOurRecipeBinding.inflate(inflater, container, false)
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
        val categoriesRVAdapter = CategoriesOurRVAdapter(requireContext(), categoriesList)

        // 카테고리
        categoriesList.apply {
            // add(CategoriesData(사진, 카테고리명)
            add(
                CategoriesData(
                    R.drawable.coffee_low,
                    "커피"
                )
            )
            add(
                CategoriesData(
                    R.drawable.beverage_low,
                    "Beverage"
                )
            )
            add(
                CategoriesData(
                    R.drawable.tea_low,
                    "티"
                )
            )
            add(
                CategoriesData(
                    R.drawable.ade_low,
                    "에이드"
                )
            )
            add(
                CategoriesData(
                    R.drawable.smoothie_low,
                    "스무디/주스"
                )
            )
            add(
                CategoriesData(
                    R.drawable.wellbeing_low,
                    "건강음료"
                )
            )
        }

        viewBinding.rvOurRecipeCategories.layoutManager =
            GridLayoutManager(requireContext(), 3)
        viewBinding.rvOurRecipeCategories.adapter = categoriesRVAdapter

//        GlobalScope.launch(Dispatchers.IO) {
//            val token: Token = tokenDb.tokenDao().getToken()
//            if (token.token == "") {
//                startActivity(goToLogin)
//            }
//            val tokenNum = token.token
//            Log.d("토큰 넘버", "${tokenNum}")
//
//            recipeService.getAllOurRecipes(tokenNum).enqueue(object : Callback<ZipdabangRecipesAll> {
//                override fun onResponse(
//                    call: Call<ZipdabangRecipesAll>,
//                    response: Response<ZipdabangRecipesAll>
//                ) {
//                    val result = response.body()
//
//                    var firstResultIdArray = arrayListOf<Int?>()
//                    var firstResultNameArray = arrayListOf<String?>()
//                    var firstResultImgUrlArray = ArrayList<String?>()
//                    var firstResultLikesArray = ArrayList<Int?>()
//
//                    Log.d("우리들의 레시피 초기 Get 성공", "${result}")
//                    var firstResultArray = arrayListOf<RecipeInfoAll?>()
//
//                    for (i in 0 until result?.data!!.size) {
//                        val firstResult = result?.data?.get(i)
//                        firstResultArray.add(firstResult)
//                        Log.d("첫번째 배열", "${firstResultArray}")
//                    }
//
//                    if (firstResultArray != null) {
//                        for (i in 0 until firstResultArray.size) {
//                            firstResultIdArray.add(firstResultArray[i]?.id)
//                            firstResultNameArray.add(firstResultArray[i]?.name)
//                            firstResultImgUrlArray.add(firstResultArray[i]?.imageUrl)
//                            firstResultLikesArray.add(firstResultArray[i]?.likes)
//                            Log.d("${i}번째 아이디", "${firstResultArray[i]?.id}")
//                            Log.d("${i}번째 이름", "${firstResultArray[i]?.name}")
//                            Log.d("${i}번째 이미지", "${firstResultArray[i]?.imageUrl}")
//                            Log.d("${i}번째 좋아요", "${firstResultArray[i]?.likes}")
//                            allRecipesList.add(
//                                AllRecipesData(
//                                    firstResultArray[i]?.imageUrl,
//                                    firstResultArray[i]?.name,
//                                    firstResultArray[i]?.likes
//                                )
//                            )
//                        }
//                    }
//
//
//                    Log.d("Id 목록", "${firstResultIdArray}")
//                    adapter = LoadingOurRVAdapter(
//                        activity as HomeMainActivity,
//                        allRecipesList,
//                        firstResultIdArray
//                    )
//                    viewBinding.rvOurRecipeAll.setLayoutManager(layoutManager)
//                    viewBinding.rvOurRecipeAll.setAdapter(adapter)
//                    layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
//                        override fun getSpanSize(position: Int): Int {
//
//                            if (position == 0) {
//
//                                return 1
//
//
//                            } else if ((position % 12 == 0) && position == (allRecipesList.size - 1)) {
//
//                                return 2
//                            } else {
//
//                                return 1
//                            }
//
//                        }
//                    })
//
//                    viewBinding.rvOurRecipeAll.setOnScrollListener(object :
//                        RecyclerView.OnScrollListener() {
//
//                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                            super.onScrolled(recyclerView, dx, dy)
//                            if (!isLoading) {
//                                if (viewBinding.rvOurRecipeAll.layoutManager != null && (viewBinding.rvOurRecipeAll.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (allRecipesList.size - 1)) {
//                                    //리스트 마지막o
////                                    moreItems()
//
//                                    val runnable = Runnable {
//
//                                        allRecipesList.add(AllRecipesData(null, null, null))
//                                        Log.d("insert before", "msg")
//                                        adapter.notifyItemInserted(allRecipesList.size - 1)
//
//                                    }
//
//                                    viewBinding.rvOurRecipeAll.post(runnable)
//
//                                    GlobalScope.launch {
//                                        delay(2000)
//                                        withContext(Dispatchers.Main) {
//                                            allRecipesList.removeAt(allRecipesList.size - 1)
//                                            val scrollToPosition = allRecipesList.size
//                                            adapter.notifyItemRemoved(scrollToPosition)
//                                            recipeService.getAllUserRecipesScrolled(
//                                                tokenNum,
//                                                firstResultIdArray.get(firstResultIdArray.size - 1)
//                                            ).enqueue(object : Callback<ZipdabangRecipesAll> {
//                                                override fun onResponse(
//                                                    call: Call<ZipdabangRecipesAll>,
//                                                    response: Response<ZipdabangRecipesAll>
//                                                ) {
//                                                    var moreResult = response.body()
//                                                    firstResultArray = ArrayList<RecipeInfoAll?>()
//                                                    Log.d("more result 결과", "${moreResult}")
//
//                                                    if (moreResult != null) {
//                                                        for (i in 0 until moreResult?.data!!.size) {
//                                                            val moreResultData =
//                                                                moreResult?.data?.get(i)
//                                                            firstResultArray.add(moreResultData)
//                                                        }
//
//                                                        Log.d(
//                                                            "last",
//                                                            "${firstResultIdArray.get(firstResultIdArray.size - 1)}"
//                                                        )
//                                                        Log.d("다음 배열", "${firstResultArray}")
//
//                                                        for (i in 0 until moreResult?.data!!.size) {
//                                                            firstResultIdArray.add(firstResultArray[i]?.id)
//                                                            firstResultNameArray.add(firstResultArray[i]?.name)
//                                                            firstResultImgUrlArray.add(firstResultArray[i]?.imageUrl)
//                                                            firstResultLikesArray.add(firstResultArray[i]?.likes)
//                                                            Log.d(
//                                                                "${i}번째 아이디",
//                                                                "${firstResultArray[i]?.id}"
//                                                            )
//                                                            Log.d(
//                                                                "${i}번째 이름",
//                                                                "${firstResultArray[i]?.name}"
//                                                            )
//                                                            Log.d(
//                                                                "${i}번째 이미지",
//                                                                "${firstResultArray[i]?.imageUrl}"
//                                                            )
//                                                            Log.d(
//                                                                "${i}번째 좋아요",
//                                                                "${firstResultArray[i]?.likes}"
//                                                            )
//                                                            allRecipesList.add(
//                                                                AllRecipesData(
//                                                                    firstResultArray[i]?.imageUrl,
//                                                                    firstResultArray[i]?.name,
//                                                                    firstResultArray[i]?.likes
//                                                                )
//                                                            )
//                                                            Log.d("아이디 배열 결과", "${firstResultIdArray}")
//                                                            adapter.notifyDataSetChanged()
//                                                            isLoading = false
//                                                        }
//                                                    }
//                                                }
//
//                                                override fun onFailure(
//                                                    call: Call<ZipdabangRecipesAll>,
//                                                    t: Throwable
//                                                ) {
//                                                    Log.d("추가 레시피 불러오기", "실패")
//                                                }
//
//                                            })
//
//                                        }
//                                    }
//
//                                    isLoading = true
//
//                                }
//                            }
//                        }
//                    })
//                }
//
//                override fun onFailure(call: Call<ZipdabangRecipesAll>, t: Throwable) {
//                    Log.d("전체 카테고리 레시피 Get", "실패")
//                }
//
//            })
//        }


    }

    override fun onResume() {
        super.onResume()

        val allRecipesList: ArrayList<AllRecipesData> = arrayListOf()

        val recipeRetrofit = Retrofit.Builder()
            .baseUrl("http://zipdabang.store:3000")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val recipeService = recipeRetrofit.create(RecipeService::class.java)

        val tokenDb = TokenDatabase.getTokenDatabase(requireContext())

        val goToLogin = Intent(requireContext(), InitialActivity::class.java)


        layoutManager = GridLayoutManager(requireContext(), 2)

        GlobalScope.launch(Dispatchers.IO) {
            val token: Token = tokenDb.tokenDao().getToken()
            if (token.token == "") {
                startActivity(goToLogin)
            }
            val tokenNum = token.token
            Log.d("토큰 넘버", "${tokenNum}")

            recipeService.getAllOurRecipes(tokenNum).enqueue(object : Callback<ZipdabangRecipesAll> {
                override fun onResponse(
                    call: Call<ZipdabangRecipesAll>,
                    response: Response<ZipdabangRecipesAll>
                ) {
                    val result = response.body()

                    var firstResultIdArray = arrayListOf<Int?>()
                    var firstResultNameArray = arrayListOf<String?>()
                    var firstResultImgUrlArray = ArrayList<String?>()
                    var firstResultLikesArray = ArrayList<Int?>()

                    Log.d("우리들의 레시피 초기 Get 성공", "${result}")
                    var firstResultArray = arrayListOf<RecipeInfoAll?>()

                    for (i in 0 until result?.data!!.size) {
                        val firstResult = result?.data?.get(i)
                        firstResultArray.add(firstResult)
                        Log.d("첫번째 배열", "${firstResultArray}")
                    }

                    if (firstResultArray != null) {
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
                    }


                    Log.d("Id 목록", "${firstResultIdArray}")
                    adapter = LoadingOurRVAdapter(
                        activity as HomeMainActivity,
                        allRecipesList,
                        firstResultIdArray
                    )
                    viewBinding.rvOurRecipeAll.setLayoutManager(layoutManager)
                    viewBinding.rvOurRecipeAll.setAdapter(adapter)
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

                    viewBinding.rvOurRecipeAll.setOnScrollListener(object :
                        RecyclerView.OnScrollListener() {

                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            if (!isLoading) {
                                if (viewBinding.rvOurRecipeAll.layoutManager != null && (viewBinding.rvOurRecipeAll.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (allRecipesList.size - 1)) {
                                    //리스트 마지막o
//                                    moreItems()

                                    val runnable = Runnable {

                                        allRecipesList.add(AllRecipesData(null, null, null))
                                        Log.d("insert before", "msg")
                                        adapter.notifyItemInserted(allRecipesList.size - 1)

                                    }

                                    viewBinding.rvOurRecipeAll.post(runnable)

                                    GlobalScope.launch {
                                        delay(2000)
                                        withContext(Dispatchers.Main) {
                                            allRecipesList.removeAt(allRecipesList.size - 1)
                                            val scrollToPosition = allRecipesList.size
                                            adapter.notifyItemRemoved(scrollToPosition)
                                            recipeService.getAllUserRecipesScrolled(
                                                tokenNum,
                                                firstResultIdArray.get(firstResultIdArray.size - 1)
                                            ).enqueue(object : Callback<ZipdabangRecipesAll> {
                                                override fun onResponse(
                                                    call: Call<ZipdabangRecipesAll>,
                                                    response: Response<ZipdabangRecipesAll>
                                                ) {
                                                    var moreResult = response.body()
                                                    firstResultArray = ArrayList<RecipeInfoAll?>()
                                                    Log.d("more result 결과", "${moreResult}")

                                                    if (moreResult != null) {
                                                        for (i in 0 until moreResult?.data!!.size) {
                                                            val moreResultData =
                                                                moreResult?.data?.get(i)
                                                            firstResultArray.add(moreResultData)
                                                        }

                                                        Log.d(
                                                            "last",
                                                            "${firstResultIdArray.get(firstResultIdArray.size - 1)}"
                                                        )
                                                        Log.d("다음 배열", "${firstResultArray}")

                                                        for (i in 0 until moreResult?.data!!.size) {
                                                            firstResultIdArray.add(firstResultArray[i]?.id)
                                                            firstResultNameArray.add(firstResultArray[i]?.name)
                                                            firstResultImgUrlArray.add(firstResultArray[i]?.imageUrl)
                                                            firstResultLikesArray.add(firstResultArray[i]?.likes)
                                                            Log.d(
                                                                "${i}번째 아이디",
                                                                "${firstResultArray[i]?.id}"
                                                            )
                                                            Log.d(
                                                                "${i}번째 이름",
                                                                "${firstResultArray[i]?.name}"
                                                            )
                                                            Log.d(
                                                                "${i}번째 이미지",
                                                                "${firstResultArray[i]?.imageUrl}"
                                                            )
                                                            Log.d(
                                                                "${i}번째 좋아요",
                                                                "${firstResultArray[i]?.likes}"
                                                            )
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

    }

}