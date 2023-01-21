package com.example.umc_zipdabang.src.my

import android.content.ClipData.Item
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.ItemDataBinding

class DataAdapter(private val dataList: ArrayList<Data>): RecyclerView.Adapter<DataAdapter.DataViewHolder>() {
    private lateinit var binding: ItemDataBinding

    inner class DataViewHolder(private val viewBinding:ItemDataBinding):RecyclerView.ViewHolder(viewBinding.root){
        //호출될때의 함수
        fun bind(data: Data){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {

    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

    }

    override fun getItemCount(): Int =dataList.size
}