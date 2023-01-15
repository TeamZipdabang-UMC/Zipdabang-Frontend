package com.example.umc_zipdabang.src.main.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_zipdabang.databinding.FragmentZipdabangRecipeDetailBinding
import java.util.zip.Inflater

class ZipdabangRecipeDetailFragment: Fragment() {
    private lateinit var viewBinding: FragmentZipdabangRecipeDetailBinding
    private lateinit var viewPager2: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentZipdabangRecipeDetailBinding.inflate(inflater, container, false)
        return viewBinding.root

        viewPager2 = viewBinding.viewpagerZipdabangRecipeDetailBanner
        viewPager2.adapter = ZipdabangRecipeDetailBannerRVAdapter(getBannerImages())
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun getBannerImages(): ArrayList<String> {
        return arrayListOf<String>(
            "https://user-images.githubusercontent.com/101035437/212522260-de0d1fa8-c6df-4f61-8dee-b4b619e0aef9.png",
            "https://user-images.githubusercontent.com/101035437/212523005-d0f5a792-a6fb-47bb-b249-f3b4418de4d6.png"
        )
    }




}