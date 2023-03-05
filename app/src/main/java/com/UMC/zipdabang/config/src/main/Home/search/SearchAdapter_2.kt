package com.UMC.zipdabang.config.src.main.Home.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.UMC.umc_zipdabang.databinding.ItemSearchReciepeBinding
import com.bumptech.glide.Glide

class SearchAdapter_2(private val context: SearchActivity, private val dataList: ArrayList<Search>) :
    RecyclerView.Adapter<SearchAdapter_2.ViewHolder>(){
    private var itemClickListener2: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(v: View?, pos: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener2 = listener
    }

      inner  class ViewHolder(private var binding: ItemSearchReciepeBinding):

        RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: Search){
            binding.homeTvCategory1.text=item.name
            binding.homeTvHeart1.text=item.likes.toString()
            Glide.with(context).load(item.image).into(binding.homeIvCategory1)
            binding.homeIvCategory1.clipToOutline = true

            binding.homeIvCategory1.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val pos=getAdapterPosition()
                    if (pos != RecyclerView.NO_POSITION) {
                        itemClickListener2?.onItemClick(p0, pos)
                    }
                }
            })




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