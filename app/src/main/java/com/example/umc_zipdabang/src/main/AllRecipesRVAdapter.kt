package com.example.umc_zipdabang.src.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding
import com.example.umc_zipdabang.src.main.GlideApp.init
import com.example.umc_zipdabang.src.main.recipe.ZipdabangRecipeDetailFragment

class AllRecipesRVAdapter(private val allRecipesList: ArrayList<AllRecipesData>): RecyclerView.Adapter<AllRecipesRVAdapter.AllRecipesDataViewHolder>() {
    inner class AllRecipesDataViewHolder(private val viewBinding: ItemRecipesPreviewBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(allRecipesData: AllRecipesData) {
            val url = allRecipesData.picUrl
            GlideApp.with(itemView)
                .load(url)
                .into(viewBinding.ivRecipePreview)
            viewBinding.tvRecipePreview.text = allRecipesData.beverage
            viewBinding.tvLikes.text = allRecipesData.likes.toString()

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
        holder.itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity
                val zipdabangRecipeDetailFragment = ZipdabangRecipeDetailFragment()

                Log.d("clickTest", "아이템 클릭 확인. position : ${holder.adapterPosition}")

                when (holder.adapterPosition) {
                    0 -> {
                        activity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, zipdabangRecipeDetailFragment)
                            .addToBackStack(null) // 뒤로가기 동작을 하면 이전 화면으로 돌아가게끔함.
                            .commitAllowingStateLoss()
                    }
                }
            }
        })
    }

    override fun getItemCount(): Int = allRecipesList.size

}