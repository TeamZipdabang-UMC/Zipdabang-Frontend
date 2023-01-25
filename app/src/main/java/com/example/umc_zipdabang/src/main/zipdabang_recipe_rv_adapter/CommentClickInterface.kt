package com.example.umc_zipdabang.src.main.zipdabang_recipe_rv_adapter

import android.media.Image
import android.widget.ImageView
import com.example.umc_zipdabang.databinding.ItemCommentBinding

interface CommentClickInterface {
    fun commentItemClickListener(itemData: ImageView, binding: ItemCommentBinding)
}