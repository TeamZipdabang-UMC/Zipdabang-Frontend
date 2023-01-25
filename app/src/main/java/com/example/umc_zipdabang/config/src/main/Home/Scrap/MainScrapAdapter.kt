package com.example.umc_zipdabang.config.src.main.Home.Scrap

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.config.src.main.Home.HomeMainActivity
import com.example.umc_zipdabang.databinding.ItemMyScrapBinding

class MainScrapAdapter(private val context: HomeMainActivity, private val dataList: ArrayList<ArrayList<Main_Scrap>>) :
    RecyclerView.Adapter<MainScrapAdapter.ViewHolder>(){




    class ViewHolder(private var binding:ItemMyScrapBinding):

        RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: ArrayList<Main_Scrap>){
            binding.homeTvCategory1.text= item[0].title
            binding.homeTvHeart1.text=item[0].heart.toString()
            binding.homeTvCategory2.text= item[1].title
            binding.homeTvHeart2.text=item[1].heart.toString()
            Glide.with(context).load(item[0].ImageUrl).into(binding.homeIvCategory1)
            Glide.with(context).load(item[1].ImageUrl).into(binding.homeIvCategory2)
            binding.homeIvCategory1.clipToOutline = true
            binding.homeIvCategory2.clipToOutline = true






        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ItemMyScrapBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, dataList[position])

    }

    override fun getItemCount(): Int {


        return dataList.size
    }

}
