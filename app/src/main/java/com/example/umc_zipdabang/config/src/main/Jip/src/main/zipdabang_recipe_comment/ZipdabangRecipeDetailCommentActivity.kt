package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.config.src.main.Jip.src.main.decoration.AdapterDecoration
import com.example.umc_zipdabang.databinding.ActivityZipdabangRecipeDetailCommentBinding
import com.example.umc_zipdabang.databinding.ItemCommentBinding

class ZipdabangRecipeDetailCommentActivity: AppCompatActivity() {
    lateinit var viewBinding: ActivityZipdabangRecipeDetailCommentBinding
    private val commentNumberList = ArrayList<com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.Comment>()
    private var page = 1
    private var isLoading = false
    private var limit = 12

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

//        val deleteCommentView = findViewById<TextView>(R.id.btn_comment_final_delete)
//        deleteCommentView.setOnClickListener {
//            showCommentDeleteToast()
//        }



    }

    class CommentInfiniteRVAdapter(val activity: ZipdabangRecipeDetailCommentActivity, private val commentNumberList: ArrayList<com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.Comment>): RecyclerView.Adapter<CommentInfiniteRVAdapter.CommentInfiniteViewHolder>() {

        class CommentInfiniteViewHolder(private var binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(context: Context, item: com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.Comment) {


                binding.tvCommentNickname.text= item.nickname
                binding.tvCommentDate.text=item.date
                Glide.with(context).load(item.profileImageUrl).into(binding.ivCommentProfile)
                binding.ivCommentProfile.clipToOutline = true
                binding.tvCommentTime.text = item.time
                binding.tvCommentContent.text = item.content

                binding.ivCommentControl.setOnClickListener {
                    val commentEditDeleteDialogView = LayoutInflater.from(context).inflate(R.layout.comment_control_dialog, null)
                    val commentEditDeleteDialogBuilder = AlertDialog.Builder(context).setView(commentEditDeleteDialogView)

                    val commentEditDeleteDialog = commentEditDeleteDialogBuilder.create()
                    commentEditDeleteDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    commentEditDeleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    commentEditDeleteDialog.window?.setGravity(Gravity.BOTTOM)
                    commentEditDeleteDialog.window?.attributes?.width = WindowManager.LayoutParams.WRAP_CONTENT
                    commentEditDeleteDialog.window?.attributes?.height = WindowManager.LayoutParams.WRAP_CONTENT

                    commentEditDeleteDialog.show()

                    val commentPopupExitButton = commentEditDeleteDialogView.findViewById<ImageView>(R.id.btn_comment_control_exit)
                    val commentPopupEditButton = commentEditDeleteDialogView.findViewById<TextView>(R.id.btn_comment_edit)
                    val commentPopupDeleteButton = commentEditDeleteDialogView.findViewById<TextView>(R.id.btn_comment_delete)

                    commentPopupExitButton.setOnClickListener {
                        commentEditDeleteDialog.dismiss()
                    }

                    // 댓글 수정 선택 시
                    commentPopupEditButton.setOnClickListener {

                    }

                    // 댓글 삭제 선택 시
                    commentPopupDeleteButton.setOnClickListener {
                        commentEditDeleteDialog.dismiss()

                        val commentDeleteDialogView = LayoutInflater.from(context).inflate(R.layout.comment_delete_dialog, null)
                        val commentDeleteDialogBuilder = AlertDialog.Builder(context)
                            .setView(commentDeleteDialogView)
                        val commentDeleteDialog = commentDeleteDialogBuilder.create()
                        commentDeleteDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        commentDeleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        commentDeleteDialog.window?.setGravity(Gravity.BOTTOM)
                        commentDeleteDialog.window?.attributes?.width = WindowManager.LayoutParams.WRAP_CONTENT
                        commentDeleteDialog.window?.attributes?.height = WindowManager.LayoutParams.WRAP_CONTENT

                        commentDeleteDialog.show()

                        val commentFinalDeleteButton = commentDeleteDialogView.findViewById<TextView>(R.id.btn_comment_final_delete)
                        val commentFinalCancelButton = commentDeleteDialogView.findViewById<TextView>(R.id.btn_comment_final_cancel)

                        // 정말 삭제하시겠습니까? - 삭제 클릭 시
                        commentFinalDeleteButton.setOnClickListener {

                        }

                        // 취소 클릭 시
                        commentFinalCancelButton.setOnClickListener {
                            commentDeleteDialog.dismiss()
                        }

                    }
                }

            }

        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CommentInfiniteRVAdapter.CommentInfiniteViewHolder {
            val viewBinding_viewholder = ItemCommentBinding.inflate(LayoutInflater.from(parent.context))
            return CommentInfiniteRVAdapter.CommentInfiniteViewHolder(viewBinding_viewholder)


        }

        override fun onBindViewHolder(holder: CommentInfiniteRVAdapter.CommentInfiniteViewHolder, position: Int) {
            holder.bind(activity, activity.commentNumberList[position])

        }

        override fun getItemCount(): Int {
            return activity.commentNumberList.size
        }

        override fun getItemViewType(position: Int): Int {
            return position
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
                adapter = ZipdabangRecipeDetailCommentActivity.CommentInfiniteRVAdapter(this, commentNumberList)
                viewBinding.rvZipdabangRecipeDetailComment.adapter = adapter
            }
            isLoading = false
            progressBar.visibility = View.GONE
        },2000)
    }

    // 토스트 띄우기
    private fun showCommentDeleteToast() {
        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.BOTTOM, 0, 0)
            val layout: View = layoutInflater.inflate(R.layout.toast_comment_delete_layout, findViewById(R.id.toast_delete))
            view = layout
        }.show()

    }


}