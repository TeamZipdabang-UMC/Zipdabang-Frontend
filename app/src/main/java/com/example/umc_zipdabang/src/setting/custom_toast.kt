package com.example.umc_zipdabang.src.setting

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.example.umc_zipdabang.databinding.MyAgreeToastBinding

object custom_toast {


    fun createToast(context: Context, message: String): Toast? {
        val inflater = LayoutInflater.from(context)
        val binding: MyAgreeToastBinding =
            MyAgreeToastBinding.inflate(inflater)

        binding.tvSample.text = message

        return Toast(context).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 20.toPx())
            duration = Toast.LENGTH_SHORT
            view = binding.root
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}