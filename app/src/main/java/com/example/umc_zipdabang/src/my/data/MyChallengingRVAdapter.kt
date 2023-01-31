package com.example.umc_zipdabang.src.my.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.databinding.ItemRecipeBinding


class MyChallengingRVAdapter(private val dataList: ArrayList<ItemRecipeChallengeData>)
    : RecyclerView.Adapter<MyChallengingRVAdapter.challengingItemViewHolder>(){

    private lateinit var binding: ItemRecipeBinding

    //viewholder 객체
    inner class challengingItemViewHolder(private val viewBinding: ItemRecipeBinding) :RecyclerView.ViewHolder(viewBinding.root){
        fun bind(ItemRecipeChallengeData: ItemRecipeChallengeData){
            val url = ItemRecipeChallengeData.image
            Glide.with(itemView)
                .load(url)
                .into(viewBinding.myRecipeImg)
            viewBinding.myRecipeTital.text = ItemRecipeChallengeData.name
            viewBinding.myRecipeHeart.text = ItemRecipeChallengeData.likes.toString()
        }
    }

    //viewholder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): challengingItemViewHolder {
        val viewBinding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return challengingItemViewHolder(viewBinding)
    }

    //viewholder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: challengingItemViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int =dataList.size

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

}