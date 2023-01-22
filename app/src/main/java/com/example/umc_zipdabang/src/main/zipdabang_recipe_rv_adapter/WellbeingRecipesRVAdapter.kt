package com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.databinding.ItemLoadingBinding
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding
import com.example.umc_zipdabang.src.main.GlideApp
import com.example.umc_zipdabang.src.main.ZipdabangRecipeSmoothieActivity
import com.example.umc_zipdabang.src.main.ZipdabangRecipeWellbeingActivity
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.SmoothieRecipesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.WellbeingRecipesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_detail.ZipdabangRecipeDetailSmoothieActivity
import com.example.umc_zipdabang.src.main.zipdabang_recipe_detail.ZipdabangRecipeDetailWellbeingActivity

class WellbeingRecipesRVAdapter(private val context: ZipdabangRecipeWellbeingActivity, private var dataList: ArrayList<WellbeingRecipesData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private fun update(list: ArrayList<WellbeingRecipesData>) {
        this.dataList= list
    }

    private var VIEW_TYPE_ITEM = 0
    private var VIEW_TYPE_LOADING = 1

    class ItemViewHolder(private var binding: ItemRecipesPreviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, item: WellbeingRecipesData) {
            binding.tvRecipePreview.text = item.wellbeing
            binding.tvLikes.text = item.likes.toString()
            Glide.with(context)
                .load(item.picUrl)
                .into(binding.ivRecipePreview)
            binding.ivRecipePreview.clipToOutline = true

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ZipdabangRecipeDetailWellbeingActivity::class.java)
                intent.run {
                    itemView.context.startActivity(this)
                }
            }
        }
    }

    class LoadingViewHolder(private var binding: ItemLoadingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, item: WellbeingRecipesData) {

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
            val binding = ItemRecipesPreviewBinding.inflate(LayoutInflater.from(context), parent, false)
            return ItemViewHolder(binding)
        } else {
            val binding = ItemLoadingBinding.inflate(LayoutInflater.from(context), parent, false)
            return LoadingViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position].wellbeing) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}