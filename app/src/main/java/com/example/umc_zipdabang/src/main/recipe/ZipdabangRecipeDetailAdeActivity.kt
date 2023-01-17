package com.example.umc_zipdabang.src.main.recipe

import android.content.Intent
import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailAdeBinding
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailBinding
import com.example.umc_zipdabang.src.main.ZipdabangRecipeAdeActivity

class ZipdabangRecipeDetailAdeActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeDetailAdeBinding
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeDetailAdeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.toolbarBackarrow.setOnClickListener {
            finish()
        }
    }
}