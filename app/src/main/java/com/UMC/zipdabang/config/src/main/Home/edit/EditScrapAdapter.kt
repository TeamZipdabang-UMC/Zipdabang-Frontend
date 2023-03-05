package com.UMC.zipdabang.config.src.main.Home.edit

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.UMC.zipdabang.R
import com.UMC.zipdabang.databinding.ItemMyscrapEditBinding
import com.bumptech.glide.Glide
import com.UMC.zipdabang.config.src.main.Home.Scrap.My_Scrap
import com.UMC.zipdabang.config.src.main.Home.Scrap.My_Scrapp
import java.sql.Types.NULL


class EditScrapAdapter(private val context: EditScrapActivity, private val dataList: ArrayList<My_Scrapp>) :
    RecyclerView.Adapter<EditScrapAdapter.ViewHolder>(){

    private var itemClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(v: View?, pos: Int, tag : String )
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    inner class ViewHolder(private var binding: ItemMyscrapEditBinding):

        RecyclerView.ViewHolder(binding.root){

        var deletelist : ArrayList<My_Scrap> = arrayListOf()




        fun bind(context: Context, item: My_Scrapp, dataList: ArrayList<My_Scrapp>){
            binding.checkIv.setTag("0")
            binding.homeIvCategory1.setColorFilter(NULL)
            binding.checkIv.setImageResource(R.drawable.uncheck_round)
            binding.homeTvCategory1.text= item.title
            binding.homeTvHeart1.text=item.heart.toString()
            Glide.with(context).load(item.ImageUrl).into(binding.homeIvCategory1)
            binding.homeIvCategory1.clipToOutline = true

            //ADAPTER(,,)

            binding.checkIv.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val pos=getAdapterPosition()


                    if(binding.checkIv.getTag()=="0")
                    {binding.checkIv.setImageResource(R.drawable.check_round)
                        binding.homeIvCategory1.setColorFilter(Color.parseColor("#80FDEC65"))
                        binding.checkIv.setTag("1")}
                    else {
                        binding.checkIv.setImageResource(R.drawable.uncheck_round)
                        binding.homeIvCategory1.setColorFilter(NULL)
                        binding.checkIv.setTag("0")
                    }
                    if (pos != RecyclerView.NO_POSITION) {
                            itemClickListener?.onItemClick(p0, pos, binding.checkIv.getTag().toString())


                    }





                }
            })




        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemMyscrapEditBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, dataList[position],dataList)
      //  holder.dd()
    }

    override fun getItemCount(): Int {

        return dataList.size
    }

}