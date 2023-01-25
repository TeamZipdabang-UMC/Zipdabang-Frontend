package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.config.src.main.Jip.src.main.decoration.AdapterDecoration
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailCommentAdeBinding
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailCommentBeverageBinding
import com.example.umc_zipdabang.databinding.ItemCommentBinding

import java.time.LocalDate
import java.time.LocalTime

class ZipdabangRecipeDetailCommentBeverageActivity: AppCompatActivity() {
    lateinit var viewBinding: ActivityZipdabangRecipeDetailCommentBeverageBinding
    private val commentNumberBeverageList: MutableList<Comment> = ArrayList()
    private var page = 1
    private var isLoading = false
    private var limit = 12

    private lateinit var progressBar: ProgressBar

    private lateinit var adapter: ZipdabangRecipeDetailCommentBeverageActivity.CommentInfiniteBeverageRVAdapter
    private lateinit var layoutManager: LinearLayoutManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("넘어옴","Beverage 코멘트")
        viewBinding = ActivityZipdabangRecipeDetailCommentBeverageBinding.inflate(layoutInflater)
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

    class CommentInfiniteBeverageRVAdapter(val activity: ZipdabangRecipeDetailCommentBeverageActivity): RecyclerView.Adapter<ZipdabangRecipeDetailCommentBeverageActivity.CommentInfiniteBeverageRVAdapter.CommentInfiniteBeverageViewHolder>() {

        class CommentInfiniteBeverageViewHolder(private var binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(context: Context, item: Comment) {

                binding.tvCommentNickname.text= item.nickname
                binding.tvCommentDate.text=item.date
                Glide.with(context).load(item.profileImageUrl).into(binding.ivCommentProfile)
                binding.ivCommentProfile.clipToOutline = true
                binding.tvCommentTime.text = item.time
                binding.tvCommentContent.text = item.content

                itemView.setOnClickListener {
//                    val intent = Intent(itemView.context, ZipdabangRecipeDetailCoffeeActivity::class.java)
//                    intent.run { itemView.context.startActivity(this)}
                }

            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ZipdabangRecipeDetailCommentBeverageActivity.CommentInfiniteBeverageRVAdapter.CommentInfiniteBeverageViewHolder {
            val viewBinding_beverage_viewholder = ItemCommentBinding.inflate(LayoutInflater.from(parent.context))
            return ZipdabangRecipeDetailCommentBeverageActivity.CommentInfiniteBeverageRVAdapter.CommentInfiniteBeverageViewHolder(viewBinding_beverage_viewholder)
        }

        override fun onBindViewHolder(holder: ZipdabangRecipeDetailCommentBeverageActivity.CommentInfiniteBeverageRVAdapter.CommentInfiniteBeverageViewHolder, position: Int) {
            holder.bind(activity, activity.commentNumberBeverageList[position])
        }

        override fun getItemCount(): Int {
            return activity.commentNumberBeverageList.size
        }
    }

    private fun getPage() {
        isLoading = true
        progressBar.visibility = View.VISIBLE

        val start = (page-1) * limit
        val end = (page) * limit

        for (i in start..end) {
            // 여기에 해당 들어갈 내용을 넣기
            commentNumberBeverageList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "정기문", "1234", "5678", "내입 썩는다."))
            commentNumberBeverageList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberBeverageList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberBeverageList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberBeverageList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberBeverageList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberBeverageList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberBeverageList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberBeverageList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberBeverageList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberBeverageList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
            commentNumberBeverageList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "김기문", "1234", "5678", "내입 썩는다."))
        }
        Handler().postDelayed({
            if (::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            } else {
                adapter = ZipdabangRecipeDetailCommentBeverageActivity.CommentInfiniteBeverageRVAdapter(this)
                viewBinding.rvZipdabangRecipeDetailComment.adapter = adapter
            }
            isLoading = false
            progressBar.visibility = View.GONE
        },2000)
    }
}