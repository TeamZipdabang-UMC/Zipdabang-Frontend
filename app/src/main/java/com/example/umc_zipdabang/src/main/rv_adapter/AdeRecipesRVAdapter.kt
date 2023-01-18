package com.example.umc_zipdabang.src.main.rv_adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding
import com.example.umc_zipdabang.src.main.data_class.AdeRecipesData
import com.example.umc_zipdabang.src.main.GlideApp
import com.example.umc_zipdabang.src.main.recipe_detail.ZipdabangRecipeDetailAdeActivity

class AdeRecipesRVAdapter(private val adeRecipesList: ArrayList<AdeRecipesData>): RecyclerView.Adapter<AdeRecipesRVAdapter.AdeRecipesDataViewHolder>() {
    inner class AdeRecipesDataViewHolder(private val viewBinding: ItemRecipesPreviewBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(adeRecipesData: AdeRecipesData) {
            val url = adeRecipesData.picUrl
            GlideApp.with(itemView)
                .load(url)
                .into(viewBinding.ivRecipePreview)
            viewBinding.tvRecipePreview.text = adeRecipesData.ade
            viewBinding.tvLikes.text = adeRecipesData.likes.toString()

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ZipdabangRecipeDetailAdeActivity::class.java)
                intent.run { itemView.context.startActivity(this)}
            }
        }

//        itemView.setOnClickListener {
//            val intent = Intent(itemView.context, ZipdabangRecipeDetailAdeActivity::class.java)
//            intent.run { itemView.context.startActivity(this)}
//        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdeRecipesDataViewHolder {
        val viewBinding = ItemRecipesPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdeRecipesDataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: AdeRecipesDataViewHolder, position: Int) {
        holder.bind(adeRecipesList[position])
    }

    override fun getItemCount(): Int = adeRecipesList.size

}