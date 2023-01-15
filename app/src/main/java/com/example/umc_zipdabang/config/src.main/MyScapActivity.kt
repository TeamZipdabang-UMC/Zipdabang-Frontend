package com.example.umc_zipdabang.config.src.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.databinding.ActivityMainBinding
import com.example.umc_zipdabang.databinding.ActivityMyscrapBinding

class MyScapActivity : AppCompatActivity() {


    private var scraps: ArrayList<My_Scrap> = arrayListOf(
        My_Scrap(
            "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
            "어르신도 좋아하실만한 담백한 블루베리 요거트",
            1
        ),
        My_Scrap(
            "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
            "어르신도 좋아하실만한 담백한 블루베리 요거트",
            12
        ) ,
        My_Scrap(
            "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
            "어르신도 좋아하실만한 담백한 블루베리 요거트",
            15
        ),
        My_Scrap(
            "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
            "어르신도 좋아하실만한 담백한 블루베리 요거트",
            19
        ),
        My_Scrap(
            "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
            "어르신도 좋아하실만한 담백한 블루베리 요거트",
            100
        ),
        My_Scrap(
            "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
            "어르신도 좋아하실만한 담백한 블루베리 요거트",
            1200
        )
    )



    private lateinit var binding: ActivityMyscrapBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyscrapBinding.inflate(layoutInflater)

        binding.myscrapRv.layoutManager=
            GridLayoutManager(this,2)
        val adapter=MyScrapAdapter(this,scraps)
         binding.myscrapRv.adapter=adapter


        binding.myscrapIvBack.setOnClickListener{
            onBackPressed()
        }


        binding.srapTvEdit.setOnClickListener {

            val intent= Intent(this,EditScrapActivity::class.java)
            intent.putExtra("array",scraps)
            startActivity(intent)




        }






        setContentView(binding.root)
    }

}







