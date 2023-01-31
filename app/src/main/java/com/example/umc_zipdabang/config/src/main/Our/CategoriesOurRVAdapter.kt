package com.example.umc_zipdabang.config.src.main.Our

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.config.src.main.Jip.src.main.GlideApp
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.*
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.CategoriesData
import com.example.umc_zipdabang.databinding.ItemCategoriesBinding


class CategoriesOurRVAdapter(val context: Context, private val categoriesList: ArrayList<CategoriesData>): RecyclerView.Adapter<CategoriesOurRVAdapter.CategoriesDataViewHolder>() {
    inner class CategoriesDataViewHolder(private val viewBinding: ItemCategoriesBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(categoriesData: CategoriesData) {
            // url이용, 글라이드 이용
            val url = categoriesData.picUrl
            GlideApp.with(itemView)
                .load(url)
                .into(viewBinding.ivCategory)
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
                        var intent = Intent(v?.context, OurRecipeCoffeeActivity::class.java)
                        v?.context?.startActivity(intent)
                    }

                    1 -> {
                        var intent = Intent(v?.context, OurRecipeBeverageActivity::class.java)
                        v?.context?.startActivity(intent)
                    }

                    2 -> {
                        var intent = Intent(v?.context, OurRecipeTeaActivity::class.java)
                        v?.context?.startActivity(intent)
                    }

                    3 -> {
                        var intent = Intent(v?.context, OurRecipeAdeActivity::class.java)
                        v?.context?.startActivity(intent)
                    }

                    4 -> {
                        var intent = Intent(v?.context, OurRecipeSmoothieActivity::class.java)
                        v?.context?.startActivity(intent)
                    }

                    else -> {
                        val intent5 = Intent(v?.context, OurRecipeWellbeingActivity::class.java)
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