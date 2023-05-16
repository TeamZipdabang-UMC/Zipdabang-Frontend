package com.UMC.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment

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
import com.UMC.umc_zipdabang.R
import com.UMC.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailCommentCoffeeBinding
import com.UMC.umc_zipdabang.databinding.ItemCommentBinding
import com.bumptech.glide.Glide
import com.UMC.umc_zipdabang.config.src.main.Jip.src.main.decoration.AdapterDecoration


class ZipdabangRecipeDetailCommentCoffeeActivity: AppCompatActivity() {
    lateinit var viewBinding: ActivityZipdabangRecipeDetailCommentCoffeeBinding
    private val commentNumberCoffeeList: MutableList<Comment> = ArrayList()
    private var page = 1
    private var isLoading = false
    private var limit = 12

    private lateinit var progressBar: ProgressBar

    private lateinit var adapter: ZipdabangRecipeDetailCommentCoffeeActivity.CommentInfiniteCoffeeRVAdapter
    private lateinit var layoutManager: LinearLayoutManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("넘어옴","Beverage 코멘트")
        viewBinding = ActivityZipdabangRecipeDetailCommentCoffeeBinding.inflate(layoutInflater)
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

    class CommentInfiniteCoffeeRVAdapter(val activity: ZipdabangRecipeDetailCommentCoffeeActivity): RecyclerView.Adapter<ZipdabangRecipeDetailCommentCoffeeActivity.CommentInfiniteCoffeeRVAdapter.CommentInfiniteCoffeeViewHolder>() {

        class CommentInfiniteCoffeeViewHolder(private var binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root){
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
        ): ZipdabangRecipeDetailCommentCoffeeActivity.CommentInfiniteCoffeeRVAdapter.CommentInfiniteCoffeeViewHolder {
            val viewBinding_coffee_viewholder = ItemCommentBinding.inflate(LayoutInflater.from(parent.context))
            return ZipdabangRecipeDetailCommentCoffeeActivity.CommentInfiniteCoffeeRVAdapter.CommentInfiniteCoffeeViewHolder(viewBinding_coffee_viewholder)
        }

        override fun onBindViewHolder(holder: ZipdabangRecipeDetailCommentCoffeeActivity.CommentInfiniteCoffeeRVAdapter.CommentInfiniteCoffeeViewHolder, position: Int) {
            holder.bind(activity, activity.commentNumberCoffeeList[position])
        }

        override fun getItemCount(): Int {
            return activity.commentNumberCoffeeList.size
        }
    }

    private fun getPage() {
        isLoading = true
        progressBar.visibility = View.VISIBLE

        val start = (page-1) * limit
        val end = (page) * limit

        for (i in start..end) {
            // 여기에 해당 들어갈 내용을 넣기
//            commentNumberCoffeeList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "킹기문", "1234", "5678", "내입 썩는다."))
//            commentNumberCoffeeList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "킹기문", "1234", "5678", "내입 썩는다."))
//            commentNumberCoffeeList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "킹기문", "1234", "5678", "내입 썩는다."))
//            commentNumberCoffeeList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "킹기문", "1234", "5678", "내입 썩는다."))
//            commentNumberCoffeeList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "킹기문", "1234", "5678", "내입 썩는다."))
//            commentNumberCoffeeList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "킹기문", "1234", "5678", "내입 썩는다."))
//            commentNumberCoffeeList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "킹기문", "1234", "5678", "내입 썩는다."))
//            commentNumberCoffeeList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "킹기문", "1234", "5678", "내입 썩는다."))
//            commentNumberCoffeeList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "킹기문", "1234", "5678", "내입 썩는다."))
//            commentNumberCoffeeList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "킹기문", "1234", "5678", "내입 썩는다."))
//            commentNumberCoffeeList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "킹기문", "1234", "5678", "내입 썩는다."))
//            commentNumberCoffeeList.add(Comment("https://user-images.githubusercontent.com/101035437/213335682-3b9f3b22-19b1-4a62-a326-d5a287557584.png", "킹기문", "1234", "5678", "내입 썩는다."))

        }
        Handler().postDelayed({
            if (::adapter.isInitialized) {
                adapter.notifyDataSetChanged()
            } else {
                adapter = ZipdabangRecipeDetailCommentCoffeeActivity.CommentInfiniteCoffeeRVAdapter(this)
                viewBinding.rvZipdabangRecipeDetailComment.adapter = adapter
            }
            isLoading = false
            progressBar.visibility = View.GONE
        },2000)
    }
}