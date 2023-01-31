package com.example.umc_zipdabang.config.src.main.Home.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.config.src.main.Home.Scrap.My_Scrapp
import com.example.umc_zipdabang.databinding.ItemInfinteBinding
import com.example.umc_zipdabang.databinding.ItemLoadingBinding

class CategoryAdeAdapter(private val context: CategoryAdeActivity, private var dataList: ArrayList<My_Scrapp>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemClickListener1: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(v: View?, pos: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener1 = listener
    }


    private var VIEW_TYPE_ITEM = 0
    private var VIEW_TYPE_LOADING = 1

    inner class ItemViewHolder(private var binding: ItemInfinteBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: My_Scrapp) {

            binding.homeTvCategory1.text= item.title
            binding.homeTvHeart1.text=item.heart.toString()
            Glide.with(context).load(item.ImageUrl).into(binding.homeIvCategory1)
            binding.homeIvCategory1.clipToOutline = true

            binding.homeIvCategory1.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val pos=getAdapterPosition()
                    if (pos != RecyclerView.NO_POSITION) {
                        itemClickListener1?.onItemClick(p0, pos)
                    }
                }
            })

        }
    }


    inner class LoadingViewHolder(private var binding: ItemLoadingBinding) :

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
