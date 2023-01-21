package com.example.umc_zipdabang.config.src.main.Home.category

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.config.src.main.Home.Scrap.My_Scrap
import com.example.umc_zipdabang.databinding.ActivityCategory1Binding
import com.example.umc_zipdabang.databinding.ActivityCategoryBinding
import kotlinx.coroutines.*
import java.lang.Runnable

class CategoryHealthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategory1Binding
    private var isLoading = false
    private var scraps: ArrayList<My_Scrap> = arrayListOf()
    private lateinit var adapter: CategoryHealthAdapter
    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    private lateinit var layoutManager: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setData()
        initAdapter()
        initScrollListener()
        binding.tvCategory.text="건강음료"

        binding.myscrapIvBack.setOnClickListener{

            onBackPressed()

        }

    }

    private fun setData() {
        scraps.add(
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )
        scraps.add(
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                1233
            )
        )
        scraps.add(
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                124
            )
        )
        scraps.add(
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                127
            )
        )
        scraps.add(
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12222
            )
        )
        scraps.add(
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                1233
            )
        )
        scraps.add(
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                121
            )
        )
        scraps.add(
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            )
        )


    }


    private fun initAdapter() {
        binding = ActivityCategory1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = CategoryHealthAdapter(this, scraps)
        layoutManager = GridLayoutManager(this, 2)
        binding.categoryRv.setLayoutManager(layoutManager)
        binding.categoryRv.setAdapter(adapter)

        layoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {

                if (position == 0)
                {

                    return 1


                }
                else if ((position % 8 == 0) && position == (scraps.size-1))
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

        binding.categoryRv.setOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    if (binding.categoryRv.layoutManager != null && (binding.categoryRv.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (scraps.size - 1)) {
                        //리스트 마지막o
                        moreItems()
                        isLoading = true

                    }
                }
            }
        })
    }


    fun moreItems() {
        val runnable = Runnable {

            scraps.add(My_Scrap(null, null, null))

            Log.d("insert before","msg")

            adapter.notifyItemInserted(scraps.size - 1)



        }
        binding.categoryRv.post(runnable)

        CoroutineScope(mainDispatcher).launch {
            delay(2000)
            val runnable2 = Runnable {

                scraps.removeAt(scraps.size - 1)
                val scrollToPosition = scraps.size
                adapter.notifyItemRemoved(scrollToPosition)


                scraps.add(
                    My_Scrap(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                scraps.add(
                    My_Scrap(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                scraps.add(
                    My_Scrap(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                scraps.add(
                    My_Scrap(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                scraps.add(
                    My_Scrap(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                scraps.add(
                    My_Scrap(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                scraps.add(
                    My_Scrap(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                scraps.add(
                    My_Scrap(
                        "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12
                    )
                )
                Log.d("change before","msg")


                adapter.notifyDataSetChanged()
                isLoading = false




            }
            runnable2.run()

        }

    }
}
