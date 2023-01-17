package com.example.umc_zipdabang.src.main.recipe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailBinding

class ZipdabangRecipeDetailActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeDetailBinding
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)


        viewBinding.toolbarBackarrow.setOnClickListener{
            // 툴바의 뒤로가기 버튼을 눌렀을 때 동작

            finish()
        }
    }
}