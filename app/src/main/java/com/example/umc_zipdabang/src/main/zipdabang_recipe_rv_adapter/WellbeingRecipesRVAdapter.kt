package com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding
import com.example.umc_zipdabang.src.main.GlideApp
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.WellbeingRecipesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_detail.ZipdabangRecipeDetailWellbeingActivity

class WellbeingRecipesRVAdapter(private val wellbeingRecipesList: ArrayList<WellbeingRecipesData>): RecyclerView.Adapter<WellbeingRecipesRVAdapter.WellbeingRecipesDataViewHolder>() {
    inner class WellbeingRecipesDataViewHolder(private val viewBinding: ItemRecipesPreviewBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(wellbeingRecipesData: WellbeingRecipesData) {
            val url = wellbeingRecipesData.picUrl
            GlideApp.with(itemView)
                .load(url)
                .into(viewBinding.ivRecipePreview)
            viewBinding.tvRecipePreview.text = wellbeingRecipesData.wellbeing
            viewBinding.tvLikes.text = wellbeingRecipesData.likes.toString()

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ZipdabangRecipeDetailWellbeingActivity::class.java)
                intent.run { itemView.context.startActivity(this)}
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WellbeingRecipesDataViewHolder {
        val viewBinding = ItemRecipesPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WellbeingRecipesDataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: WellbeingRecipesDataViewHolder, position: Int) {
        holder.bind(wellbeingRecipesList[position])
    }

    override fun getItemCount(): Int = wellbeingRecipesList.size

}