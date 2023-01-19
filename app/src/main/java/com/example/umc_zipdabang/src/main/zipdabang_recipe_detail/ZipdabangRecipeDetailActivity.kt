package com.example.umc_zipdabang.src.main.zipdabang_recipe_detail

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailBinding
import com.example.umc_zipdabang.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailCommentActivity
import io.github.muddz.styleabletoast.StyleableToast

class ZipdabangRecipeDetailActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeDetailBinding
    private lateinit var viewPager2: ViewPager2
    // 사용자에 따라 달라짐
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
                showLikeToast()
            } else {
                like = false
                viewBinding.ivZipdabangRecipeLike.setImageResource(R.drawable.ic_heart_unfilled)
                showLikeCancelToast()
            }
        }

        viewBinding.ivZipdabangRecipeScrap.setOnClickListener {
            if (!scrap) {
                scrap = true
                viewBinding.ivZipdabangRecipeScrap.setImageResource(R.drawable.ic_scrap_filled)
                showScrapToast()
            } else {
                scrap = false
                viewBinding.ivZipdabangRecipeScrap.setImageResource(R.drawable.ic_scrap_unfilled)
                showScrapCancelToast()
            }
        }

        viewBinding.tvZipdabangRecipeCommentViewDetail.setOnClickListener {
            val intent = Intent(this, ZipdabangRecipeDetailCommentActivity::class.java)
            startActivity(intent)
        }

        viewBinding.btnZipdabangRecipeWriteComment.setOnClickListener {
            viewBinding.btnZipdabangRecipeWriteComment.visibility = View.INVISIBLE
            viewBinding.layoutWriteComment.visibility = View.VISIBLE
            viewBinding.editTextComment.visibility = View.VISIBLE
            viewBinding.ivUploadComment.visibility = View.VISIBLE
        }

        viewBinding.ivUploadComment.setOnClickListener {
            if (viewBinding.editTextComment.text != null) {
                viewBinding.layoutWriteComment.visibility = View.INVISIBLE
                viewBinding.editTextComment.visibility = View.INVISIBLE
                viewBinding.ivUploadComment.visibility = View.INVISIBLE
                viewBinding.editTextComment.text = null
                viewBinding.btnZipdabangRecipeWriteComment.visibility = View.VISIBLE
            }
        }
    }

    private fun showLikeToast() {

        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.BOTTOM, 0, 0)
            val layout: View = layoutInflater.inflate(R.layout.toast_like_layout, findViewById(R.id.toast_like))
            view = layout
        }.show()
    }

    private fun showLikeCancelToast() {
        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.BOTTOM, 0, 0)
            val layout: View = layoutInflater.inflate(R.layout.toast_like_cancel_layout, findViewById(R.id.toast_like_cancel))
            view = layout
        }.show()
    }

    private fun showScrapToast() {
        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.BOTTOM, 0, 0)
            val layout: View = layoutInflater.inflate(R.layout.toast_scrap_layout, findViewById(R.id.toast_scrap))
            view = layout
        }.show()
    }

    private fun showScrapCancelToast() {
        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.BOTTOM, 0, 0)
            val layout: View = layoutInflater.inflate(R.layout.toast_scrap_cancel_layout, findViewById(R.id.toast_scrap_cancel))
            view = layout
        }.show()
    }


}