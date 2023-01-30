package com.example.umc_zipdabang.config.src.main.Home.category

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.config.src.main.Home.Scrap.My_Scrap
import com.example.umc_zipdabang.config.src.main.Home.Scrap.My_Scrapp
import com.example.umc_zipdabang.databinding.ItemInfinteBinding
import com.example.umc_zipdabang.databinding.ItemLoadingBinding

class CategoryBeverageAdapter(private val context: CategoryBeverageActivity, private var dataList: ArrayList<My_Scrapp>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun update(list : ArrayList<My_Scrapp>){
        this.dataList= list
    }

    private var VIEW_TYPE_ITEM = 0
    private var VIEW_TYPE_LOADING = 1

    class ItemViewHolder(private var binding: ItemInfinteBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: My_Scrapp) {

            binding.homeTvCategory1.text= item.title
            binding.homeTvHeart1.text=item.heart.toString()
            Glide.with(context).load(item.ImageUrl).into(binding.homeIvCategory1)
            binding.homeIvCategory1.clipToOutline = true

        }
    }


    class LoadingViewHolder(private var binding: ItemLoadingBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: My_Scrapp) {


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
                ItemInfinteBinding.inflate(LayoutInflater.from(context), parent, false)
            return ItemViewHolder(binding)
        } else {
            val binding =
                ItemLoadingBinding.inflate(LayoutInflater.from(context), parent, false)
            return LoadingViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position].title) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM

        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }


}
