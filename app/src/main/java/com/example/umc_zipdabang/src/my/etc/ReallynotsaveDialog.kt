package com.example.umc_zipdabang.src.my.etc

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.databinding.DialogReallynotsaveBinding

class ReallynotsaveDialog (context: AppCompatActivity): Dialog(context){

    private var binding: DialogReallynotsaveBinding = DialogReallynotsaveBinding.inflate(layoutInflater)

    private var onClickListener: ButtonClickListener ?=null
    interface ButtonClickListener{
        fun onClicked()
    }

    val dialog = Dialog(context)

    fun setOnCLickListener(listener: ButtonClickListener){
        onClickListener = listener
    }

    fun showDialog(){
        dialog.setContentView(binding.root)

        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.setCancelable(true)

        binding.myDeletebtn.setOnClickListener{
            //deleteDialog 띄우기
        }
        binding.mySavebtn.setOnClickListener{
            dialog.dismiss()
            //홈화면 가기
        }
        binding.myCancelbtn.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }
}