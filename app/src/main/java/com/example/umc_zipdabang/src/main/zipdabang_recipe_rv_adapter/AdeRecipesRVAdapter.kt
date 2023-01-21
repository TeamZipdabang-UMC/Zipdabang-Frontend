package com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.databinding.ItemLoadingBinding
import com.example.umc_zipdabang.databinding.ItemRecipesPreviewBinding
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.AdeRecipesData
import com.example.umc_zipdabang.src.main.GlideApp
import com.example.umc_zipdabang.src.main.ZipdabangRecipeAdeActivity
import com.example.umc_zipdabang.src.main.ZipdabangRecipeTeaActivity
import com.example.umc_zipdabang.src.main.zipdabang_recipe_data_class.TeaRecipesData
import com.example.umc_zipdabang.src.main.zipdabang_recipe_detail.ZipdabangRecipeDetailAdeActivity

class AdeRecipesRVAdapter(private val context: ZipdabangRecipeAdeActivity, private var dataList: ArrayList<AdeRecipesData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun update(list : ArrayList<AdeRecipesData>){
        this.dataList= list
    }

    private var VIEW_TYPE_ITEM = 0
    private var VIEW_TYPE_LOADING = 1

    class ItemViewHolder(private var binding: ItemRecipesPreviewBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: AdeRecipesData) {

            binding.tvRecipePreview.text= item.ade
            binding.tvLikes.text=item.likes.toString()
            Glide.with(context).load(item.picUrl).into(binding.ivRecipePreview)
            binding.ivRecipePreview.clipToOutline = true

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ZipdabangRecipeDetailAdeActivity::class.java)
                intent.run { itemView.context.startActivity(this)}
            }

        }
    }


    class LoadingViewHolder(private var binding: ItemLoadingBinding) :

        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: AdeRecipesData) {


        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(context, dataList[position])
        } else if (holder is LoadingViewHolder) {
            holder.bind(context, dataList[position])
        }
    }


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val binding =
                ItemRecipesPreviewBinding.inflate(LayoutInflater.from(context), parent, false)
            return ItemViewHolder(binding)
        } else {
            val binding =
                ItemLoadingBinding.inflate(LayoutInflater.from(context), parent, false)
            return LoadingViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position].ade) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM

        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }



//class AdeRecipesRVAdapter(private val adeRecipesList: ArrayList<AdeRecipesData>): RecyclerView.Adapter<AdeRecipesRVAdapter.AdeRecipesDataViewHolder>() {
//    inner class AdeRecipesDataViewHolder(private val viewBinding: ItemRecipesPreviewBinding): RecyclerView.ViewHolder(viewBinding.root) {
//        fun bind(adeRecipesData: AdeRecipesData) {
//            val url = adeRecipesData.picUrl
//            GlideApp.with(itemView)
//                .load(url)
//                .into(viewBinding.ivRecipePreview)
//            viewBinding.tvRecipePreview.text = adeRecipesData.ade
//            viewBinding.tvLikes.text = adeRecipesData.likes.toString()
//
//            itemView.setOnClickListener {
//                val intent = Intent(itemView.context, ZipdabangRecipeDetailAdeActivity::class.java)
//                intent.run { itemView.context.startActivity(this)}
//            }
//        }
//
////        itemView.setOnClickListener {
////            val intent = Intent(itemView.context, ZipdabangRecipeDetailAdeActivity::class.java)
////            intent.run { itemView.context.startActivity(this)}
////        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return super.getItemViewType(position)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdeRecipesDataViewHolder {
//        val viewBinding = ItemRecipesPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return AdeRecipesDataViewHolder(viewBinding)
//    }
//
//    override fun onBindViewHolder(holder: AdeRecipesDataViewHolder, position: Int) {
//        holder.bind(adeRecipesList[position])
//    }
//
//    override fun getItemCount(): Int = adeRecipesList.size
//
}