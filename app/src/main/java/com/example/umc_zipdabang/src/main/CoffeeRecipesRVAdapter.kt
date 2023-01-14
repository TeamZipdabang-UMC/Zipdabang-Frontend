package com.example.umc_zipdabang.src.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding

class CoffeeRecipesRVAdapter(private val coffeeRecipesList: ArrayList<CoffeeRecipesData>): RecyclerView.Adapter<CoffeeRecipesRVAdapter.CoffeeRecipesDataViewHolder>() {
    inner class CoffeeRecipesDataViewHolder(private val viewBinding: ItemRecipesPreviewBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(coffeeRecipesData: CoffeeRecipesData) {
            val url = coffeeRecipesData.picUrl
            GlideApp.with(itemView)
                .load(url)
                .into(viewBinding.ivRecipePreview)
            viewBinding.tvRecipePreview.text = coffeeRecipesData.coffee
            viewBinding.tvLikes.text = coffeeRecipesData.likes.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeRecipesRVAdapter.CoffeeRecipesDataViewHolder {
        val viewBinding = ItemRecipesPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoffeeRecipesDataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: CoffeeRecipesRVAdapter.CoffeeRecipesDataViewHolder, position: Int) {
        holder.bind(coffeeRecipesList[position])
    }

    override fun getItemCount(): Int = coffeeRecipesList.size

}