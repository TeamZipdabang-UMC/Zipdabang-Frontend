package com.example.umc_zipdabang.src.my.data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ItemRecipeBinding
import com.example.umc_zipdabang.src.my.MyChallengedoneFragment

class IntroChallengedoneRVAdapter(private val ItemRecipeList: ArrayList<ItemRecipeData>):
    RecyclerView.Adapter<IntroChallengedoneRVAdapter.ItemRecipeDataViewHolder>(){

    inner class ItemRecipeDataViewHolder(private val viewBinding: ItemRecipeBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(ItemRecipeData: ItemRecipeData){
            val url = ItemRecipeData.picUrl
            Glide.with(itemView)
                .load(url)
                .into(viewBinding.myRecipeImg)
            viewBinding.myRecipeTital.text = ItemRecipeData.tital
            viewBinding.myRecipeHeart.text = ItemRecipeData.likes.toString()

            //클릭하면 fragment로 이동
//            var fragment: Fragment = SelectPostFrag()
//            var bundle: Bundle = Bundle()
//            fragment.arguments = bundle
        }
    }

    override fun onBindViewHolder(holder: ItemRecipeDataViewHolder, position: Int) {
        holder.bind(ItemRecipeList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRecipeDataViewHolder {
        val viewBinding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemRecipeDataViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = ItemRecipeList.size

  /*  override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }*/
}