package com.example.umc_zipdabang.config.src.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityEditscrapBinding
import com.example.umc_zipdabang.databinding.ItemMyScrapBinding
import com.example.umc_zipdabang.databinding.ItemMyscrapBeforeEditBinding
import com.example.umc_zipdabang.databinding.ItemMyscrapEditBinding


class EditScrapAdapter(private val context: EditScrapActivity, private val dataList: ArrayList<My_Scrap>) :
    RecyclerView.Adapter<EditScrapAdapter.ViewHolder>(){

    private var itemClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(v: View?, pos: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    inner class ViewHolder(private var binding: ItemMyscrapEditBinding):

        RecyclerView.ViewHolder(binding.root){

        var deletelist : ArrayList<My_Scrap> = arrayListOf()


        fun bind(context: Context, item: My_Scrap){

            binding.homeTvCategory1.text= item.title
            binding.homeTvHeart1.text=item.heart.toString()
            Glide.with(context).load(item.ImageUrl).into(binding.homeIvCategory1)
            binding.homeIvCategory1.clipToOutline = true

            binding.checkIv.setOnClickListener(object : View.OnClickListener{
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