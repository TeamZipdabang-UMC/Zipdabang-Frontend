package com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_rv_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.databinding.ItemCommentBinding

class CommentItemAdapter(private val mList: ArrayList<ImageView>): RecyclerView.Adapter<CommentItemAdapter.ViewHolder>() {
    private var onClickListener: CommentClickInterface? = null

    fun commentItemClickFunc(pOnClick: CommentClickInterface) {
        this.onClickListener = pOnClick
    }

    inner class ViewHolder(private val itemCommentBinding: ItemCommentBinding): RecyclerView.ViewHolder(itemCommentBinding.root) {
        fun bind(mItemData: ImageView) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                itemCommentBinding.ivCommentControl.setOnClickListener {
                    onClickListener?.commentItemClickListener(mItemData, itemCommentBinding)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
}