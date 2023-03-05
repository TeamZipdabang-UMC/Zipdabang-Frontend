package com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.UMC.umc_zipdabang.databinding.ItemLoadingBinding
import com.UMC.umc_zipdabang.databinding.ItemRecipesPreviewBinding
import com.bumptech.glide.Glide
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.ZipdabangRecipeSmoothieActivity
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailSmoothieActivity
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.SmoothieRecipesData



class SmoothieRecipesRVAdapter(private val context: ZipdabangRecipeSmoothieActivity, private var dataList: ArrayList<SmoothieRecipesData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private fun update(list: ArrayList<SmoothieRecipesData>) {
        this.dataList= list
    }

    private var VIEW_TYPE_ITEM = 0
    private var VIEW_TYPE_LOADING = 1

    class ItemViewHolder(private var binding: ItemRecipesPreviewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, item: SmoothieRecipesData) {
            binding.tvRecipePreview.text = item.smoothie
            binding.tvLikes.text = item.likes.toString()
            Glide.with(context)
                .load(item.picUrl)
                .into(binding.ivRecipePreview)
            binding.ivRecipePreview.clipToOutline = true

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ZipdabangRecipeDetailSmoothieActivity::class.java)
                intent.run {
                    itemView.context.startActivity(this)
                }
            }
        }
    }

    class LoadingViewHolder(private var binding: ItemLoadingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, item: SmoothieRecipesData) {

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
        return when (dataList[position].smoothie) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}