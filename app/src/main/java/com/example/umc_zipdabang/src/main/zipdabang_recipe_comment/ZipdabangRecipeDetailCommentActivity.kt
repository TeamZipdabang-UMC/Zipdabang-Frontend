package com.example.umc_zipdabang.src.main.zipdabang_recipe_comment

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailCommentBinding
import com.example.umc_zipdabang.src.main.decoration.AdapterDecoration
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.Comment
import com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter.RecipeDetailCommentRVAdapter
import java.time.LocalDate
import java.time.LocalTime

class ZipdabangRecipeDetailCommentActivity: AppCompatActivity() {
    lateinit var viewBinding: ActivityZipdabangRecipeDetailCommentBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeDetailCommentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val commentList: ArrayList<Comment> = arrayListOf()
        commentList.apply {
            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머"))
            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣ머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미asdfasdfasdfasdfa낭머"))
            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣ머이ㅏㅁㄴ이ㅏㄻ니asdfasdfasdfasdf아ㅓ미낭머"))
            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어asdfasdfasdfazxcvxzdfaeawdf요ㅗㅇ러ㅣ머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머"))
            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣ머이ㅏㅁㄴ이ㅏㄻ니아ㅓasdfasdvcdawefasdcvadawe미낭머"))
            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣ머이ㅏㅁㄴ이xzcasedfwaefasdㅏㄻ니아ㅓ미낭머"))
            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣ머이ㅏㅁasfdddddasdfacewaefcwcawertㄴ이ㅏㄻ니아ㅓ미낭머"))
            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣ머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미vxzdfaeae낭머"))
            add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", LocalDate.of(2020, 11, 23),LocalTime.of(10, 10), "너무 맛ㄱ잇어요ㅗㅇ러ㅣ머이ㅏㅁaweraqdfavcasdfaqewfㄴ이ㅏㄻ니아ㅓ미낭머"))

        }

        val commentRVAdapter = RecipeDetailCommentRVAdapter(commentList)
        viewBinding.rvZipdabangRecipeDetailComment.adapter = commentRVAdapter
        viewBinding.rvZipdabangRecipeDetailComment.layoutManager = LinearLayoutManager(this)
        viewBinding.ivZipdabangRecipeDetailCommentsBackarrow.setOnClickListener {
            finish()
        }
        viewBinding.rvZipdabangRecipeDetailComment.addItemDecoration(AdapterDecoration())

        // 댓글 업로드 버튼 눌렀을 때
        viewBinding.ivUploadComment.setOnClickListener {
            Toast(this).apply {
                duration = Toast.LENGTH_SHORT
                setGravity(Gravity.BOTTOM, 0, 0)
                val layout: View = layoutInflater.inflate(
                    R.layout.toast_comment_upload_layout, findViewById(
                        R.id.toast_comment_upload))
                view = layout
            }.show()
            viewBinding.editTextComment.text = null
        }

    }
}