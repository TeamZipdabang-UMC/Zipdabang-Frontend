package com.example.umc_zipdabang.src.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.R

class ZipdabangRecipeBannerAdapter: ListAdapter<ZipdabangRecipeBanner, ZipdabangRecipeBannerAdapter.ZipdabangRecipeBannerViewHolder>(BannerDiffCallback()) {

    // ListAdapter?
    // 역할 : 데이터의 리스트를 받아 0번째부터 순차적으로 뷰홀더와 바인딩함.
    // 이 때 레이아웃은 유지한 채로 데이터만 업데이트를 함으로써 성능 향상을 도모해야함.
    // 이를 지원하는 것이 ListAdapter
    // 데이터가 변경되었음이 판명난 이후에야 레이아웃을 업데이트함.

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ZipdabangRecipeBannerViewHolder {
        // 뷰홀더가 생성 되어야 할 때,
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_zipdabang_recipe_banner, parent, false)
        //인자로 레이아웃을 넣어서 inflate시킨다.
        return ZipdabangRecipeBannerViewHolder(view)

    }

    override fun onBindViewHolder(holder: ZipdabangRecipeBannerViewHolder, position: Int) {
        //onCreateviewHolder 호출 후, 뷰홀더가 잘 생성이 된다면 해당 홀더가 인자로 전달되는 것(위와 같이)
        // 홀더가 잘 생성되었으니, 이 홀더에 데이터를 바인딩함.
        // 해당 포지션의 데이터 타입을 반환함.
        holder.bind(getItem(position))
    }

    // 하위 클래스는 두 override된 메소드 아래에 위치시킴.
    class ZipdabangRecipeBannerViewHolder(view: View): RecyclerView.ViewHolder(view) {
        //여기서 View는, Banner에서 inflate시킬 layout을 의미.

        private val bannerImageView = view.findViewById<ImageView>(R.id.iv_banner_image)
        // 인자로 Banner 객체*(데이터클래스)를 받아 바인딩해줘야함.
        fun bind(banner: ZipdabangRecipeBanner) {
            GlideApp.with(itemView)
                .load(banner.backgroundImageUrl)
                .into(bannerImageView)
        }
    }
}

// 두 객체가 다른지 비교하는 기준. -> 리스트어댑터가 다른 사진을 보여주는 척도.
class BannerDiffCallback: DiffUtil.ItemCallback<ZipdabangRecipeBanner>() {
    override fun areItemsTheSame(
        oldItem: ZipdabangRecipeBanner,
        newItem: ZipdabangRecipeBanner
    ): Boolean {
        return oldItem.productId == newItem.productId
    }

    // 아이디가 동일하다면, 아래 메소드도 실행됨.
    override fun areContentsTheSame(
        oldItem: ZipdabangRecipeBanner,
        newItem: ZipdabangRecipeBanner
    ): Boolean {
        return oldItem == newItem
    }

}