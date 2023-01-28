package com.example.umc_zipdabang.config.src.main.Home.Scrap

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.databinding.ItemMyscrapBeforeEditBinding

class MyScrapAdapter(private val context: MyScapActivity, private val dataList: ArrayList<My_Scrapp>) :
    RecyclerView.Adapter<MyScrapAdapter.ViewHolder>(){


    class ViewHolder(private var binding: ItemMyscrapBeforeEditBinding):

        RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: My_Scrapp, dataList: ArrayList<My_Scrapp>){

            if(dataList.size!=0) {

                binding.homeTvCategory1.text = item.title
                binding.homeTvHeart1.text = item.heart.toString()
                Glide.with(context).load(item.ImageUrl).into(binding.homeIvCategory1)
                binding.homeIvCategory1.clipToOutline = true

            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemMyscrapBeforeEditBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, dataList[position],dataList)

    }

    override fun getItemCount(): Int {

        return dataList.size
    }

}