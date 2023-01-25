package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.WellbeingRecipesData
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter.WellbeingLoadingRVAdapter
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeWellbeingBinding
import kotlinx.coroutines.*
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


        setData()
        initAdapter()
        initScrollListener()

        viewBinding.toolbarBackarrow.setOnClickListener{
            // 툴바의 뒤로가기 버튼을 눌렀을 때 동작

            finish()
        }
    }

    private fun setData() {
        wellbeingRecipesList.add(
            WellbeingRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        wellbeingRecipesList.add(
            WellbeingRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        wellbeingRecipesList.add(
            WellbeingRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        wellbeingRecipesList.add(
            WellbeingRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        wellbeingRecipesList.add(
            WellbeingRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        wellbeingRecipesList.add(
            WellbeingRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        wellbeingRecipesList.add(
            WellbeingRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        wellbeingRecipesList.add(
            WellbeingRecipesData(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )

    }


    private fun initAdapter() {
        wellbeingRecipesRVAdapter = WellbeingLoadingRVAdapter(this, wellbeingRecipesList)
        layoutManager = GridLayoutManager(this, 2)
        viewBinding.rvZipdabangRecipeWellbeing.setLayoutManager(layoutManager)
        viewBinding.rvZipdabangRecipeWellbeing.setAdapter(wellbeingRecipesRVAdapter)

        layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {

                if (position == 0)
                {

                    return 1


                }
                else if ((position % 8 == 0) && position == (wellbeingRecipesList.size-1))
                {

                    return 2
                }
                else
                {

                    return 1
                }

            }
        })
    }

    private fun initScrollListener() {

        viewBinding.rvZipdabangRecipeWellbeing.setOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    if (viewBinding.rvZipdabangRecipeWellbeing.layoutManager != null && (viewBinding.rvZipdabangRecipeWellbeing.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (wellbeingRecipesList.size - 1)) {
                        //리스트 마지막o
                        moreItems()
                        isLoading = true

                    }
                }
            }
        })
    }


    private fun moreItems() {
        val runnable = Runnable {

            wellbeingRecipesList.add(WellbeingRecipesData(null, null, null))

            Log.d("insert before", "msg")

            wellbeingRecipesRVAdapter.notifyItemInserted(wellbeingRecipesList.size - 1)





        }
        viewBinding.rvZipdabangRecipeWellbeing.post(runnable)

        CoroutineScope(mainDispatcher).launch {
            delay(2000)
            val runnable2 = Runnable {

                wellbeingRecipesList.removeAt(wellbeingRecipesList.size - 1)
                val scrollToPosition = wellbeingRecipesList.size
                wellbeingRecipesRVAdapter.notifyItemRemoved(scrollToPosition)


                wellbeingRecipesList.add(
                    WellbeingRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                wellbeingRecipesList.add(
                    WellbeingRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                wellbeingRecipesList.add(
                    WellbeingRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                wellbeingRecipesList.add(
                    WellbeingRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                wellbeingRecipesList.add(
                    WellbeingRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                wellbeingRecipesList.add(
                    WellbeingRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                wellbeingRecipesList.add(
                    WellbeingRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                wellbeingRecipesList.add(
                    WellbeingRecipesData(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )


                wellbeingRecipesRVAdapter.notifyDataSetChanged()
                isLoading = false

            }
            runnable2.run()
        }
    }
}