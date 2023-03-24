package com.UMC.zipdabang.config.src.main.Home.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.UMC.zipdabang.databinding.ItemSearchBinding
import com.UMC.zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_comment.ZipdabangRecipeDetailActivity

class SearchAdpater_1(private val context: SearchActivity, private val dataList: ArrayList<Search_Receipe>) :
    RecyclerView.Adapter<SearchAdpater_1.ViewHolder>(){

    private var itemClickListener1: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(v: View?, pos: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener1 = listener
    }



    class ViewHolder(private var binding: ItemSearchBinding):

        RecyclerView.ViewHolder(binding.root){

        fun bind(context: Context, item: Search_Receipe, list : ArrayList<Search_Receipe>){

             if(list.size!=0) {
                 binding.homeTvCategory.text = item.category
                 val adapter = SearchAdapter_2(context as SearchActivity, item.receipe)
                 binding.homeRvSearchCategory.adapter = adapter
                 binding.homeRvSearchCategory.layoutManager = GridLayoutManager(context, 2)


                 adapter.setOnItemClickListener(object : SearchAdapter_2.OnItemClickListener {


                     override fun onItemClick(v: View?, pos: Int) {
                         var intent = Intent(
                             context,
                             ZipdabangRecipeDetailActivity::class.java
                         )
                         intent.putExtra("recipeId",item.receipe[pos].id.toString())
                         itemView.context.startActivity(intent)




                     }
                 })


             }





        }



        }






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemSearchBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, dataList[position],dataList)

    }

    override fun getItemCount(): Int {

        return dataList.size
    }
}