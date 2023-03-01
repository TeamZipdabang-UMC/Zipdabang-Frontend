package com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.UMC.umc_zipdabang.R
import com.bumptech.glide.Glide

class ZipdabangRecipeDetailBannerRVAdapter(var bannerImagesUrl: ArrayList<String>): RecyclerView.Adapter<ZipdabangRecipeDetailBannerRVAdapter.PagerViewHolder>() {
    inner class PagerViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_zipdabang_recipe_detail_banner, parent, false)) {
        val detailBanner = itemView.findViewById<ImageView>(R.id.iv_banner_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
//        holder.bind(bannerImagesUrl[position])

        // 글라이드 이용
        Glide.with(holder.itemView.context)
            .load(bannerImagesUrl)
            .into(holder.itemView.findViewById(R.id.iv_banner_image))


    }

    override fun getItemCount(): Int = bannerImagesUrl.size
}