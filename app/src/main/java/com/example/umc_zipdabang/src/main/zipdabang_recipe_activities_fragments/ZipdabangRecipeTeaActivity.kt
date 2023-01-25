package com.example.umc_zipdabang.src.main.zipdabang_recipe_activities_fragments

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeTeaBinding
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.TeaRecipesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter.TeaLoadingRVAdapter
import kotlinx.coroutines.*
import java.lang.Runnable

class ZipdabangRecipeTeaActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeTeaBinding

    private var isLoading = false
    var grid = 2
    val teaRecipesList: ArrayList<TeaRecipesData> = arrayListOf()

    private lateinit var teaRecipesRVAdapter: TeaLoadingRVAdapter

    // private 맞는지 확인 필요
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeTeaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)


        setData()
        initAdapter()
        initScrollListener()

        viewBinding.toolbarBackarrow.setOnClickListener{
            // 툴바의 뒤로가기 버튼을 눌렀을 때 동작

            viewBinding.toolbarBackarrow.setOnClickListener{
                // 툴바의 뒤로가기 버튼을 눌렀을 때 동작
                finish()
            }
        }
    }


    private fun setData() {
        teaRecipesList.add(
            TeaRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        teaRecipesList.add(
            TeaRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        teaRecipesList.add(
            TeaRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        teaRecipesList.add(
            TeaRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        teaRecipesList.add(
            TeaRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        teaRecipesList.add(
            TeaRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        teaRecipesList.add(
            TeaRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        teaRecipesList.add(
            TeaRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
    }

    private fun initAdapter() {
        teaRecipesRVAdapter = TeaLoadingRVAdapter(this, teaRecipesList)
        layoutManager = GridLayoutManager(this, 2)
        viewBinding.rvZipdabangRecipeTea.layoutManager = layoutManager
        viewBinding.rvZipdabangRecipeTea.adapter = teaRecipesRVAdapter

        layoutManager.setSpanSizeLookup(object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) {
                    return 1
                }
                else if ((position % 8 == 0) && position == (teaRecipesList.size-1)) {
                    return 2
                }
                else {
                    return 1
                }
            }
        })
    }

    private fun initScrollListener() {
        viewBinding.rvZipdabangRecipeTea.setOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    if (viewBinding.rvZipdabangRecipeTea.layoutManager != null && (viewBinding.rvZipdabangRecipeTea.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (teaRecipesList.size-1)) {
                        moreItems()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun moreItems() {
        val runnable = Runnable {
            teaRecipesList.add(TeaRecipesData(null, null, null))
            Log.d("insert before", "msg")
            teaRecipesRVAdapter.notifyItemInserted(teaRecipesList.size - 1)

        }
        viewBinding.rvZipdabangRecipeTea.post(runnable)

        CoroutineScope(mainDispatcher).launch {
            delay(2000)
            val runnable2 = Runnable {
                teaRecipesList.removeAt(teaRecipesList.size-1)
                val scrollToPosition = teaRecipesList.size
                teaRecipesRVAdapter.notifyItemRemoved(scrollToPosition)

                teaRecipesList.add(
                    TeaRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                teaRecipesList.add(
                    TeaRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                teaRecipesList.add(
                    TeaRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                teaRecipesList.add(
                    TeaRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                teaRecipesList.add(
                    TeaRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                teaRecipesList.add(
                    TeaRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                teaRecipesList.add(
                    TeaRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                teaRecipesList.add(
                    TeaRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )

                teaRecipesRVAdapter.notifyDataSetChanged()
                isLoading = false
            }
            runnable2.run()
        }
    }
}