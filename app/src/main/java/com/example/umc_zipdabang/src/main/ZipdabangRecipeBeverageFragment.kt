package com.example.umc_zipdabang.src.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.databinding.FragmentZipdabangRecipeBeverageBinding
import com.example.umc_zipdabang.databinding.FragmentZipdabangRecipeCoffeeBinding

class ZipdabangRecipeBeverageFragment: Fragment() {
    private lateinit var viewBinding: FragmentZipdabangRecipeBeverageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentZipdabangRecipeBeverageBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        viewBinding.rvZipdabangRecipeBeverage.layoutManager = GridLayoutManager(requireContext(), 2)

        viewBinding.toolbarBackarrow.setOnClickListener{
            // 툴바의 뒤로가기 버튼을 눌렀을 때 동작

        }
    }
}