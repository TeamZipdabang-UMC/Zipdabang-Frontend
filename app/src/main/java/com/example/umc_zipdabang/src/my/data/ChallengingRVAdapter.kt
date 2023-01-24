package com.example.umc_zipdabang.src.my.data

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.databinding.ItemLoadingBinding
import com.example.umc_zipdabang.databinding.ItemInfiniteBinding
import com.example.umc_zipdabang.src.my.MyChallengingFragment

class ChallengingRVAdapter(private val context: MyChallengingFragment, private var dataList:ArrayList<ItemRecipeData>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    fun update(list: ArrayList<ItemRecipeData>){
        this.dataList=list
    }

    private var VIEW_TYPE_ITEM=0
    private var VIEW_TYPE_LOADING=1
    val activity = context as Activity

    class ItemViewHolder(private val viewBinding: ItemInfiniteBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(context: Context, ItemRecipeData: ItemRecipeData){
            val url = ItemRecipeData.picUrl
            Glide.with(itemView)
                .load(url)
                .into(viewBinding.myRecipeImg)
            viewBinding.myRecipeTital.text = ItemRecipeData.tital
            viewBinding.myRecipeHeart.text = ItemRecipeData.likes.toString()
        }
    }

    class LoadingViewHolder(private val viewBinding: ItemLoadingBinding):RecyclerView.ViewHolder(viewBinding.root){
        fun bind(context: Context, item:ItemRecipeData){

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemViewHolder){
            holder.bind(activity, dataList[position])
        }else if(holder is LoadingViewHolder){
            holder.bind(activity, dataList[position])
        }
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == VIEW_TYPE_ITEM){
            val binding = ItemInfiniteBinding.inflate(LayoutInflater.from(activity), parent, false)
            return ItemViewHolder(binding)
        }else{
            val binding = ItemLoadingBinding.inflate(LayoutInflater.from(activity), parent, false)
            return LoadingViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position].tital) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}