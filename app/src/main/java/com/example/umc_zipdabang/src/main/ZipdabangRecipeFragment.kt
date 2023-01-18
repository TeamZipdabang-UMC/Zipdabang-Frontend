package com.example.umc_zipdabang.src.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.databinding.FragmentZipdabangRecipeBinding
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.AllRecipesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.CategoriesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter.AllRecipesRVAdapter
import com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter.CategoriesRVAdapter
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
            add(CategoriesData("https://user-images.githubusercontent.com/101035437/212458099-b80f35b0-9e6f-4f7e-a863-acfa8995e3b1.png", "커피"))
            add(CategoriesData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png", "Beverage"))
            add(CategoriesData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png", "티"))
            add(CategoriesData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png", "에이드"))
            add(CategoriesData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png", "스무디/주스"))
            add(CategoriesData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png", "건강음료"))
        }

        val categoriesRVAdapter = CategoriesRVAdapter(requireContext(), categoriesList)


        viewBinding.rvZipdabangRecipeCategories.layoutManager = GridLayoutManager(requireContext(), 3)
        viewBinding.rvZipdabangRecipeCategories.adapter = categoriesRVAdapter


        val allRecipesList: ArrayList<AllRecipesData> = arrayListOf()
        allRecipesList.apply {
            //add(AllRecipesData(사진, 음료명, 좋아요 수)
            add(AllRecipesData("https://user-images.githubusercontent.com/101035437/212458353-a0e2e377-03d3-4be1-b5e1-d4e234c086b6.png", "sik-k", 150))
            add(AllRecipesData("https://user-images.githubusercontent.com/101035437/212458353-a0e2e377-03d3-4be1-b5e1-d4e234c086b6.png", "식혜를 마시는데 글자수가 20자정도는 들어가야지", 150))
            add(AllRecipesData("https://user-images.githubusercontent.com/101035437/212458353-a0e2e377-03d3-4be1-b5e1-d4e234c086b6.png", "식혜를 마시는데 글자수가 20자정도는 들어가야지", 150))
            add(AllRecipesData("https://user-images.githubusercontent.com/101035437/212458353-a0e2e377-03d3-4be1-b5e1-d4e234c086b6.png", "식혜를 마시는데 글자수가 20자정도는 들어가야지", 150))
            add(AllRecipesData("https://user-images.githubusercontent.com/101035437/212458353-a0e2e377-03d3-4be1-b5e1-d4e234c086b6.png", "식혜를 마시는데 글자수가 20자정도는 들어가야지", 150))
            add(AllRecipesData("https://user-images.githubusercontent.com/101035437/212458353-a0e2e377-03d3-4be1-b5e1-d4e234c086b6.png", "식혜를 마시는데 글자수가 20자정도는 들어가야지", 150))
            add(AllRecipesData("https://user-images.githubusercontent.com/101035437/212458353-a0e2e377-03d3-4be1-b5e1-d4e234c086b6.png", "식혜를 마시는데 글자수가 20자정도는 들어가야지", 150))

        }

        val allRecipesRVAdapter = AllRecipesRVAdapter(allRecipesList)

        viewBinding.rvZipdabangRecipeAll.adapter = allRecipesRVAdapter
        viewBinding.rvZipdabangRecipeAll.layoutManager = GridLayoutManager(requireContext(), 2)


    }
}