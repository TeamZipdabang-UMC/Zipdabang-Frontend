package com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.UMC.zipdabang.databinding.ItemLoadingBinding
import com.UMC.zipdabang.databinding.ItemRecipesPreviewBinding
import com.bumptech.glide.Glide
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.ZipdabangRecipeCoffeeActivity
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailCoffeeActivity
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.CoffeeRecipesData


class CoffeeRecipesRVAdapter(private val context: ZipdabangRecipeCoffeeActivity, private var dataList: ArrayList<CoffeeRecipesData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun update(list : ArrayList<CoffeeRecipesData>){
        this.dataList= list
    }

    private var VIEW_TYPE_ITEM = 0
    private var VIEW_TYPE_LOADING = 1

    class ItemViewHolder(private var binding: ItemRecipesPreviewBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: CoffeeRecipesData) {

            binding.tvRecipePreview.text= item.coffee
            binding.tvLikes.text=item.likes.toString()
            Glide.with(context).load(item.picUrl).into(binding.ivRecipePreview)
            binding.ivRecipePreview.clipToOutline = true

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ZipdabangRecipeDetailCoffeeActivity::class.java)
                intent.run { itemView.context.startActivity(this)}
            }

        }
    }


    class LoadingViewHolder(private var binding: ItemLoadingBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: CoffeeRecipesData) {


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
            val binding =
                ItemRecipesPreviewBinding.inflate(LayoutInflater.from(context), parent, false)
            return ItemViewHolder(binding)
        } else {
            val binding =
                ItemLoadingBinding.inflate(LayoutInflater.from(context), parent, false)
            return LoadingViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position].coffee) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM

        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }


}