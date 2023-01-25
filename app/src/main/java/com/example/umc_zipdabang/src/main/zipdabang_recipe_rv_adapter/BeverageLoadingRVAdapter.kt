package com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.databinding.ItemLoadingBinding
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding
import com.example.umc_zipdabang.src.main.MainActivity
import com.example.umc_zipdabang.src.main.zipdabang_recipe_activities_fragments.ZipdabangRecipeAdeActivity
import com.example.umc_zipdabang.src.main.zipdabang_recipe_activities_fragments.ZipdabangRecipeBeverageActivity
import com.example.umc_zipdabang.src.main.zipdabang_recipe_activities_fragments.ZipdabangRecipeFragment
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.AdeRecipesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.AllRecipesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.BeverageRecipesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_detail.ZipdabangRecipeDetailActivity
import com.example.umc_zipdabang.src.main.zipdabang_recipe_detail.ZipdabangRecipeDetailCoffeeActivity
import kotlinx.coroutines.NonDisposableHandle.parent

class BeverageLoadingRVAdapter(private val context: ZipdabangRecipeBeverageActivity, private var dataList: ArrayList<BeverageRecipesData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun update(list : ArrayList<BeverageRecipesData>){
        this.dataList= list
    }

    private var VIEW_TYPE_ITEM = 0
    private var VIEW_TYPE_LOADING = 1

    class ItemViewHolder(private var binding: ItemRecipesPreviewBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: BeverageRecipesData) {

            binding.tvRecipePreview.text= item.beverage
            binding.tvLikes.text=item.likes.toString()
            Glide.with(context)
                .load(item.picUrl)
                .into(binding.ivRecipePreview)
            binding.ivRecipePreview.clipToOutline = true

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ZipdabangRecipeDetailActivity::class.java)
                intent.run { itemView.context.startActivity(this)}
            }

        }
    }


    class LoadingViewHolder(private var binding: ItemLoadingBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: BeverageRecipesData) {


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
                ItemRecipesPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemViewHolder(binding)
        } else {
            val binding =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LoadingViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position].beverage) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }
}