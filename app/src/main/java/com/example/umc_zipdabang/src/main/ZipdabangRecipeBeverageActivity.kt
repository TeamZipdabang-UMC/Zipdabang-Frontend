package com.example.umc_zipdabang.src.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeBeverageBinding

class ZipdabangRecipeBeverageActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeBeverageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeBeverageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val beverageRecipesList: ArrayList<BeverageRecipesData> = arrayListOf()
        beverageRecipesList.apply {
            // add(AllRecipesData(사진, 커피명, 좋아요 수)
            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465847-c47c7299-a045-43f1-8a27-4599222aca50.png", "아메리카노", 150))
            add(BeverageRecipesData("https://user-images.githubusercontent.com/101035437/212465911-3fb5bba0-b2d3-4d76-95c1-b043780b5178.png", "카라멜마끼아또", 2000))
        }

        val beverageRecipesRVAdapter = BeverageRecipesRVAdapter(beverageRecipesList)

        viewBinding.rvZipdabangRecipeBeverage.adapter = beverageRecipesRVAdapter
        viewBinding.rvZipdabangRecipeBeverage.layoutManager = GridLayoutManager(this, 2)

        viewBinding.toolbarBackarrow.setOnClickListener{
            // 툴바의 뒤로가기 버튼을 눌렀을 때 동작

        }
    }

}