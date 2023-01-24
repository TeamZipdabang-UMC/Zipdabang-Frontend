package com.example.umc_zipdabang.src.my.data

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.databinding.ItemLoadingBinding
import com.example.umc_zipdabang.databinding.ItemRecipeBinding
import com.example.umc_zipdabang.src.main.MainActivity

class MyMyrecipeRVAdapter(private val context: MainActivity, private var dataList: ArrayList<ItemRecipeData>)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        fun update(list : ArrayList<ItemRecipeData>){
            this.dataList = list
        }

    private var VIEW_TYPE_ITEM = 0
    private var VIEW_TYPE_LOADING = 1

    class ItemViewHolder(private var binding: ItemRecipeBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(context:Context, item: ItemRecipeData){
                binding.myRecipeTital.text =item.tital
                binding.myRecipeHeart.text = item.likes.toString()
                Glide.with(context)
                    .load(item.picUrl)
                    .into(binding.myRecipeImg)
                binding.myRecipeImg.clipToOutline = true
            }
        }

    class LoadingViewHolder(private var binding: ItemLoadingBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(context:Context, item: ItemRecipeData){

            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ItemViewHolder){
            holder.bind(context, dataList[position])
        }else if(holder is LoadingViewHolder){
            holder.bind(context, dataList[position])
        }
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == VIEW_TYPE_ITEM){
            val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return ItemViewHolder(binding)
        }else{
            val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return LoadingViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(dataList[position].tital){
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}