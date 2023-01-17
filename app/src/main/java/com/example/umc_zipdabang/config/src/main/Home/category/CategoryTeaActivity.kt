package com.example.umc_zipdabang.config.src.main.Home.category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.config.src.main.Home.Scrap.My_Scrap
import com.example.umc_zipdabang.databinding.ActivityCategoryBinding

class CategoryTeaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var scraps: ArrayList<My_Scrap> = arrayListOf(

            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12
            ),
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                123
            ),
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                125
            ),
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                130
            ),
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                124
            ),
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                1222
            ),
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                1211
            ),
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                122
            ),
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                900
            ),
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                12020
            ),
            My_Scrap(
                "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                "어르신도 좋아하실만한 담백한 블루베리 요거트",
                14
            ),

            )

        binding.categoryRv.layoutManager = GridLayoutManager(this, 2)
        val adapter = CategoryTeaAdapter(this, scraps)
        binding.categoryRv.adapter = adapter

        adapter.notifyDataSetChanged()
        binding.tvCategory.text="티"

        binding.myscrapIvBack.setOnClickListener {
            onBackPressed()
        }


    }
}