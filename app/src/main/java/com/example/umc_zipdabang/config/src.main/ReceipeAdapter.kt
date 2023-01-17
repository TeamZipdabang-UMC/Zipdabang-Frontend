package com.example.umc_zipdabang.config.src.main

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ItemMyScrapBinding
import com.example.umc_zipdabang.databinding.ItemMyscrapEditBinding
import com.example.umc_zipdabang.databinding.ItemReceipeBinding
import java.lang.StringBuilder
import java.sql.Types

class ReceipeAdapter(private val context: MainActivity, private val dataList: ArrayList<Home_receipe>) :
    RecyclerView.Adapter<ReceipeAdapter.ViewHolder>(){

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(v: View?, pos: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }


    inner class ViewHolder(private var binding: ItemReceipeBinding):

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



            binding.arrowIv.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val pos=getAdapterPosition()
                    if (pos != RecyclerView.NO_POSITION) {
                        itemClickListener?.onItemClick(p0, pos)
                    }
                }
            })


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
