package com.example.umc_zipdabang.src.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding

class AllRecipesRVAdapter(private val allRecipesList: ArrayList<AllRecipesData>): RecyclerView.Adapter<AllRecipesRVAdapter.AllRecipesDataViewHolder>() {
    inner class AllRecipesDataViewHolder(private val viewBinding: ItemRecipesPreviewBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(allRecipesData: AllRecipesData) {
            val url = allRecipesData.picUrl
            GlideApp.with(itemView)
                .load(url)
                .into(viewBinding.ivRecipePreview)
            viewBinding.tvRecipePreview.text = allRecipesData.beverage
            viewBinding.tvLikes.text = allRecipesData.likes.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllRecipesDataViewHolder {
        val viewBinding = ItemRecipesPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllRecipesDataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: AllRecipesDataViewHolder, position: Int) {
        holder.bind(allRecipesList[position])
    }

    override fun getItemCount(): Int = allRecipesList.size
}