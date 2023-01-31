package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailBeverageBinding

class ZipdabangRecipeDetailBeverageActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityZipdabangRecipeDetailBeverageBinding
    private lateinit var viewPager2: ViewPager2
    // 사용자에 따라 달라짐
    private var like: Boolean = false
    private var scrap: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeDetailBeverageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.toolbarBackarrow.setOnClickListener {
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

        //=========================레시피 도전 화면 start=======================

        // 하단의 도전버튼 눌렀을 때의 동작들
        viewBinding.btnChallengeStart.setOnClickListener {
            // 버튼 교체
            viewBinding.btnChallengeStart.visibility = View.INVISIBLE
            viewBinding.btnChallengeComplete.visibility = View.VISIBLE

            // 설명글 교체
            viewBinding.layoutZipdabangRecipeDetailChallengers.visibility = View.INVISIBLE
            viewBinding.tvZipdabangRecipeDetailCurrent.visibility = View.INVISIBLE
            viewBinding.tvZipdabangRecipeDetailChallengeNum.visibility = View.INVISIBLE
            viewBinding.tvZipdabangRecipeDetailChallenge.visibility = View.INVISIBLE

            viewBinding.tvZipdabangRecipeDetailChallenging.visibility = View.VISIBLE
        }

        viewBinding.btnChallengeComplete.setOnClickListener {
            viewBinding.btnChallengeComplete.visibility = View.INVISIBLE
            viewBinding.btnChallengeRestart.visibility = View.VISIBLE

            viewBinding.tvZipdabangRecipeDetailChallenging.visibility = View.INVISIBLE
            viewBinding.tvZipdabangRecipeDetailSucceeded.visibility = View.VISIBLE

            val successDialogView = LayoutInflater.from(this).inflate(R.layout.recipe_success_dialog, null)
            val successDialogBuilder = AlertDialog.Builder(this)
                .setView(successDialogView)

            val successDialog = successDialogBuilder.create()
            successDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            successDialog.window?.setGravity(Gravity.BOTTOM)
            successDialog.window?.attributes?.width = WindowManager.LayoutParams.WRAP_CONTENT
            successDialog.window?.attributes?.height = WindowManager.LayoutParams.WRAP_CONTENT

            successDialog.show()

            val exitButton = successDialogView.findViewById<ImageView>(R.id.iv_exit_popup)
            val commentButton = successDialogView.findViewById<TextView>(R.id.btn_popup_comment)
            val laterButton = successDialogView.findViewById<TextView>(R.id.tv_popup_comment_later)

            exitButton.setOnClickListener {
                // 없애는 작업
                successDialog.dismiss()
            }

            commentButton.setOnClickListener {
                // 없애는 작업
                successDialog.dismiss()

                // 액티비티마다 아래 도착 액티비티 수정 필요!!
                val commentIntent = Intent(this, ZipdabangRecipeDetailCommentActivity::class.java)
                startActivity(commentIntent)
            }

            laterButton.setOnClickListener {
                // 없애는 작업
                successDialog.dismiss()
            }
        }

        viewBinding.btnChallengeRestart.setOnClickListener {
            viewBinding.btnChallengeRestart.visibility = View.INVISIBLE
            viewBinding.btnChallengeComplete.visibility = View.VISIBLE

            // 설명 글 바꾸는 부분
            viewBinding.tvZipdabangRecipeDetailSucceeded.visibility = View.INVISIBLE
            viewBinding.tvZipdabangRecipeDetailChallenging.visibility = View.VISIBLE
        }
        //=========================레시피 도전 화면 end=======================
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
            val layout: View = layoutInflater.inflate(
                R.layout.toast_like_cancel_layout, findViewById(
                    R.id.toast_like_cancel))
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
            val layout: View = layoutInflater.inflate(
                R.layout.toast_scrap_cancel_layout, findViewById(
                    R.id.toast_scrap_cancel))
            view = layout
        }.show()
    }
}