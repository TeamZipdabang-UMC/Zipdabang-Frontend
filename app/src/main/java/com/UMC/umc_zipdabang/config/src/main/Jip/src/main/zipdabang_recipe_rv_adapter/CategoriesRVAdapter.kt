package com.UMC.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.UMC.umc_zipdabang.databinding.ItemCategoriesBinding
import com.UMC.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.*
import com.UMC.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.CategoriesData


class CategoriesRVAdapter(val context: Context, private val categoriesList: ArrayList<CategoriesData>): RecyclerView.Adapter<CategoriesRVAdapter.CategoriesDataViewHolder>() {
    inner class CategoriesDataViewHolder(private val viewBinding: ItemCategoriesBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(categoriesData: CategoriesData) {
            // url이용, 글라이드 이용
            viewBinding.ivCategory.setImageResource(categoriesData.picUrl)
            viewBinding.tvCategory.text = categoriesData.category
//            itemView.setOnClickListener()

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
        holder?.bind(categoriesList[position])

        holder.itemView.setOnClickListener(object: View.OnClickListener {

            // 댓글삭제 : 데이터셋

            override fun onClick(v: View?) {
//                val activity = v!!.context as AppCompatActivity

                Log.d("clickTest", "아이템 클릭 확인. position : ${holder.adapterPosition}")

                when (holder.adapterPosition) {
                    0 -> {
                        var intent = Intent(v?.context, ZipdabangRecipeCoffeeActivity::class.java)
                        v?.context?.startActivity(intent)
                    }

                    1 -> {
                        var intent = Intent(v?.context, ZipdabangRecipeBeverageActivity::class.java)
                        v?.context?.startActivity(intent)
                    }

                    2 -> {
                        var intent = Intent(v?.context, ZipdabangRecipeTeaActivity::class.java)
                        v?.context?.startActivity(intent)
                    }

                    3 -> {
                        var intent = Intent(v?.context, ZipdabangRecipeAdeActivity::class.java)
                        v?.context?.startActivity(intent)
                    }

                    4 -> {
                        var intent = Intent(v?.context, ZipdabangRecipeSmoothieActivity::class.java)
                        v?.context?.startActivity(intent)
                    }

                    else -> {
                        val intent5 = Intent(v?.context, ZipdabangRecipeWellbeingActivity::class.java)
                        v?.context?.startActivity(intent5)
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