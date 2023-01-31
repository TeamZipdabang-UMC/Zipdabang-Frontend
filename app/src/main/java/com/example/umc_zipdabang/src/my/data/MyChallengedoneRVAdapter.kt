package com.example.umc_zipdabang.src.my.data

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.config.src.main.Home.HomeMainActivity
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailActivity
import com.example.umc_zipdabang.databinding.ItemRecipeBinding
import com.example.umc_zipdabang.src.my.MyChallengedoneFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.converter.gson.GsonConverterFactory

class MyChallengedoneRVAdapter(private val context: HomeMainActivity, private val dataList:ArrayList<ItemRecipeChallengeData>)
    :RecyclerView.Adapter<MyChallengedoneRVAdapter.challengedoneItemViewHolder>(){

    private lateinit var binding: ItemRecipeBinding

    inner class challengedoneItemViewHolder(private val viewBinding: ItemRecipeBinding) :RecyclerView.ViewHolder(viewBinding.root){
        fun bind(context: Context, ItemRecipeChallengeData: ItemRecipeChallengeData){
            val url = ItemRecipeChallengeData.image
            Glide.with(itemView)
                .load(url)
                .into(viewBinding.myRecipeImg)
            viewBinding.myRecipeTital.text = ItemRecipeChallengeData.name
            viewBinding.myRecipeHeart.text = ItemRecipeChallengeData.likes.toString()

            itemView.setOnClickListener{
                GlobalScope.launch(Dispatchers.IO){

                    withContext(Dispatchers.Main){
                        val selectId = dataList[adapterPosition].userId.toString()
                        Log.d("통신","${selectId}")
                        val intent = Intent(itemView.context, ZipdabangRecipeDetailActivity::class.java)
                        intent.putExtra("recipeId","${selectId}")
                        intent.run{
                            itemView.context.startActivity(this)
                        }
                    }


                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): challengedoneItemViewHolder {
        val viewBinding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return challengedoneItemViewHolder(viewBinding)
    }


    override fun onBindViewHolder(holder: MyChallengedoneRVAdapter.challengedoneItemViewHolder, position: Int) {
        holder.bind(context, dataList[position])

    }

    override fun getItemCount(): Int =dataList.size

}