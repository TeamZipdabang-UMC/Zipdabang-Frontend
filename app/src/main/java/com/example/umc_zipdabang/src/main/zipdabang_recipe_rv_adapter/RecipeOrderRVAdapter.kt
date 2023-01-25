package com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.databinding.ItemRecipeOrderBinding
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.RecipeOrder

class RecipeOrderRVAdapter(private val recipeOrderList: ArrayList<RecipeOrder>): RecyclerView.Adapter<RecipeOrderRVAdapter.RecipeOrderViewHolder>() {
    inner class RecipeOrderViewHolder(private val viewBinding: ItemRecipeOrderBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(recipeOrder: RecipeOrder) {
            viewBinding.tvOrderNum.text = recipeOrder.stepNum.toString()
            viewBinding.tvOrderDescription.text = recipeOrder.stepDesc
            Glide.with(itemView).load(recipeOrder.stepImgUrl).into(viewBinding.ivOrderDescription)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeOrderViewHolder {
        val viewBinding = ItemRecipeOrderBinding.inflate(LayoutInflater.from(parent.context))
        return RecipeOrderViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecipeOrderViewHolder, position: Int) {
        holder.bind(recipeOrderList[position])
    }

    override fun getItemCount(): Int = recipeOrderList.size
}