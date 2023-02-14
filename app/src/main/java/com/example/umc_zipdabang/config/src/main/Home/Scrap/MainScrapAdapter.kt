package com.example.umc_zipdabang.config.src.main.Home.Scrap

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.config.src.main.Home.HomeMainActivity
import com.example.umc_zipdabang.databinding.ItemMyScrapBinding

class MainScrapAdapter(private val context: HomeMainActivity, private val dataList: ArrayList<ArrayList<Main_Scrap>>) :
    RecyclerView.Adapter<MainScrapAdapter.ViewHolder>(){
    private var itemClickListener2: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(v: View?, pos: Int)
    }
    fun setOnItemClickListener2(listener: OnItemClickListener) {
        itemClickListener2 = listener
    }

    private var itemClickListener1: OnItemClickListener? = null

    fun setOnItemClickListener1(listener: OnItemClickListener) {
        itemClickListener1 = listener
    }




   inner class ViewHolder(private var binding:ItemMyScrapBinding):

        RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: ArrayList<Main_Scrap>){

            if(item.size!=0) {
                binding.homeTvCategory1.text = item[0].title
                binding.homeTvHeart1.text = item[0].heart.toString()
                Glide.with(context).load(item[0].ImageUrl).into(binding.homeIvCategory1)
                binding.homeIvCategory1.clipToOutline = true
                binding.homeHeartIv1.visibility=View.VISIBLE

                binding.homeIvCategory1.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(p0: View?) {
                        val pos=getAdapterPosition()
                        if (pos != RecyclerView.NO_POSITION) {
                            itemClickListener1?.onItemClick(p0, pos)


                        }
                    }
                })

                if(item.size!=1) {

                    binding.homeTvHeart2.text = item[1].heart.toString()
                    binding.homeTvCategory2.text = item[1].title
                    Glide.with(context).load(item[1].ImageUrl).into(binding.homeIvCategory2)
                    binding.homeIvCategory2.clipToOutline = true
                    binding.homeHeartIv2.visibility=View.VISIBLE




                    binding.homeIvCategory2.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(p0: View?) {
                            val pos = getAdapterPosition()
                            if (pos != RecyclerView.NO_POSITION) {
                                itemClickListener2?.onItemClick(p0, pos)


                            }
                        }
                    })

                }




            }




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
