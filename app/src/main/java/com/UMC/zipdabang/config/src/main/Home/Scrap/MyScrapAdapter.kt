package com.UMC.zipdabang.config.src.main.Home.Scrap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.UMC.umc_zipdabang.databinding.ItemMyscrapBeforeEditBinding
import com.bumptech.glide.Glide

class MyScrapAdapter(private val context: MyScapActivity, private val dataList: ArrayList<My_Scrapp>) :
    RecyclerView.Adapter<MyScrapAdapter.ViewHolder>(){


    private var itemClickListener1: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(v: View?, pos: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener1 = listener
    }



    inner class ViewHolder(private var binding: ItemMyscrapBeforeEditBinding):

        RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: My_Scrapp, dataList: ArrayList<My_Scrapp>){

            if(dataList.size!=0) {

                binding.homeTvCategory1.text = item.title
                binding.homeTvHeart1.text = item.heart.toString()
                Glide.with(context).load(item.ImageUrl).into(binding.homeIvCategory1)
                binding.homeIvCategory1.clipToOutline = true
                binding.homeTvHeart1.visibility=View.VISIBLE

                binding.homeIvCategory1.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(p0: View?) {
                        val pos=getAdapterPosition()
                        if (pos != RecyclerView.NO_POSITION) {
                            itemClickListener1?.onItemClick(p0, pos)
                        }
                    }
                })

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