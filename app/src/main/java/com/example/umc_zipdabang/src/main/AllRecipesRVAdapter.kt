package com.example.umc_zipdabang.src.main

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding
import com.example.umc_zipdabang.src.main.recipe.ZipdabangRecipeDetailActivity

class AllRecipesRVAdapter(private val allRecipesList: ArrayList<AllRecipesData>): RecyclerView.Adapter<AllRecipesRVAdapter.AllRecipesDataViewHolder>() {
    inner class AllRecipesDataViewHolder(private val viewBinding: ItemRecipesPreviewBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(allRecipesData: AllRecipesData) {
            val url = allRecipesData.picUrl
            GlideApp.with(itemView)
                .load(url)
                .into(viewBinding.ivRecipePreview)
            viewBinding.tvRecipePreview.text = allRecipesData.beverage
            viewBinding.tvLikes.text = allRecipesData.likes.toString()

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ZipdabangRecipeDetailActivity::class.java)
                intent.run { itemView.context.startActivity(this)}
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllRecipesRVAdapter.AllRecipesDataViewHolder {
        val viewBinding = ItemRecipesPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllRecipesDataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: AllRecipesDataViewHolder, position: Int) {
        holder.bind(allRecipesList[position])
//        holder.itemView.setOnClickListener(object: View.OnClickListener {
//            override fun onClick(v: View?) {
//                val activity = v!!.context as AppCompatActivity
//                val zipdabangRecipeDetailActivity = ZipdabangRecipeDetailActivity()
//
//                Log.d("clickTest", "아이템 클릭 확인. position : ${holder.adapterPosition}")
//
//                when (holder.adapterPosition) {
//                    // fragment에서 Activity로 이동
//                    0 -> {
//                        val intent = Intent(activity, ZipdabangRecipeDetailActivity::class.java)
//                    }
//                }
//            }
//        })
    }

    override fun getItemCount(): Int = allRecipesList.size

}