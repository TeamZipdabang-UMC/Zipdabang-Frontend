package com.example.umc_zipdabang.src.main.recipe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailAdeBinding
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailBinding

class ZipdabangRecipeDetailAdeActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeDetailAdeBinding
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeDetailAdeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }
}