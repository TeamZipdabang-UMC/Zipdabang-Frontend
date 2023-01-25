package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.Ingredient
import com.example.umc_zipdabang.databinding.ItemIngredientsBinding


class IngredientsRVAdapter(private val ingredientsList: ArrayList<Ingredient>): RecyclerView.Adapter<IngredientsRVAdapter.IngredientsViewHolder>() {
    inner class IngredientsViewHolder(private val viewBinding: ItemIngredientsBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(ingredient: Ingredient) {
//            val ingredients = ingredient.ingredient
//            val amounts = ingredient.amount
            viewBinding.tvIngredientName.text = ingredient.ingredient
            viewBinding.tvIngredientAmount.text = ingredient.amount
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val viewBinding = ItemIngredientsBinding.inflate(LayoutInflater.from(parent.context))
        return IngredientsViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind(ingredientsList[position])
    }

    override fun getItemCount(): Int = ingredientsList.size
}