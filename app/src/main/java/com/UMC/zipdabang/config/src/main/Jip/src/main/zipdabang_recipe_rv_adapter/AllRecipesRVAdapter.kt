package com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.UMC.zipdabang.databinding.ItemRecipesPreviewBinding
import com.UMC.zipdabang.config.src.main.Jip.src.main.GlideApp
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailActivity
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.AllRecipesData



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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllRecipesDataViewHolder {
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