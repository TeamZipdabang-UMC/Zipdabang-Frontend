package com.example.umc_zipdabang.src.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding

class BeverageRecipesRVAdapter(private val beverageRecipesList: ArrayList<BeverageRecipesData>): RecyclerView.Adapter<BeverageRecipesRVAdapter.BeverageRecipesDataViewHolder>() {
    inner class BeverageRecipesDataViewHolder(private val viewBinding: ItemRecipesPreviewBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(beverageRecipesData: BeverageRecipesData) {
            val url = beverageRecipesData.picUrl
            GlideApp.with(itemView)
                .load(url)
                .into(viewBinding.ivRecipePreview)
            viewBinding.tvRecipePreview.text = beverageRecipesData.beverage
            viewBinding.tvLikes.text = beverageRecipesData.likes.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeverageRecipesRVAdapter.BeverageRecipesDataViewHolder {
        val viewBinding = ItemRecipesPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeverageRecipesDataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: BeverageRecipesRVAdapter.BeverageRecipesDataViewHolder, position: Int) {
        holder.bind(beverageRecipesList[position])
    }

    override fun getItemCount(): Int = beverageRecipesList.size

}