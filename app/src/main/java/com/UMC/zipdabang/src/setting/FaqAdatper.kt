package com.UMC.zipdabang.src.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.UMC.umc_zipdabang.databinding.ItemFaqBinding

class FaqAdatper(private val context: MyFAQActivity, private val dataList: ArrayList<FAQ>) :
    RecyclerView.Adapter<FaqAdatper.ViewHolder>(){


    inner class ViewHolder(private var binding: ItemFaqBinding):

        RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: FAQ){


            binding.question.text=item.ask
            binding.answer.text=item.answer

            binding.layout01.setOnClickListener {
                if(binding.layoutDetail01.visibility == View.VISIBLE) {
                    binding.layoutDetail01.visibility = View.GONE
                    binding.layoutBtn01.animate().apply {
                        duration = 300
                        rotation(0f)
                    }
                } else {
                    binding.layoutDetail01.visibility = View.VISIBLE
                    binding.layoutBtn01.animate().apply {
                        duration = 300
                        rotation(180f)
                    }
                }
            }


        }

    }

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(v: View?, pos: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemFaqBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size

    }

}