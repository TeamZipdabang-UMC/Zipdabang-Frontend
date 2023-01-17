package com.example.umc_zipdabang.src.main.recipe

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailBinding
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailSmoothieBinding
import com.example.umc_zipdabang.src.main.ZipdabangRecipeAdeActivity
import com.example.umc_zipdabang.src.main.ZipdabangRecipeSmoothieActivity

class ZipdabangRecipeDetailSmoothieActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeDetailSmoothieBinding
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeDetailSmoothieBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)


        viewBinding.toolbarBackarrow.setOnClickListener {
            finish()
        }
    }
}