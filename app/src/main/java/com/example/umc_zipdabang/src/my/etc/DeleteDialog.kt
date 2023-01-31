package com.example.umc_zipdabang.src.my.etc

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowId
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.databinding.DialogDeleteBinding

class DeleteDialog(context: AppCompatActivity):Dialog(context) {

    private var binding: DialogDeleteBinding = DialogDeleteBinding.inflate(context.layoutInflater)


    private var onClickListener: ButtonClickListener?= null

    interface ButtonClickListener{
        fun onClicked()
    }

    val dialog= Dialog(context)



    fun setOnClickListener(listener: ButtonClickListener){
        onClickListener = listener
    }

    fun showDialog()
    {

        dialog.setContentView(binding.root)

        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.setCancelable(true)


        binding.myCancelbtn.setOnClickListener(object  : View.OnClickListener {
            override fun onClick(p0: View?) {
                dialog.dismiss()
            }


        })


        binding.myDeletebtn.setOnClickListener {
            onClickListener?.onClicked()
            dialog.dismiss()
        }

        dialog.show()


    }
}