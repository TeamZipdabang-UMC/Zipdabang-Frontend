package com.example.umc_zipdabang.src.main

import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ItemCategoriesBinding

class CategoriesRVAdapter(private val categoriesList: ArrayList<CategoriesData>): RecyclerView.Adapter<CategoriesRVAdapter.CategoriesDataViewHolder>() {
    inner class CategoriesDataViewHolder(private val viewBinding: ItemCategoriesBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(categoriesData: CategoriesData) {
            // url이용, 글라이드 이용
            val url = categoriesData.picUrl
            GlideApp.with(itemView)
                .load(url)
                .into(viewBinding.ivCategory)
            viewBinding.tvCategory.text = categoriesData.category
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesDataViewHolder {
        val viewBinding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesDataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: CategoriesDataViewHolder, position: Int) {
        holder.bind(categoriesList[position])
        holder.itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity
                val coffeeRecipeFragment = ZipdabangRecipeCoffeeFragment()


                activity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, coffeeRecipeFragment)
                    .addToBackStack(null) // 뒤로가기 동작을 하면 이전 화면으로 돌아가게끔함.
                    .commitAllowingStateLoss()
            }
        })
    }

    override fun getItemCount(): Int = categoriesList.size

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener: OnItemClickListener
}