package com.example.umc_zipdabang.config.src.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.databinding.ItemMyScrapBinding
import com.example.umc_zipdabang.databinding.ItemMyscrapBeforeEditBinding
import com.example.umc_zipdabang.databinding.ItemMyscrapEditBinding

class EditScrapAdapter(private val context: EditScrapActivity, private val dataList: ArrayList<My_Scrap>) :
    RecyclerView.Adapter<EditScrapAdapter.ViewHolder>(){


    var deletelist : ArrayList<My_Scrap> = arrayListOf()

    class ViewHolder(private var binding: ItemMyscrapEditBinding):

        RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: My_Scrap){
            binding.homeTvCategory1.text= item.title
            binding.homeTvHeart1.text=item.heart.toString()
            Glide.with(context).load(item.ImageUrl).into(binding.homeIvCategory1)
            binding.homeIvCategory1.clipToOutline = true

            binding.checkIv.setOnClickListener() {

                if(binding.checkIv.isSelected){




                }


            }












        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemMyscrapEditBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, dataList[position])

    }

    override fun getItemCount(): Int {

        return dataList.size
    }

}