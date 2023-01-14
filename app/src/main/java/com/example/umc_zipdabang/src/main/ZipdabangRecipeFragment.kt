package com.example.umc_zipdabang.src.main

import android.icu.text.CaseMap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_zipdabang.databinding.FragmentZipdabangRecipeBinding
import org.json.JSONObject

class ZipdabangRecipeFragment: Fragment() {
    private lateinit var viewBinding: FragmentZipdabangRecipeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentZipdabangRecipeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewpager = viewBinding.viewpagerZipdabangRecipeBanner
        val viewpagerIndicator = viewBinding.viewpagerZipdabangRecipeBannerIndicator

        val assetLoader = AssetLoader()
        val zipdabangRecipe = assetLoader.getJsonString(requireContext(), "zipdabang_recipe.json")

        if (!zipdabangRecipe.isNullOrEmpty()) { // 조건 잘 확인할것. Data가 null/empty가 아니어야 파싱하도록.
            val jsonObject = JSONObject(zipdabangRecipe)
            // 그 결과, jsonObject 변수를 이용해, 값을 키로 조회할 수 있게 된다.

            val zipdabangRecipeImageUrl = jsonObject.getJSONObject("zipdabang_recipe_image_url")
            val imageUrl = zipdabangRecipeImageUrl.getString("image_url")

            viewpager.adapter = ZipdabangRecipeBannerAdapter().apply {

            }


            // 결국, .json 형태의 파일을 '문자열'형태로 가져오고,
            // 문자열 형태의 데이터를 다시 JSON Objcet/Array 형태로 변환하여
            // 키를 통해 값을 조회하는 형식으로 데이터를 가져옴.
        }

        val categoriesList: ArrayList<CategoriesData> = arrayListOf()
        categoriesList.apply {
            // add(CategoriesData(사진, 카테고리명)

        }

        val categoriesRVAdapter = CategoriesRVAdapter(categoriesList)

        viewBinding.rvZipdabangRecipeCategories.adapter = categoriesRVAdapter
        viewBinding.rvZipdabangRecipeCategories.layoutManager = GridLayoutManager(requireContext(), 3)


        // viewpager adapter 할당
    }
}