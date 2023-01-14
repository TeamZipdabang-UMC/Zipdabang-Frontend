package com.example.umc_zipdabang.src.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding

class TeaRecipesRVAdapter(private val teaRecipesList: ArrayList<TeaRecipesData>): RecyclerView.Adapter<TeaRecipesRVAdapter.TeaRecipesDataViewHolder>() {
    inner class TeaRecipesDataViewHolder(private val viewBinding: ItemRecipesPreviewBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(teaRecipesData: TeaRecipesData) {
            val url = teaRecipesData.picUrl
            GlideApp.with(itemView)
                .load(url)
                .into(viewBinding.ivRecipePreview)
            viewBinding.tvRecipePreview.text = teaRecipesData.tea
            viewBinding.tvLikes.text = teaRecipesData.likes.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeaRecipesRVAdapter.TeaRecipesDataViewHolder {
        val viewBinding = ItemRecipesPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeaRecipesDataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: TeaRecipesRVAdapter.TeaRecipesDataViewHolder, position: Int) {
        holder.bind(teaRecipesList[position])
    }

    override fun getItemCount(): Int = teaRecipesList.size

}