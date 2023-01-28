package com.example.umc_zipdabang.src.my

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.umc_zipdabang.databinding.ActivityMySaveBinding
import com.example.umc_zipdabang.databinding.DialogUploadBinding
import com.example.umc_zipdabang.databinding.DialogUploadsuccessBinding
import java.io.ByteArrayOutputStream
import java.io.File

class MySaveActivity:AppCompatActivity() {
    private lateinit var viewBinding: ActivityMySaveBinding
    private lateinit var binding_upload : DialogUploadBinding
    private lateinit var binding_uploadsuccess : DialogUploadsuccessBinding

    fun bitmaptoByteArray(bitmap: Bitmap) : ByteArray{
        var outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
        return outputStream.toByteArray()
    }
    fun byteArrayToBitmap(byteArray: ByteArray):Bitmap{
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return bitmap
    }
    fun bitmapToString(bitmap:Bitmap):String{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
    fun stringToBitmap(encodedString: String?):Bitmap{
        val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMySaveBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val sharedPreference = getSharedPreferences("writing",0)
        val editor = sharedPreference.edit()
        val sharedPreference2 = getSharedPreferences("writing_image", 0)
        val editor2 = sharedPreference2.edit()

        var thumbnail_sp = sharedPreference2.getString("thumbnail","")
        var title_sp = sharedPreference.getString("title","")
        var time_sp = sharedPreference.getString("time","")
        var describe_sp = sharedPreference.getString("describe","")
        var aftertip_sp = sharedPreference.getString("aftertip","")
        var ingredient1_title_sp = sharedPreference.getString("ingredient1_title","")
        var ingredient1_quan_sp = sharedPreference.getString("ingredient1_quan","")
        var ingredient2_title_sp = sharedPreference.getString("ingredient2_title","")
        var ingredient2_quan_sp = sharedPreference.getString("ingredient2_quan","")
        var ingredient3_title_sp = sharedPreference.getString("ingredient3_title","")
        var ingredient3_quan_sp = sharedPreference.getString("ingredient3_quan","")
        var ingredient4_title_sp = sharedPreference.getString("ingredient4_title","")
        var ingredient4_quan_sp = sharedPreference.getString("ingredient4_quan","")
        var ingredient5_title_sp = sharedPreference.getString("ingredient5_title","")
        var ingredient5_quan_sp = sharedPreference.getString("ingredient5_quan","")
        var ingredient6_title_sp = sharedPreference.getString("ingredient6_title","")
        var ingredient6_quan_sp = sharedPreference.getString("ingredient6_quan","")
        var ingredient7_title_sp = sharedPreference.getString("ingredient7_title","")
        var ingredient7_quan_sp = sharedPreference.getString("ingredient7_quan","")
        var ingredient8_title_sp = sharedPreference.getString("ingredient8_title","")
        var ingredient8_quan_sp = sharedPreference.getString("ingredient8_quan","")
        var ingredient9_title_sp = sharedPreference.getString("ingredient9_title","")
        var ingredient9_quan_sp = sharedPreference.getString("ingredient9_quan","")
        var ingredient10_title_sp = sharedPreference.getString("ingredient10_title","")
        var ingredient10_quan_sp = sharedPreference.getString("ingredient10_quan","")
        var step1_image_sp = sharedPreference2.getString("step1_image","")
        var step1_describe_sp = sharedPreference.getString("step1_describe","")
        var step2_image_sp = sharedPreference2.getString("step2_image","")
        var step2_describe_sp = sharedPreference.getString("step2_describe","")
        var step3_image_sp = sharedPreference2.getString("step3_image","")
        var step3_describe_sp = sharedPreference.getString("step3_describe","")
        var step4_image_sp = sharedPreference2.getString("step4_image","")
        var step4_describe_sp = sharedPreference.getString("step4_describe","")
        var step5_image_sp = sharedPreference2.getString("step5_image","")
        var step5_describe_sp = sharedPreference.getString("step5_describe","")
        var step6_image_sp = sharedPreference2.getString("step6_image","")
        var step6_describe_sp = sharedPreference.getString("step6_describe","")
        var step7_image_sp = sharedPreference2.getString("step7_image","")
        var step7_describe_sp = sharedPreference.getString("step7_describe","")
        var step8_image_sp = sharedPreference2.getString("step8_image","")
        var step8_describe_sp = sharedPreference.getString("step8_describe","")
        var step9_image_sp = sharedPreference2.getString("step9_image","")
        var step9_describe_sp = sharedPreference.getString("step9_describe","")
        var step10_image_sp = sharedPreference2.getString("step10_image","")
        var step10_describe_sp = sharedPreference.getString("step10_describe","")


        sharedPreference.getString("title","@")?.let { Log.e(ContentValues.TAG, it) }
        sharedPreference.getString("time","@")?.let { Log.e(ContentValues.TAG, it) }
        sharedPreference.getString("describe","@")?.let { Log.e(ContentValues.TAG, it) }
        sharedPreference.getString("aftertip","@")?.let { Log.e(ContentValues.TAG, it) }
        sharedPreference.getString("ingredient1_title","@")?.let { Log.e(ContentValues.TAG, it) }
        sharedPreference.getString("ingredient1_quan","@")?.let { Log.e(ContentValues.TAG, it) }
        sharedPreference.getString("step1_describe","@")?.let { Log.e(ContentValues.TAG, it) }
        sharedPreference.getString("category","@")?.let { Log.e(ContentValues.TAG, it) }


        Glide.with(this)
            .asBitmap()
            .load(thumbnail_sp)
            .centerCrop()
            .into(viewBinding.myImage)

       Glide.with(this)
           .asBitmap()
           .load(step1_image_sp)
           .centerCrop()
           .into(viewBinding.myRecipeRealimageStep)

        viewBinding.myRecipeEdtTital.setText(title_sp)
        viewBinding.myRecipeEdtTime.setText(time_sp)
        viewBinding.myRecipeEdtDescribe.setText(describe_sp)
        viewBinding.myRecipeEdtAftertip.setText(aftertip_sp)
        viewBinding.myRecipeEdtIngredientname.setText(ingredient1_title_sp)
        viewBinding.myRecipeEdtIngredientqun.setText(ingredient1_quan_sp)
        viewBinding.myRecipeEdtStep.setText(step1_describe_sp)

        viewBinding.myImage.setOnClickListener{
            viewBinding.myImage.bringToFront()
            selectGallery(0)
        }

        viewBinding.myRecipeImageStep.setOnClickListener {
            viewBinding.myRecipeImageStep.bringToFront()
            selectGallery(1)
        }

        viewBinding.myUploadbtn.setOnClickListener {
            binding_upload = DialogUploadBinding.inflate(layoutInflater)
            val dialog_upload_builder = AlertDialog.Builder(this).setView(binding_upload.root)
            val dialog_upload = dialog_upload_builder.create()

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
                dialog_uploadsuccess.setCancelable(false)

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

    companion object{
        const val REVIEW_MIN_LENGTH = 10
        const val REQ_GALLERY =1
    }
    fun getRealPathFromURI(uri: Uri):String{
        val buildName = Build.MANUFACTURER
        if(buildName.equals("Xiaomi")){
            return uri.path!!
        }
        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, proj, null, null, null)
        if(cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }
    lateinit var imageFile : File
    private val imageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        val imageUri = result.data?.data
        Log.d("uri 떴냐", imageUri.toString())

        imageUri?.let{
            imageFile = File(getRealPathFromURI(it))

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(viewBinding.myImage)
        }
    }
    private val imageResult1 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        val imageUri = result.data?.data
        Log.d("uri 떴냐", imageUri.toString())

        imageUri?.let{
            imageFile = File(getRealPathFromURI(it))

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(viewBinding.myRecipeRealimageStep)
        }
    }
    private fun selectGallery(num:Int){
        val writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if(writePermission == PackageManager.PERMISSION_DENIED ||
            readPermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE), REQ_GALLERY)
        }else{
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )

            if(num == 1){
                imageResult1.launch(intent)
            } else{
                imageResult.launch(intent)
            }
        }
    }
}