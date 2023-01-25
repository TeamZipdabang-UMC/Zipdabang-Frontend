package com.example.umc_zipdabang.src.my.etc

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.DialogUploadBinding
import com.example.umc_zipdabang.databinding.DialogUploadCategoryBinding

class UploadcategoryDialog (context: AppCompatActivity): Dialog(context){

    private var binding: DialogUploadCategoryBinding = DialogUploadCategoryBinding.inflate(layoutInflater)
    private var binding2 : DialogUploadBinding = DialogUploadBinding.inflate(layoutInflater)

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

        binding.myDonebtn.setOnClickListener{
            //uploead 띄우기
        }
        binding.myBackbtn.setOnClickListener{
            dialog.dismiss()
        }
        binding.myCoffee.setOnClickListener{
            binding.myCoffee.isSelected =!(binding.myCoffee.isSelected == true)
            if(binding.myCoffee.isSelected){
                binding.myCoffee.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)
            }
            else{
                binding.myCoffee.setBackgroundResource(R.drawable.my_btn_round_notsave)
            }
        }
        binding.myBeverage.setOnClickListener{
            binding.myBeverage.isSelected =!(binding.myBeverage.isSelected == true)
            if(binding.myBeverage.isSelected){
                binding.myBeverage.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)
            }
            else{
                binding.myBeverage.setBackgroundResource(R.drawable.my_btn_round_notsave)
            }
        }
        binding.myTea.setOnClickListener{
            binding.myTea.isSelected =!(binding.myTea.isSelected == true)
            if(binding.myTea.isSelected){
                binding.myTea.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)
            }
            else{
                binding.myTea.setBackgroundResource(R.drawable.my_btn_round_notsave)
            }
        }
        binding.myAde.setOnClickListener{
            binding.myAde.isSelected =!(binding.myAde.isSelected == true)
            if(binding.myAde.isSelected){
                binding.myAde.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)
            }
            else{
                binding.myAde.setBackgroundResource(R.drawable.my_btn_round_notsave)
            }
        }
        binding.mySmudi.setOnClickListener{
            binding.mySmudi.isSelected =!(binding.mySmudi.isSelected == true)
            if(binding.mySmudi.isSelected){
                binding.mySmudi.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)
            }
            else{
                binding.mySmudi.setBackgroundResource(R.drawable.my_btn_round_notsave)
            }
        }
        binding.myHealth.setOnClickListener{
            binding.myHealth.isSelected =!(binding.myHealth.isSelected == true)
            if(binding.myHealth.isSelected){
                binding.myHealth.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)
            }
            else{
                binding.myHealth.setBackgroundResource(R.drawable.my_btn_round_notsave)
            }
        }

        dialog.show()
    }
}