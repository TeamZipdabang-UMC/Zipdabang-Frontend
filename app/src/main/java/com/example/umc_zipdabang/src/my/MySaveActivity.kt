package com.example.umc_zipdabang.src.my

import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.databinding.ActivityMySaveBinding
import com.example.umc_zipdabang.databinding.DialogUploadBinding
import com.example.umc_zipdabang.databinding.DialogUploadsuccessBinding

class MySaveActivity:AppCompatActivity() {
    private lateinit var viewBinding: ActivityMySaveBinding
    private lateinit var binding_upload : DialogUploadBinding
    private lateinit var binding_uploadsuccess : DialogUploadsuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMySaveBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val sharedPreference = getSharedPreferences("writing",0)
        val editor = sharedPreference.edit()

        var title_sp = sharedPreference.getString("title","")
        var time_sp = sharedPreference.getString("time","")
        var describe_sp = sharedPreference.getString("describe","")
        var aftertip_sp = sharedPreference.getString("aftertip","")
        var ingredient1_title_sp = sharedPreference.getString("ingredient1_title","")
        var ingredient1_quan_sp = sharedPreference.getString("ingredient1_quan","")
        var step1_image_sp = sharedPreference.getString("step1_image","")
        var step1_describe_sp = sharedPreference.getString("step1_describe","")

        sharedPreference.getString("title","@")?.let { Log.e(ContentValues.TAG, it) }
        sharedPreference.getString("time","@")?.let { Log.e(ContentValues.TAG, it) }
        sharedPreference.getString("describe","@")?.let { Log.e(ContentValues.TAG, it) }
        sharedPreference.getString("aftertip","@")?.let { Log.e(ContentValues.TAG, it) }
        sharedPreference.getString("ingredient1_title","@")?.let { Log.e(ContentValues.TAG, it) }
        sharedPreference.getString("ingredient1_quan","@")?.let { Log.e(ContentValues.TAG, it) }
        sharedPreference.getString("step1_image","@")?.let { Log.e(ContentValues.TAG, it) }
        sharedPreference.getString("step1_describe","@")?.let { Log.e(ContentValues.TAG, it) }

            viewBinding.myRecipeEdtTital.setText(title_sp)
            viewBinding.myRecipeEdtTime.setText(time_sp)
            viewBinding.myRecipeEdtDescribe.setText(describe_sp)
            viewBinding.myRecipeEdtAftertip.setText(aftertip_sp)
            viewBinding.myRecipeEdtIngredientname.setText(ingredient1_title_sp)
            viewBinding.myRecipeEdtIngredientqun.setText(ingredient1_quan_sp)
            viewBinding.myRecipeImageStep.setText(step1_image_sp)
            viewBinding.myRecipeEdtStep.setText(step1_describe_sp)


        viewBinding.myUploadbtn.setOnClickListener {
            binding_upload = DialogUploadBinding.inflate(layoutInflater)
            val dialog_upload_builder = AlertDialog.Builder(this).setView(binding_upload.root)
            val dialog_upload = dialog_upload_builder.create()

            //여기서 카테고리 저장하셈
            //dialog_uploadcategory.window?.setFeatureDrawableResource(ColorDrawable(Color.parseColor()))
            dialog_upload.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            dialog_upload.setCanceledOnTouchOutside(true)
            dialog_upload.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog_upload.window?.setGravity(Gravity.BOTTOM)
            dialog_upload.setCancelable(true)

            //업로드 재확인
            binding_upload.myUploadbtn.setOnClickListener{
                dialog_upload.dismiss()
                binding_uploadsuccess = DialogUploadsuccessBinding.inflate(layoutInflater)
                val dialog_uploadsuccess_builder = AlertDialog.Builder(this).setView(binding_uploadsuccess.root)
                val dialog_uploadsuccess = dialog_uploadsuccess_builder.create()

                dialog_uploadsuccess.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                dialog_uploadsuccess.setCanceledOnTouchOutside(true)
                dialog_uploadsuccess.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog_uploadsuccess.window?.setGravity(Gravity.BOTTOM)
                dialog_uploadsuccess.setCancelable(true)

                //업로드 레시피 보러가기
                binding_uploadsuccess.myUploaddonebtn.setOnClickListener {
                    editor.putString("title", viewBinding.myRecipeEdtTital.text.toString())
                    editor.putString("time", viewBinding.myRecipeEdtTime.text.toString())
                    editor.putString("describe", viewBinding.myRecipeEdtDescribe.text.toString())
                    editor.putString("aftertip", viewBinding.myRecipeEdtAftertip.text.toString())
                    editor.putString("ingridient1_title",viewBinding.myRecipeEdtIngredientname.toString())
                    editor.putString("ingridient1_quan",viewBinding.myRecipeEdtIngredientqun.toString())
                    editor.putString("step1_image",viewBinding.myRecipeImageStep.toString())
                    editor.putString("step1_describe",viewBinding.myRecipeEdtStep.toString())
                    editor.apply()

                    sharedPreference.getString("title","")?.let { Log.e(ContentValues.TAG, it) }
                    sharedPreference.getString("time","")?.let { Log.e(ContentValues.TAG, it) }
                    sharedPreference.getString("describe","")?.let { Log.e(ContentValues.TAG, it) }
                    sharedPreference.getString("aftertip","")?.let { Log.e(ContentValues.TAG, it) }
                    sharedPreference.getString("ingridient1_title","")?.let { Log.e(ContentValues.TAG, it) }
                    sharedPreference.getString("ingridient1_quan","")?.let { Log.e(ContentValues.TAG, it) }
                    sharedPreference.getString("step1_image","")?.let { Log.e(ContentValues.TAG, it) }
                    sharedPreference.getString("step1_describe","")?.let { Log.e(ContentValues.TAG, it) }


                    dialog_uploadsuccess.dismiss()
                    finish()
                    //sp 여기서 다 보내주기
                }
                //나중에 보기
                binding_uploadsuccess.myUploadseelaterbtn.setOnClickListener {
                    //홈화면 돌아가기
                    dialog_uploadsuccess.dismiss()
                    finish()
                }

                dialog_uploadsuccess.show()
            }
            binding_upload.myCancelbtn.setOnClickListener {
                dialog_upload.dismiss()
            }

            dialog_upload.show()
        }

        viewBinding.myBackbtn.setOnClickListener {
            finish()
        }
    }
}