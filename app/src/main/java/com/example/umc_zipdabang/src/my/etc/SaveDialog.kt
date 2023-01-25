package com.example.umc_zipdabang.src.my.etc

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.DialogSaveBinding
import com.example.umc_zipdabang.src.my.MyFragment

class SaveDialog(context: AppCompatActivity): Dialog(context) {

    private var binding: DialogSaveBinding = DialogSaveBinding.inflate(context.layoutInflater)

    private var onClickListener: ButtonClickListener ?=null
    interface ButtonClickListener{
        fun onClicked()
    }

    fun setOnCLickListener(listener: ButtonClickListener){
        onClickListener = listener
    }

    val dialog = Dialog(context)
    fun showDialog(){
        dialog.setContentView(binding.root)

        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.setCancelable(true)

        binding.myCancelbtn.setOnClickListener{
            dialog.dismiss()
        }
        binding.mySavebtn.setOnClickListener{
            dialog.dismiss()
            //홈화면 가기
        }
        dialog.show()
    }
}