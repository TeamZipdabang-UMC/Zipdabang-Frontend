package com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.UMC.umc_zipdabang.databinding.ItemLoadingBinding
import com.UMC.umc_zipdabang.databinding.ItemRecipesPreviewBinding
import com.bumptech.glide.Glide
import com.UMC.zipdabang.config.src.main.Home.HomeMainActivity
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.RecipeService
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailActivity
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.AllRecipesData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.converter.gson.GsonConverterFactory

class LoadingRVAdapter(private val context: HomeMainActivity, private var dataList: ArrayList<AllRecipesData>, private var idList: ArrayList<Int?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun update(list : ArrayList<AllRecipesData>){
        this.dataList= list
    }

    private var VIEW_TYPE_ITEM = 0
    private var VIEW_TYPE_LOADING = 1

    class ItemViewHolder(private var binding: ItemRecipesPreviewBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: AllRecipesData,
                 idList: ArrayList<Int?>) {

            binding.tvRecipePreview.text= item.beverage
            binding.tvLikes.text=item.likes.toString()
            Glide.with(context)
                .load(item.picUrl)
                .into(binding.ivRecipePreview)
            binding.ivRecipePreview.clipToOutline = true

            itemView.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO) {
                    val recipeSelectRetrofit = retrofit2.Retrofit.Builder()
                        .baseUrl("http://zipdabang.store:3000")
                        .addConverterFactory(GsonConverterFactory.create()).build()
                    val recipeSelectService = recipeSelectRetrofit.create(RecipeService::class.java)
                    withContext(Dispatchers.Main) {
                        val recipeLocation = adapterPosition
                        val selectedRecipeCommentId = idList[adapterPosition]
                        Log.d("클릭된 레시피의 Id", "${selectedRecipeCommentId}")
                        val sendIntent = Intent(itemView.context, ZipdabangRecipeDetailActivity::class.java)
                        sendIntent.putExtra("recipeId", "${selectedRecipeCommentId?.toString()}")
                        sendIntent.run { itemView.context.startActivity(this)}
                    }
                }
            }
        }
    }


    class LoadingViewHolder(private var binding: ItemLoadingBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: AllRecipesData) {


        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ItemViewHolder) {
            holder.bind(context, dataList[position], idList)
        } else if (holder is LoadingViewHolder) {
            holder.bind(context, dataList[position])
        }
    }


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val binding =
                ItemRecipesPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemViewHolder(binding)
        } else {
            val binding =
                ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LoadingViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position].beverage) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }
}