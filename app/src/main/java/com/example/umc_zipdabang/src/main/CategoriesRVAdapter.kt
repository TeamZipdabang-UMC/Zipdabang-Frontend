package com.example.umc_zipdabang.src.main

import android.text.TextUtils.replace
import android.util.Log
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
                val beverageRecipeFragment = ZipdabangRecipeBeverageFragment()
                val teaRecipeFragment = ZipdabangRecipeTeaFragment()
                val adeRecipeFragment = ZipdabangRecipeAdeFragment()
                val smoothieRecipeFragment = ZipdabangRecipeSmoothieFragment()
                val wellbeingRecipeFragment = ZipdabangRecipeWellbeingFragment()

                Log.d("clickTest", "아이템 클릭 확인. position : ${holder.adapterPosition}")

                when (holder.adapterPosition) {
                    0 -> {
                        activity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, coffeeRecipeFragment)
                            .addToBackStack(null) // 뒤로가기 동작을 하면 이전 화면으로 돌아가게끔함.
                            .commitAllowingStateLoss()
                    }

                    1 -> {
                        activity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, beverageRecipeFragment)
                            .addToBackStack(null) // 뒤로가기 동작을 하면 이전 화면으로 돌아가게끔함.
                            .commitAllowingStateLoss()
                    }

                    2 -> {
                        activity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, teaRecipeFragment)
                            .addToBackStack(null) // 뒤로가기 동작을 하면 이전 화면으로 돌아가게끔함.
                            .commitAllowingStateLoss()
                    }

                    3 -> {
                        activity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, adeRecipeFragment)
                            .addToBackStack(null) // 뒤로가기 동작을 하면 이전 화면으로 돌아가게끔함.
                            .commitAllowingStateLoss()
                    }

                    4 -> {
                        activity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, smoothieRecipeFragment)
                            .addToBackStack(null) // 뒤로가기 동작을 하면 이전 화면으로 돌아가게끔함.
                            .commitAllowingStateLoss()
                    }

                    5 -> {
                        activity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, wellbeingRecipeFragment)
                            .addToBackStack(null) // 뒤로가기 동작을 하면 이전 화면으로 돌아가게끔함.
                            .commitAllowingStateLoss()
                    }
                }
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