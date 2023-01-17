package com.example.umc_zipdabang.src.main.recipe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailBeverageBinding
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailBinding
import com.example.umc_zipdabang.src.main.ZipdabangRecipeAdeActivity
import com.example.umc_zipdabang.src.main.ZipdabangRecipeBeverageActivity

class ZipdabangRecipeDetailBeverageActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeDetailBeverageBinding
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeDetailBeverageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.toolbarBackarrow.setOnClickListener {
            finish()
        }
    }
}