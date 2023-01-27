package com.example.umc_zipdabang.src.my

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Base64
import android.util.Base64.decode
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.webkit.URLUtil.decode
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.umc_zipdabang.BuildConfig
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.*
import com.example.umc_zipdabang.src.my.etc.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Byte.decode
import kotlin.properties.Delegates

class MyWritingActivity:AppCompatActivity() {
    private lateinit var viewBinding: ActivityMyWritingBinding
    private lateinit var viewBinding2: LayoutStepBinding
    private lateinit var binding_uploadcategory : DialogUploadCategoryBinding
    private lateinit var binding_upload : DialogUploadBinding
    private lateinit var binding_uploadsuccess : DialogUploadsuccessBinding
    private lateinit var binding_camera : DialogCameraBinding
    private lateinit var binding_save : DialogSaveBinding
    private lateinit var binding_reallynotsave : DialogReallynotsaveBinding

    val api = APIS.create()

    //sharedpreference 쪽 api 연결하기

    ////임시저장, 업로드 누를떄 dialog 제작하기
    ////내레시피 안됨 ㅠㅠ

    //재료 스텝 삭제 버튼 구현
    //업로드 버튼 다썼을때 활성화되게 하기
    //임시저장 해둔게 있으면 글쓰기 전에 dialog 띄우기
    //toast 띄우기

    //재료 스탭 갯수 view 위치 이동되게하기
    //오류 띄우는거 수정하기

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
    fun stringToBitmap(encodedString: String):Bitmap{
        val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyWritingBinding.inflate(layoutInflater)
        viewBinding2 = LayoutStepBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val sharedPreference = getSharedPreferences("writing", 0)
        val editor = sharedPreference.edit()
        val sharedPreference2 = getSharedPreferences("writing_image", 0)
        val editor2 = sharedPreference.edit()

       /* viewBinding.myUploadbtn.setOnClickListener {
            editor.putString("title", viewBinding.myRecipeEdtTital.text.toString())
            editor.putString("time", viewBinding.myRecipeEdtTime.text.toString())
            editor.putString("describe", viewBinding.myRecipeEdtDescribe.text.toString())
            editor.putString("aftertip", viewBinding.myRecipeEdtAftertip.text.toString())
            editor.putString("ingridient1_title",viewBinding.myRecipeEdtIngredientname.toString())
            editor.putString("ingridient1_quan",viewBinding.myRecipeEdtIngredientqun.toString())
            editor.putString("step1_image",viewBinding.myRecipeStepimage.toString())
            editor.putString("step1_describe",viewBinding.myRecipeEdtStep.toString())

            //재료, step, 사진 저장하기
        }*/

        viewBinding.myRecipeEdtTital.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (viewBinding.myRecipeEdtTital.text.toString().length == 20) {
                    viewBinding.myRecipeTvTital.error = "최대 글자 수를 초과하였습니다."
                } else {
                    viewBinding.myRecipeTvTital.error = null
                }
            }
        })
        viewBinding.myRecipeEdtDescribe.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (viewBinding.myRecipeEdtDescribe.text.toString().length == 100) {
                    viewBinding.myRecipeTvDescribe.error = "최대 글자 수를 초과하였습니다."
                } else {
                    viewBinding.myRecipeTvDescribe.error = null
                }
            }
        })
        viewBinding.myRecipeEdtStep.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (viewBinding.myRecipeEdtStep.text.toString().length == 200) {
                    viewBinding.myRecipeTvStep.error = "최대 글자 수를 초과하였습니다."
                } else {
                    viewBinding.myRecipeTvStep.error = null
                }
            }
        })
        viewBinding.myRecipeEdtAftertip.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (viewBinding.myRecipeEdtAftertip.text.toString().length == 200) {
                    viewBinding.myRecipeTvAftertip.error = "최대 글자 수를 초과하였습니다."
                } else {
                    viewBinding.myRecipeTvAftertip.error = null
                }
            }
        })

        var ingredient_view : String
        var ingredient_title_save : String
        var ingredient_quan_save : String
        var ingredient_title_sp :String
        var ingredient_quan_sp :String
        var num: Int = 1
        viewBinding.myIngredientPlusbtn.setOnClickListener {
            num++
            if (num == 10) {
                viewBinding.myIngredientPlusbtn.setVisibility(View.INVISIBLE)
            }
            viewBinding.myIngredientNumtv.setText("" + num + "/10")
            //ingredient_view = "ingredient"+num+"_view"
            val ingredient_view = LayoutInflater.from(this).inflate(R.layout.layout_ingredient, null)
            viewBinding.myLinearIngredientPlusframe.addView(ingredient_view)



            ingredient_title_sp = "ingredient"+num+"_title"
            ingredient_quan_sp = "ingredient"+num+"_quan"


//            ingredient_view.findViewById<EditText>(R.id.my_recipe_edt_ingredientname).getText()
//            ingredient_view.findViewById<EditText>(R.id.my_recipe_edt_ingredientqun).getText()

            //editor.putString(ingredient_title_sp,viewBinding.myRecipeEdtIngredientname.text.toString())
//            editor.putString(ingredient_quan_sp,viewBinding.myRecipeEdtIngredientqun.text.toString())
        }
        var num2: Int = 1
        viewBinding.myStepPlusbtn.setOnClickListener {
            val step_view = LayoutInflater.from(this).inflate(R.layout.layout_step, null)
            viewBinding.myLinearStepPlusframe.addView(step_view)
            num2++
            viewBinding.myStepNumtv.setText("Step" + num2 + "/Step10")

            step_view.findViewById<TextView>(R.id.my_recipe_step_tv).setText("Step " + num2)
            step_view.findViewById<Button>(R.id.my_recipe_btn).setText("Step" + num2 + " 사진 첨부")
            step_view.findViewById<TextView>(R.id.my_recipe_edt_step).setHint("레시피를 만드는 방법의 " + num2 + "단계를 설명해 주세요.\n" + "(최대 200자)")

            if (num2 == 10) {
                viewBinding.myStepPlusbtn.setVisibility(View.INVISIBLE)
            }
        }
        viewBinding2.myRecipeEdtStep.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (viewBinding.myRecipeEdtStep.text.toString().length == 200) {
                    viewBinding.myRecipeTvStep.error = "최대 글자 수를 초과하였습니다."
                } else {
                    viewBinding.myRecipeTvStep.error = null
                }
            }
        })

        viewBinding.mySavebtn.setOnClickListener {
            editor.putString("title", viewBinding.myRecipeEdtTital.text.toString())
            editor.putString("time", viewBinding.myRecipeEdtTime.text.toString())
            editor.putString("describe", viewBinding.myRecipeEdtDescribe.text.toString())
            editor.putString("aftertip", viewBinding.myRecipeEdtAftertip.text.toString())
            editor.putString("ingredient1_title",viewBinding.myRecipeEdtIngredientname.text.toString())
            editor.putString("ingredient1_quan",viewBinding.myRecipeEdtIngredientqun.text.toString())
            //editor.putString("step1_image",viewBinding.myRecipeImageStep.text.toString())
            editor.putString("step1_describe",viewBinding.myRecipeEdtStep.text.toString())
            editor.apply()

            sharedPreference.getString("title","@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("time","@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("describe","@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("aftertip","@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient1_title","@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient1_quan","@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step1_image","@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step1_describe","@")?.let { Log.e(ContentValues.TAG, it) }

            binding_save = DialogSaveBinding.inflate(layoutInflater)
            val dialog_save_builder = AlertDialog.Builder(this).setView(binding_save.root)
            val dialog_save = dialog_save_builder.create()

            dialog_save.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            dialog_save.setCanceledOnTouchOutside(true)
            dialog_save.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog_save.window?.setGravity(Gravity.BOTTOM)
            dialog_save.setCancelable(true)

            binding_save.myCancelbtn.setOnClickListener{
                editor.clear()
                editor.apply()
                dialog_save.onBackPressed()
            }
            binding_save.mySavebtn.setOnClickListener{
                dialog_save.dismiss()
                finish()
            }
            dialog_save.show()
        }

        viewBinding.myBackbtn.setOnClickListener {
            finish()
        }


        class check_coffee(var coffee: String){

        }

        var check_beverage:String
        var check_tea:String
        var check_ade:String
        var check_smudi:String
        var check_health:String
        viewBinding.myUploadbtn.setOnClickListener{
            editor.putString("title", viewBinding.myRecipeEdtTital.text.toString())
            editor.putString("time", viewBinding.myRecipeEdtTime.text.toString())
            editor.putString("describe", viewBinding.myRecipeEdtDescribe.text.toString())
            editor.putString("aftertip", viewBinding.myRecipeEdtAftertip.text.toString())
            editor.putString("ingridient1_title",viewBinding.myRecipeEdtIngredientname.text.toString())
            editor.putString("ingridient1_quan",viewBinding.myRecipeEdtIngredientqun.text.toString())
            editor.putString("step1_describe",viewBinding.myRecipeEdtStep.text.toString())
            editor.apply()


            sharedPreference.getString("title","@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("time","@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("describe","@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("aftertip","@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient1_title","@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient1_quan","@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step1_describe","@")?.let { Log.e(ContentValues.TAG, it) }

//            var title_save = viewBinding.myRecipeEdtTital.text.toString()
//            var time_save = viewBinding.myRecipeEdtTime.text.toString()
//            var describe_save = viewBinding.myRecipeEdtDescribe.text.toString()
//            var aftertip_save = viewBinding.myRecipeEdtAftertip.text.toString()
//            var ingridient1_title_save = viewBinding.myRecipeEdtIngredientname.text.toString()
//            var ingridient1_quan_save = viewBinding.myRecipeEdtIngredientqun.text.toString()
//            //var step1_image_save = viewBinding.myRecipeEdtTital.text.toString()
//            var step1_describe_save = viewBinding.myRecipeEdtStep.text.toString()

            binding_uploadcategory = DialogUploadCategoryBinding.inflate(layoutInflater)
            val dialog_uploadcategory_builder = AlertDialog.Builder(this).setView(binding_uploadcategory.root)
            val dialog_uploadcategory = dialog_uploadcategory_builder.create()

            dialog_uploadcategory.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            dialog_uploadcategory.setCanceledOnTouchOutside(true)
            dialog_uploadcategory.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog_uploadcategory.window?.setGravity(Gravity.BOTTOM)
            dialog_uploadcategory.setCancelable(true)

            binding_uploadcategory.myCoffee.setOnClickListener {
                binding_uploadcategory.myCoffee.isSelected = !(binding_uploadcategory.myCoffee.isSelected == true)
                if (binding_uploadcategory.myCoffee.isSelected) {
                    binding_uploadcategory.myCoffee.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)
                    var check = check_coffee("1")
                } else {
                    binding_uploadcategory.myCoffee.setBackgroundResource(R.drawable.my_btn_round_notsave)
                    var check = check_coffee("0")
                }
            }
            binding_uploadcategory.myBeverage.setOnClickListener {
                binding_uploadcategory.myBeverage.isSelected = !(binding_uploadcategory.myBeverage.isSelected == true)
                if (binding_uploadcategory.myBeverage.isSelected) {
                    binding_uploadcategory.myBeverage.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)
                    check_beverage = "1"
                } else {
                    binding_uploadcategory.myBeverage.setBackgroundResource(R.drawable.my_btn_round_notsave)
                    check_beverage = "0"
                }
            }
            binding_uploadcategory.myTea.setOnClickListener {
                binding_uploadcategory.myTea.isSelected = !(binding_uploadcategory.myTea.isSelected == true)
                if (binding_uploadcategory.myTea.isSelected) {
                    binding_uploadcategory.myTea.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)
                    check_tea ="1"
                } else {
                    binding_uploadcategory.myTea.setBackgroundResource(R.drawable.my_btn_round_notsave)
                    check_tea ="0"
                }
            }
            binding_uploadcategory.myAde.setOnClickListener {
                binding_uploadcategory.myAde.isSelected = !(binding_uploadcategory.myAde.isSelected == true)
                if (binding_uploadcategory.myAde.isSelected) {
                    binding_uploadcategory.myAde.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)
                    check_ade = "1"
                } else {
                    binding_uploadcategory.myAde.setBackgroundResource(R.drawable.my_btn_round_notsave)
                    check_ade = "0"
                }
            }
            binding_uploadcategory.mySmudi.setOnClickListener {
                binding_uploadcategory.mySmudi.isSelected = !(binding_uploadcategory.mySmudi.isSelected == true)
                if (binding_uploadcategory.mySmudi.isSelected) {
                    binding_uploadcategory.mySmudi.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)
                    check_smudi = "1"
                } else {
                    binding_uploadcategory.mySmudi.setBackgroundResource(R.drawable.my_btn_round_notsave)
                    check_smudi="0"
                }
            }
            binding_uploadcategory.myHealth.setOnClickListener {
                binding_uploadcategory.myHealth.isSelected = !(binding_uploadcategory.myHealth.isSelected == true)
                if (binding_uploadcategory.myHealth.isSelected) {
                    binding_uploadcategory.myHealth.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)
                    check_health = "1"
                } else {
                    binding_uploadcategory.myHealth.setBackgroundResource(R.drawable.my_btn_round_notsave)
                    check_health = "0"
                }
            }

            /*if(binding_uploadcategory.myCoffee.isSelected){
                editor.putString("category","coffee")
                editor.apply()
            }else if(binding_uploadcategory.myBeverage.isSelected){
                editor.putString("category","beverage")
                editor.apply()
            }else if(binding_uploadcategory.myTea.isSelected){
                editor.putString("category","tea")
                editor.apply()
            }else if(binding_uploadcategory.myAde.isSelected){
                editor.putString("category","ade")
                editor.apply()
            }else if(binding_uploadcategory.mySmudi.isSelected){
                editor.putString("category","smudi")
                editor.apply()
            }else if(binding_uploadcategory.myHealth.isSelected){
                editor.putString("category","health")
                editor.apply()
            }else{
                editor.putString("category","")
                editor.apply()
            }*/

            //업로드 완료 눌렀을때
            binding_uploadcategory.myDonebtn.setOnClickListener {
                binding_upload = DialogUploadBinding.inflate(layoutInflater)
                val dialog_upload_builder = AlertDialog.Builder(this).setView(binding_upload.root)
                val dialog_upload = dialog_upload_builder.create()

                //여기서 카테고리 저장하셈
                /*if( ){
                    editor.putString("category","coffee")
                    editor.apply()
                }else if(binding_uploadcategory.myBeverage.isSelected){
                    editor.putString("category","beverage")
                    editor.apply()
                }else if(binding_uploadcategory.myTea.isSelected){
                    editor.putString("category","tea")
                    editor.apply()
                }else if(binding_uploadcategory.myAde.isSelected){
                    editor.putString("category","ade")
                    editor.apply()
                }else if(binding_uploadcategory.mySmudi.isSelected){
                    editor.putString("category","smudi")
                    editor.apply()
                }else if(binding_uploadcategory.myHealth.isSelected){
                    editor.putString("category","health")
                    editor.apply()
                }else{
                    editor.putString("category","")
                    editor.apply()
                }*/

                //dialog_uploadcategory.window?.setFeatureDrawableResource(ColorDrawable(Color.parseColor()))
                dialog_upload.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                dialog_upload.setCanceledOnTouchOutside(true)
                dialog_upload.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog_upload.window?.setGravity(Gravity.BOTTOM)
                dialog_upload.setCancelable(true)


                val recipe_list = PostNewRecipeList(1, sharedPreference.getString("title","")
                ,sharedPreference.getString("describe",""),sharedPreference.getString("aftertip","")
                    ,sharedPreference.getString("time",""), sharedPreference.getString("aftertip",""))
                val ingredient_list = arrayListOf<PostNewRecipeIngredient>(
                    PostNewRecipeIngredient(sharedPreference.getString("ingredient1_title",""), sharedPreference.getString("ingredient1_quan",""))
                )
                val steps_list = arrayListOf<PostNewRecipeSteps>(
                    PostNewRecipeSteps(sharedPreference.getString("step1_title",""), sharedPreference.getString("step1_describe",""), sharedPreference.getString("",""))
                )

                val body = PostNewRecipeBody(recipe_list, ingredient_list, steps_list)
                api.post_newrecipe("x-access-token", body).enqueue(object: Callback<PostNewRecipeBodyResponse> {
                    override fun onResponse(call: Call<PostNewRecipeBodyResponse>, response: Response<PostNewRecipeBodyResponse>) {
                        Log.d("통신","통신은 성공임")
                    }
                    override fun onFailure(call: Call<PostNewRecipeBodyResponse>, t: Throwable) {
                        Log.d("통신", "통신 실패..")
                    }
                })

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
                        dialog_uploadcategory.dismiss()
                        dialog_uploadsuccess.dismiss()
                        finish()
                    }
                    //나중에 보기
                    binding_uploadsuccess.myUploadseelaterbtn.setOnClickListener {
                        dialog_uploadcategory.dismiss()
                        dialog_uploadsuccess.dismiss()
                        finish()
                    }
                    binding_uploadsuccess.myXbtn.setOnClickListener{
                        dialog_uploadcategory.dismiss()
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
            //업로드 뒤로가기헀을때
            binding_uploadcategory.myBackbtn.setOnClickListener {
               dialog_uploadcategory.dismiss()
            }

            dialog_uploadcategory.show()
        }

        viewBinding.myRecipeImageStep.setOnClickListener{
            viewBinding.myRecipeTvImage.setText("사진은 하나만 첨부가능하며,변경할수 없습니다.")
            binding_camera = DialogCameraBinding.inflate(layoutInflater)
            val dialog_camera_builder = AlertDialog.Builder(this).setView(binding_camera.root)
            val dialog_camera = dialog_camera_builder.create()

            dialog_camera.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            dialog_camera.setCanceledOnTouchOutside(true)
            dialog_camera.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog_camera.window?.setGravity(Gravity.BOTTOM)
            dialog_camera.setCancelable(true)

            binding_camera.myCameraFrame.setOnClickListener{
                Log.d("카메라 준비완료","ㅈㅂ")
            }
            binding_camera.myFileFrame.setOnClickListener{
                viewBinding.myRecipeRealimageStep.bringToFront()
                selectGallery(1)
                dialog_camera.dismiss()
            }
            dialog_camera.show()
        }

        viewBinding.myAlbumbtn.setOnClickListener {
            binding_camera = DialogCameraBinding.inflate(layoutInflater)
            val dialog_camera_builder = AlertDialog.Builder(this).setView(binding_camera.root)
            val dialog_camera = dialog_camera_builder.create()

            dialog_camera.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            dialog_camera.setCanceledOnTouchOutside(true)
            dialog_camera.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog_camera.window?.setGravity(Gravity.BOTTOM)
            dialog_camera.setCancelable(true)

            binding_camera.myCameraFrame.setOnClickListener{
                Log.d("카메라 준비완료","ㅈㅂ")
            }
            binding_camera.myFileFrame.setOnClickListener{
                viewBinding.myImage.bringToFront()
                selectGallery(0)
                dialog_camera.dismiss()
            }

            dialog_camera.show()
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

        val sharedPreference2 = getSharedPreferences("writing_image", 0)
        val editor2 = sharedPreference2.edit()

        val file = File(absolutelyPath(imageUri, this))
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        val body = MultipartBody.Part.createFormData("profile", file.name, requestFile)
        sendImage(body)

        imageUri?.let{
            imageFile = File(getRealPathFromURI(it))

            val sharedPreference = getSharedPreferences("writing", 0)
            val editor = sharedPreference.edit()

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val layout = viewBinding.myImage
                        layout.background = BitmapDrawable(resources, resource)
                        editor.putString("thumbnail", bitmapToString(resource))
                        editor.apply()
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
                //.into(viewBinding.myImage)
        }
    }
    private val imageResult1 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        val imageUri = result.data?.data

        val sharedPreference = getSharedPreferences("writing", 0)
        val editor = sharedPreference.edit()

        imageUri?.let{
            imageFile = File(getRealPathFromURI(it))

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        val layout = viewBinding.myRecipeRealimageStep
                        layout.background = BitmapDrawable(resources, resource)
                        editor.putString("step1_image", bitmapToString(resource))
                        editor.apply()
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
    }

    private fun selectGallery(num:Int){
        val writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if(writePermission == PackageManager.PERMISSION_DENIED ||
                readPermission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
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

    fun sendImage(body: MultipartBody.Part){
        api.post_newrecipe_image("x-access-token", body).enqueue(object: Callback<PostNewRecipeImageBodyResponse>{
            override fun onResponse(call: Call<PostNewRecipeImageBodyResponse>, response: Response<PostNewRecipeImageBodyResponse>) {
                Log.d("통신","통신 잘됐나")
            }
            override fun onFailure(call: Call<PostNewRecipeImageBodyResponse>, t: Throwable) {
                Log.d("통신","통신 전혀 안됨..")
            }
        })
    }

    fun absolutelyPath(path:Uri?, context: Context): String{
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

    override fun onBackPressed() {
        val sharedPreference = getSharedPreferences("writing", 0)
        val editor = sharedPreference.edit()

        binding_reallynotsave = DialogReallynotsaveBinding.inflate(layoutInflater)
        val dialog_reallynotsave_builder = AlertDialog.Builder(this).setView(binding_reallynotsave.root)
        val dialog_reallynotsave = dialog_reallynotsave_builder.create()

        dialog_reallynotsave.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog_reallynotsave.setCanceledOnTouchOutside(true)
        dialog_reallynotsave.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog_reallynotsave.window?.setGravity(Gravity.BOTTOM)
        dialog_reallynotsave.setCancelable(true)

        binding_reallynotsave.myDeletebtn.setOnClickListener{
            dialog_reallynotsave.dismiss()
            finish()
        }
        binding_reallynotsave.mySavebtn.setOnClickListener{

        }
        binding_reallynotsave.myCancelbtn.setOnClickListener {
            dialog_reallynotsave.dismiss()
            finish()
        }
        dialog_reallynotsave.show()
    }
}
