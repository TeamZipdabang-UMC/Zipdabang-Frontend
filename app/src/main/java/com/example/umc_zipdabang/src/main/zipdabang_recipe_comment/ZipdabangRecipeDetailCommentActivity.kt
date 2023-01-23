package com.example.umc_zipdabang.src.main.zipdabang_recipe_comment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeBeverageBinding
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailCommentBinding
import com.example.umc_zipdabang.databinding.ItemCommentBinding
import com.example.umc_zipdabang.src.main.decoration.AdapterDecoration
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.BeverageRecipesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.CoffeeRecipesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.Comment
import com.example.umc_zipdabang.src.main.zipdabang_recipe_detail.ZipdabangRecipeDetailCoffeeActivity
import com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter.BeverageRecipesRVAdapter
import com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter.RecipeDetailCommentRVAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.NonDisposableHandle.parent
import java.lang.Runnable
import java.time.LocalDate
import java.time.LocalTime

class ZipdabangRecipeDetailCommentActivity: AppCompatActivity() {
    lateinit var viewBinding: ActivityZipdabangRecipeDetailCommentBinding
    val commentNumberList: MutableList<Comment> = ArrayList()
    var page = 1
    var isLoading = false
    var limit = 12

    private lateinit var progressBar: ProgressBar

    lateinit var adapter: CommentInfiniteRVAdapter
    lateinit var layoutManager: LinearLayoutManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityZipdabangRecipeDetailCommentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        progressBar = viewBinding.progressBar

        layoutManager = LinearLayoutManager(this)
        viewBinding.rvZipdabangRecipeDetailComment.layoutManager = layoutManager
        getPage()

        viewBinding.rvZipdabangRecipeDetailComment.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = adapter.itemCount

                    if (!isLoading) {
                        if ((visibleItemCount + pastVisibleItem) >= total) {
                            page += 1
                            getPage()
                        }
                    }
                }
            }
        })


        val commentList: ArrayList<Comment> = arrayListOf()
        commentList.apply {
            add(
                Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", "12345",
                    "6789", "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
            )
            add(
                Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", "12345",
                    "6789", "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
            )
            add(
                    Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", "12345",
                        "6789", "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
                    )
            add(
                Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", "12345",
                    "6789", "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
            )
            add(
                Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", "12345",
                    "6789", "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
            )
            add(
                Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", "12345",
                    "6789", "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
            )
            add(
                Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", "12345",
                    "6789", "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
            )
            add(
                Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", "12345",
                    "6789", "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
            )
            add(
                Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png","김기문", "12345",
                    "6789", "너무 맛ㄱ잇어요ㅗㅇ러ㅣasdfasdfsasafasdfasdfa머이ㅏㅁㄴ이ㅏㄻ니아ㅓ미낭머")
            )

        }

        val commentRVAdapter = RecipeDetailCommentRVAdapter(commentList)
//        viewBinding.rvZipdabangRecipeDetailComment.adapter = commentRVAdapter
//        viewBinding.rvZipdabangRecipeDetailComment.layoutManager = LinearLayoutManager(this)
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

    class CommentInfiniteRVAdapter(val activity: ZipdabangRecipeDetailCommentActivity): RecyclerView.Adapter<CommentInfiniteRVAdapter.CommentInfiniteViewHolder>() {

        class CommentInfiniteViewHolder(private var binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(context: Context, item: Comment) {

                binding.tvCommentNickname.text= item.nickname
                binding.tvCommentDate.text=item.date
                Glide.with(context).load(item.profileImageUrl).into(binding.ivCommentProfile)
                binding.ivCommentProfile.clipToOutline = true
                binding.tvCommentTime.text = item.time
                binding.tvCommentContent.text = item.content

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, ZipdabangRecipeDetailCoffeeActivity::class.java)
                    intent.run { itemView.context.startActivity(this)}
                }

            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CommentInfiniteViewHolder {
            val viewBinding_viewholder = ItemCommentBinding.inflate(LayoutInflater.from(parent.context))
            return CommentInfiniteRVAdapter.CommentInfiniteViewHolder(viewBinding_viewholder)
        }

        override fun onBindViewHolder(holder: CommentInfiniteViewHolder, position: Int) {
            holder.bind(activity, activity.commentNumberList[position])
        }

        override fun getItemCount(): Int {
            return activity.commentNumberList.size
        }
    }

    private fun getPage() {
        isLoading = true
        progressBar.visibility = View.VISIBLE

        val start = (page-1) * limit
        val end = (page) * limit

        for (i in start..end) {
            // 여기에 해당 들어갈 내용을 넣기
            commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
        }
        Handler().postDelayed({
            if (::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            } else {
                adapter = CommentInfiniteRVAdapter(this)
                viewBinding.rvZipdabangRecipeDetailComment.adapter = adapter
            }
            isLoading = false
            progressBar.visibility = View.GONE
        },2000)
    }
}