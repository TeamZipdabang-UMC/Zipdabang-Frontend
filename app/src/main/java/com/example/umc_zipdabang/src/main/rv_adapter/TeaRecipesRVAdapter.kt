package com.example.umc_zipdabang.src.main.rv_adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding
import com.example.umc_zipdabang.src.main.GlideApp
import com.example.umc_zipdabang.src.main.data_class.TeaRecipesData
import com.example.umc_zipdabang.src.main.recipe_detail.ZipdabangRecipeDetailTeaActivity

class TeaRecipesRVAdapter(private val teaRecipesList: ArrayList<TeaRecipesData>): RecyclerView.Adapter<TeaRecipesRVAdapter.TeaRecipesDataViewHolder>() {
    inner class TeaRecipesDataViewHolder(private val viewBinding: ItemRecipesPreviewBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(teaRecipesData: TeaRecipesData) {
            val url = teaRecipesData.picUrl
            GlideApp.with(itemView)
                .load(url)
                .into(viewBinding.ivRecipePreview)
            viewBinding.tvRecipePreview.text = teaRecipesData.tea
            viewBinding.tvLikes.text = teaRecipesData.likes.toString()

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ZipdabangRecipeDetailTeaActivity::class.java)
                intent.run { itemView.context.startActivity(this)}
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeaRecipesDataViewHolder {
        val viewBinding = ItemRecipesPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeaRecipesDataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: TeaRecipesDataViewHolder, position: Int) {
        holder.bind(teaRecipesList[position])
    }

    override fun getItemCount(): Int = teaRecipesList.size

}