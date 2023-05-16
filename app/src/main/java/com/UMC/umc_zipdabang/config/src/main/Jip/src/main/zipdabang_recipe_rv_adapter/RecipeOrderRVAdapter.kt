package com.UMC.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.UMC.umc_zipdabang.R
import com.UMC.umc_zipdabang.databinding.ItemRecipeOrderBinding
import com.bumptech.glide.Glide
import com.UMC.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.Step

class RecipeOrderRVAdapter(private val recipeOrderList: ArrayList<Step>): RecyclerView.Adapter<RecipeOrderRVAdapter.RecipeOrderViewHolder>() {
    inner class RecipeOrderViewHolder(private val viewBinding: ItemRecipeOrderBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(step: Step) {
            viewBinding.tvOrderNum.text = step.step.toString()
            viewBinding.tvOrderDescription.text = step.stepDescription.toString()
            Glide.with(itemView)
                .load(step.stepImageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(viewBinding.ivOrderDescription)


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