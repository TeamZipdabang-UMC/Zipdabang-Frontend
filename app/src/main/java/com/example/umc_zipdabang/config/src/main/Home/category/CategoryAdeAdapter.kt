package com.example.umc_zipdabang.config.src.main.Home.category

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.config.src.main.Home.Scrap.My_Scrap
import com.example.umc_zipdabang.databinding.ItemMyscrapBeforeEditBinding

class CategoryAdeAdapter(private val context: CategoryAdeActivity, private val dataList: ArrayList<My_Scrap>) :
    RecyclerView.Adapter<CategoryAdeAdapter.ViewHolder>(){


    class ViewHolder(private var binding: ItemMyscrapBeforeEditBinding):

        RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: My_Scrap){
            binding.homeTvCategory1.text= item.title
            binding.homeTvHeart1.text=item.heart.toString()
            Glide.with(context).load(item.ImageUrl).into(binding.homeIvCategory1)
            binding.homeIvCategory1.clipToOutline = true


        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemMyscrapBeforeEditBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, dataList[position])

    }

    override fun getItemCount(): Int {

        return dataList.size
    }

}