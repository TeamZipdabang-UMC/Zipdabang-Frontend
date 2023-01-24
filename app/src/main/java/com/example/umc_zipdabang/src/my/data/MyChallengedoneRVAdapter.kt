package com.example.umc_zipdabang.src.my.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.databinding.ItemRecipeBinding

class MyChallengedoneRVAdapter(private val dataList:ArrayList<ItemRecipeData>) :RecyclerView.Adapter<MyChallengedoneRVAdapter.challengedoneItemViewHolder>(){

    private lateinit var binding: ItemRecipeBinding

    inner class challengedoneItemViewHolder(private val viewBinding: ItemRecipeBinding) :RecyclerView.ViewHolder(viewBinding.root){
        fun bind(ItemRecipeData: ItemRecipeData){
            val url = ItemRecipeData.picUrl
            Glide.with(itemView)
                .load(url)
                .into(viewBinding.myRecipeImg)
            viewBinding.myRecipeTital.text = ItemRecipeData.tital
            viewBinding.myRecipeHeart.text = ItemRecipeData.likes.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): challengedoneItemViewHolder {
        val viewBinding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return challengedoneItemViewHolder(viewBinding)
    }


    override fun onBindViewHolder(holder: MyChallengedoneRVAdapter.challengedoneItemViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int =dataList.size

}