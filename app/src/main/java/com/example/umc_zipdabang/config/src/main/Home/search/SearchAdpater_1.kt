package com.example.umc_zipdabang.config.src.main.Home.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.config.src.main.Home.reciepe.Home_receipe
import com.example.umc_zipdabang.databinding.ItemSearchBinding

class SearchAdpater_1(private val context: SearchActivity, private val dataList: ArrayList<Home_receipe>) :
    RecyclerView.Adapter<SearchAdpater_1.ViewHolder>(){


    class ViewHolder(private var binding: ItemSearchBinding):

        RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: Home_receipe){

            binding.homeTvCategory.text = item.category
            val adapter= SearchAdapter_2(context as SearchActivity,item.receipe)
            binding.homeRvSearchCategory.adapter=adapter
            binding.homeRvSearchCategory.layoutManager=GridLayoutManager(context,2)




        }



        }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemSearchBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, dataList[position])

    }

    override fun getItemCount(): Int {

        return dataList.size
    }
}