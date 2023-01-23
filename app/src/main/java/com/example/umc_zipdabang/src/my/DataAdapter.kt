package com.example.umc_zipdabang.src.my

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.ItemDataBinding

class DataAdapter(private val dataList: ArrayList<Data>): RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    private lateinit var binding: ItemDataBinding

    inner class DataViewHolder(private val viewBinding:ItemDataBinding):RecyclerView.ViewHolder(viewBinding.root){
        //호출될때의 함수
        fun bind(data: Data){
            viewBinding.myImg.text = data.img
            viewBinding.myTitle.text = data.tital
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position])
        //adapter에 어떠한 item을 누르던간에 누르면, item 삭제되기.
        binding.root.setOnClickListener{
            dataList.removeAt(position)
        }
    }

    override fun getItemCount(): Int =dataList.size
}