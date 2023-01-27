package com.example.umc_zipdabang.config.src.main.Home.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.config.src.main.Home.Scrap.Search
import com.example.umc_zipdabang.databinding.ItemSearchReciepeBinding

class SearchAdapter_2(private val context: SearchActivity, private val dataList: ArrayList<Search>) :
    RecyclerView.Adapter<SearchAdapter_2.ViewHolder>(){


    class ViewHolder(private var binding: ItemSearchReciepeBinding):

        RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: Search){
            binding.homeTvCategory1.text=item.name
            binding.homeTvHeart1.text=item.likes.toString()
            Glide.with(context).load(item.image).into(binding.homeIvCategory1)
            binding.homeIvCategory1.clipToOutline = true


        }



    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=  ItemSearchReciepeBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, dataList[position])

    }

    override fun getItemCount(): Int {

        return dataList.size
    }
}