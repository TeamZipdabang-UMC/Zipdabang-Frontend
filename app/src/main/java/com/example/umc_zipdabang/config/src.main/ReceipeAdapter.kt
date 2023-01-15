package com.example.umc_zipdabang.config.src.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.databinding.ItemMyScrapBinding
import com.example.umc_zipdabang.databinding.ItemReceipeBinding
import java.lang.StringBuilder

class ReceipeAdapter(private val context: MainActivity, private val dataList: ArrayList<Home_receipe>) :
    RecyclerView.Adapter<ReceipeAdapter.ViewHolder>(){
    class ViewHolder(private var binding:ItemReceipeBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(context: Context, item: Home_receipe){
            binding.homeTvCategory.text= item.category
            binding.homeTvCategory1.text= item.receipe[0].title
            binding.homeTvHeart1.text=item.receipe[0].heart.toString()
            binding.homeTvCategory2.text= item.receipe[1].title
            binding.homeTvHeart2.text=item.receipe[1].heart.toString()
            Glide.with(context).load(item.receipe[0].ImageUrl).into(binding.homeIvCategory1)
            Glide.with(context).load(item.receipe[1].ImageUrl).into(binding.homeIvCategory2)
            binding.homeIvCategory1.clipToOutline = true
            binding.homeIvCategory2.clipToOutline = true

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ItemReceipeBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size

    }

}
