package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.IngredientDetail
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailActivity
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.Ingredient
import com.example.umc_zipdabang.databinding.ItemIngredientsBinding
import kotlinx.android.synthetic.main.item_ingredients.view.*
import kotlinx.coroutines.withContext


class IngredientsRVAdapter(private val ingredientsList: ArrayList<IngredientDetail>): RecyclerView.Adapter<IngredientsRVAdapter.IngredientsViewHolder>() {
    inner class IngredientsViewHolder(private val viewBinding: ItemIngredientsBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(ingredient: IngredientDetail) {
//            val ingredients = ingredient.ingredient
//            val amounts = ingredient.amount
            viewBinding.tvIngredientName.text = ingredient.name
            viewBinding.tvIngredientAmount.text = ingredient.quantity
            viewBinding.ivSearch.setOnClickListener {

                val shoppingIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://search.shopping.naver.com/search/all?query=${ingredient.name}"))
                itemView.context.startActivity(shoppingIntent)
            }



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