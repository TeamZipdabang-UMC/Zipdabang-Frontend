package com.UMC.zipdabang.src.my.data

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.ItemRecipeBinding
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.config.src.main.Jip.src.main.MainActivity
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailActivity
import com.example.umc_zipdabang.src.my.MyChallengingActivity

import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailActivity
import com.UMC.zipdabang.src.my.MyChallengingActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyChallengingRVAdapter(private val context: MyChallengingActivity, private val dataList: ArrayList<ItemRecipeChallengeData>, private val width :Double, private val height: Int)
    : RecyclerView.Adapter<MyChallengingRVAdapter.challengingItemViewHolder>(){

    private lateinit var binding: ItemRecipeBinding

    val displayMetrics = DisplayMetrics()

    val pxWidth = displayMetrics.widthPixels
    val pxHeight = displayMetrics.heightPixels

    //viewholder 객체
    inner class challengingItemViewHolder(private val viewBinding: ItemRecipeBinding) :RecyclerView.ViewHolder(viewBinding.root){
        fun bind(context: Context, ItemRecipeChallengeData: ItemRecipeChallengeData){
            val url = ItemRecipeChallengeData.image
            Glide.with(itemView)
                .load(url)
                .into(viewBinding.myRecipeImg)
            viewBinding.myRecipeTital.text = ItemRecipeChallengeData.name
            viewBinding.myRecipeHeart.text = ItemRecipeChallengeData.likes.toString()

            itemView.setOnClickListener {

                GlobalScope.launch(Dispatchers.IO) {

                    withContext(Dispatchers.Main) {
                        val selectId = dataList[adapterPosition].userId.toString()
                        Log.d("통신", "${selectId}")
                        val intent =
                            Intent(itemView.context, ZipdabangRecipeDetailActivity::class.java)
                        intent.putExtra("recipeId", "${selectId}")
                        intent.run {
                            itemView.context.startActivity(this)
                        }
                    }
                }
            }
        }
    }

    //viewholder 만들어질 때 실행할 동작
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): challengingItemViewHolder {
        val viewBinding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return challengingItemViewHolder(viewBinding)
    }

    //viewholder가 실제로 데이터를 표시해야 할 때 호출되는 함수
    override fun onBindViewHolder(holder: challengingItemViewHolder, position: Int) {
        holder.bind(context, dataList[position])

        holder.itemView.layoutParams.height = (width/1.6).toInt()
        holder.itemView.layoutParams.width = (width/2).toInt()
        holder.itemView.requestLayout()
    }

    override fun getItemCount(): Int =dataList.size

    /*override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }*/


}