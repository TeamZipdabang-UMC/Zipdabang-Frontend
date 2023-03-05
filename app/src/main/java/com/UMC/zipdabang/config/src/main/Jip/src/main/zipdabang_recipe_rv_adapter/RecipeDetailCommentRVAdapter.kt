package com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.UMC.umc_zipdabang.databinding.ItemCommentBinding
import com.UMC.zipdabang.config.src.main.Jip.src.main.GlideApp
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.Comment

class RecipeDetailCommentRVAdapter(private val commentsList: ArrayList<com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.Comment>): RecyclerView.Adapter<RecipeDetailCommentRVAdapter.CommentsViewHolder>() {
    inner class CommentsViewHolder(private val viewBinding: ItemCommentBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(comment: Comment) {
            val profilePicUrl = comment.profileImageUrl
            GlideApp.with(itemView)
                .load(profilePicUrl)
                .into(viewBinding.ivCommentProfile)
            viewBinding.tvCommentNickname.text = comment.nickname
            viewBinding.tvCommentDate.text = comment.date.toString()
            viewBinding.tvCommentTime.text = comment.time.toString()
            viewBinding.tvCommentContent.text = comment.content
            viewBinding.ivCommentControl.visibility = View.INVISIBLE
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val viewBinding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context))
        return CommentsViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(commentsList[position])

    }

    override fun getItemCount(): Int = commentsList.size

}