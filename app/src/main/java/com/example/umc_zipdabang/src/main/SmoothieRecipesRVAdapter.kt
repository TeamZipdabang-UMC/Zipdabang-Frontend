package com.example.umc_zipdabang.src.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding

class SmoothieRecipesRVAdapter(private val smoothieRecipesList: ArrayList<SmoothieRecipesData>): RecyclerView.Adapter<SmoothieRecipesRVAdapter.SmoothieRecipesDataViewHolder>() {
    inner class SmoothieRecipesDataViewHolder(private val viewBinding: ItemRecipesPreviewBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(smoothieRecipesData: SmoothieRecipesData) {
            val url = smoothieRecipesData.picUrl
            GlideApp.with(itemView)
                .load(url)
                .into(viewBinding.ivRecipePreview)
            viewBinding.tvRecipePreview.text = smoothieRecipesData.smoothie
            viewBinding.tvLikes.text = smoothieRecipesData.likes.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmoothieRecipesRVAdapter.SmoothieRecipesDataViewHolder {
        val viewBinding = ItemRecipesPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SmoothieRecipesDataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: SmoothieRecipesRVAdapter.SmoothieRecipesDataViewHolder, position: Int) {
        holder.bind(smoothieRecipesList[position])
    }

    override fun getItemCount(): Int = smoothieRecipesList.size
}