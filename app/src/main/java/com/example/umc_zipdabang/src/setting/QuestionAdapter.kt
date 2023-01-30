package com.example.umc_zipdabang.src.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.ItemQuestionBinding

class QuestionAdapter(private val context: FirstQuestionActivity, private val dataList: ArrayList<question>) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>(){


    inner class ViewHolder(private var binding: ItemQuestionBinding):

        RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: question){


            binding.questTitle.text= item.title
            binding.questDate.text=item.date


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
        val binding=ItemQuestionBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size

    }

}