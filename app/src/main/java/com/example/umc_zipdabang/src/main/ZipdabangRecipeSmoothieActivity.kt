package com.example.umc_zipdabang.src.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.*
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.SmoothieRecipesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter.SmoothieRecipesRVAdapter
import kotlinx.coroutines.*
import java.lang.Runnable

class ZipdabangRecipeSmoothieActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeSmoothieBinding

    private var isLoading = false
    var grid = 2
    val smoothieRecipesList: ArrayList<SmoothieRecipesData> = arrayListOf()

    private lateinit var smoothieRecipesRVAdapter: SmoothieRecipesRVAdapter

    // private 맞는지 확인 필요
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeSmoothieBinding.inflate(layoutInflater)
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
        smoothieRecipesRVAdapter = SmoothieRecipesRVAdapter(this, smoothieRecipesList)
        layoutManager = GridLayoutManager(this, 2)
        viewBinding.rvZipdabangRecipeSmoothie.layoutManager = layoutManager
        viewBinding.rvZipdabangRecipeSmoothie.adapter = smoothieRecipesRVAdapter

        layoutManager.setSpanSizeLookup(object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position == 0) {
                    return 1
                }
                else if ((position % 8 == 0) && position == (smoothieRecipesList.size-1)) {
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

                smoothieRecipesRVAdapter.notifyDataSetChanged()
                isLoading = false
            }
            runnable2.run()
        }
    }

}