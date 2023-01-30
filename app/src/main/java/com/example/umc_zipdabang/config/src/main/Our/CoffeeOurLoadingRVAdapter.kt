package com.example.umc_zipdabang.config.src.main.Our

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.RecipeService
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.ZipdabangRecipeCoffeeActivity
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailActivity
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_data_class.CoffeeRecipesData
import com.example.umc_zipdabang.databinding.ItemLoadingBinding
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope


import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.converter.gson.GsonConverterFactory

class CoffeeOurLoadingRVAdapter(private val context: OurRecipeCoffeeActivity, private var dataList: ArrayList<CoffeeRecipesData>, private var idList: ArrayList<Int?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun update(list : ArrayList<CoffeeRecipesData>){
        this.dataList= list
    }

    private var VIEW_TYPE_ITEM = 0
    private var VIEW_TYPE_LOADING = 1

    class ItemViewHolder(private var binding: ItemRecipesPreviewBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: CoffeeRecipesData, idList: ArrayList<Int?>) {

            binding.tvRecipePreview.text= item.coffee
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
                        val sendIntent = Intent(itemView.context, OurRecipeDetailActivity::class.java)
                        sendIntent.putExtra("recipeId", "${selectedRecipeCommentId?.toString()}")
                        sendIntent.run { itemView.context.startActivity(this)}
                    }
                }
            }

        }
    }


    class LoadingViewHolder(private var binding: ItemLoadingBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: CoffeeRecipesData) {


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
        return when (dataList[position].coffee) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }
}