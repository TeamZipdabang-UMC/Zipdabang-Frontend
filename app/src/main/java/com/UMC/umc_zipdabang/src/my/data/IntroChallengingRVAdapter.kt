package com.UMC.umc_zipdabang.src.my.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.UMC.umc_zipdabang.databinding.ItemRecipeBinding
import com.bumptech.glide.Glide


class IntroChallengingRVAdapter(private val ItemRecipeList: ArrayList<ItemRecipeChallengeData>):
    RecyclerView.Adapter<IntroChallengingRVAdapter.ItemRecipeDataViewHolder>(){

    inner class ItemRecipeDataViewHolder(private val viewBinding: ItemRecipeBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(ItemRecipeData: ItemRecipeChallengeData){
            val url = ItemRecipeData.image
            Glide.with(itemView)
                .load(url)
                .into(viewBinding.myRecipeImg)
            viewBinding.myRecipeTital.text = ItemRecipeData.name
            viewBinding.myRecipeHeart.text = ItemRecipeData.likes.toString()

            //클릭하면 fragment로 이동
        }
    }

    override fun onBindViewHolder(holder: ItemRecipeDataViewHolder, position: Int) {
        holder.bind(ItemRecipeList[position])

//        holder.itemView.layoutParams.height = width/2
//        holder.itemView.layoutParams.width = width/2
//        holder.itemView.requestLayout()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRecipeDataViewHolder {
        val viewBinding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemRecipeDataViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = ItemRecipeList.size

    /*  override fun getItemViewType(position: Int): Int {
          return super.getItemViewType(position)
      }*/
}