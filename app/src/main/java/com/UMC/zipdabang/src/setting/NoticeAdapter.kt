package com.UMC.zipdabang.src.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.UMC.umc_zipdabang.databinding.ItemMyNoticeBinding

class NoticeAdapter(private val context: MyNoticeActivity, private val dataList: ArrayList<Notice>) :
    RecyclerView.Adapter<NoticeAdapter.ViewHolder>(){


    inner class ViewHolder(private var binding: ItemMyNoticeBinding):

        RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: Notice){


            binding.myNoticeTitle.text= item.title
            binding.myNoticeDate.text=item.date


            binding.arrow.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val pos=getAdapterPosition()
                    if (pos != RecyclerView.NO_POSITION) {
                        itemClickListener?.onItemClick(p0, pos)
                    }
                }
            })


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
        val binding=ItemMyNoticeBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size

    }

}