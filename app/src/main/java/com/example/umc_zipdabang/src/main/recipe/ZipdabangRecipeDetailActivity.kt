package com.example.umc_zipdabang.src.main.recipe

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailBinding

class ZipdabangRecipeDetailActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeDetailBinding
    private lateinit var viewPager2: ViewPager2
    private var like: Boolean = false
    private var scrap: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)


        viewBinding.toolbarBackarrow.setOnClickListener{
            // 툴바의 뒤로가기 버튼을 눌렀을 때 동작
            finish()
        }

        viewBinding.ivZipdabangRecipeLike.setOnClickListener {
            if (!like) {
                like = true
                viewBinding.ivZipdabangRecipeLike.setImageResource(R.drawable.ic_heart_filled)

            } else {
                like = false
                viewBinding.ivZipdabangRecipeLike.setImageResource(R.drawable.ic_heart_unfilled)
            }
        }

        viewBinding.ivZipdabangRecipeScrap.setOnClickListener {
            if (!scrap) {
                scrap = true
                viewBinding.ivZipdabangRecipeScrap.setImageResource(R.drawable.ic_scrap_filled)
            } else {
                scrap = false
                viewBinding.ivZipdabangRecipeScrap.setImageResource(R.drawable.ic_scrap_unfilled)
            }

        }
    }
}