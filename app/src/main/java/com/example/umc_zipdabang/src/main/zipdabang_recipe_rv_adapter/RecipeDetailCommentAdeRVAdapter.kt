package com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.databinding.ItemCommentBinding
import com.example.umc_zipdabang.databinding.ItemLoadingBinding
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding
import com.example.umc_zipdabang.src.main.GlideApp
import com.example.umc_zipdabang.src.main.ZipdabangRecipeSmoothieActivity
import com.example.umc_zipdabang.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailCommentActivity
import com.example.umc_zipdabang.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailCommentAdeActivity
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.Comment
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.SmoothieRecipesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_detail.ZipdabangRecipeDetailSmoothieActivity
import java.time.LocalDate
import java.time.LocalTime

class RecipeDetailCommentAdeRVAdapter(private val context: ZipdabangRecipeDetailCommentAdeActivity, private var dataList: ArrayList<Comment>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private fun update(list: ArrayList<Comment>) {
        this.dataList= list
    }

    private var VIEW_TYPE_ITEM = 0
    private var VIEW_TYPE_LOADING = 1

    class ItemViewHolder(private var binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, item: Comment) {
//            val profileImageUrl: String,
//            val nickname: String,
//            val date: LocalDate,
//            val time: LocalTime,
//            val content: String
            binding.tvCommentNickname.text = item.nickname
            binding.tvCommentDate.text = item.date.toString()
            binding.tvCommentContent.text = item.content.toString()
            binding.tvCommentTime.text = item.time.toString()
            Glide.with(context)
                .load(item.profileImageUrl)
                .into(binding.ivCommentProfile)
            binding.ivCommentProfile.clipToOutline = true

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ZipdabangRecipeDetailCommentAdeActivity::class.java)
                intent.run {
                    itemView.context.startActivity(this)
                }
            }
        }
    }

    class LoadingViewHolder(private var binding: ItemLoadingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, item: Comment) {

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(context, dataList[position])
        } else if (holder is LoadingViewHolder) {
            holder.bind(context, dataList[position])
        }
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val binding = ItemCommentBinding.inflate(LayoutInflater.from(context), parent, false)
            return ItemViewHolder(binding)
        } else {
            val binding = ItemLoadingBinding.inflate(LayoutInflater.from(context), parent, false)
            return LoadingViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position].nickname) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}