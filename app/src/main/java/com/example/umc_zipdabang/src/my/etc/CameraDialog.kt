package com.example.umc_zipdabang.src.my.etc

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.databinding.DialogCameraBinding

class CameraDialog (context: AppCompatActivity): Dialog(context) {
    private var binding: DialogCameraBinding = DialogCameraBinding.inflate(layoutInflater)

    private var onClickListener: ButtonClickListener? = null
    interface ButtonClickListener {
        fun onClicked()
    }

    val dialog = Dialog(context)

    fun setOnCLickListener(listener: ButtonClickListener) {
        onClickListener = listener
    }

    fun showDialog(){
        dialog.setContentView(binding.root)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.setCancelable(true)

        binding.myCameraFrame.setOnClickListener{
            //카메라로 찍기
        }
        binding.myFileFrame.setOnClickListener{
            //이미지 파일 가져오기
        }
        dialog.show()
    }
}