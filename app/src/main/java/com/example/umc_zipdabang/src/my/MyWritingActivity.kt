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
    private lateinit var binding_upload : DialogUploadBinding
    private lateinit var binding_uploadsuccess : DialogUploadsuccessBinding
    private lateinit var binding_camera : DialogCameraBinding
    private lateinit var binding_save : DialogSaveBinding
    private lateinit var binding_reallynotsave : DialogReallynotsaveBinding

    /*private val retrofit = RetrofitInstance.getInstance().create(APIS_My::class.java)*/

    //sharedpreference 쪽 api 연결하기

    ////임시저장, 업로드 누를떄 dialog 제작하기
    ////내레시피 안됨 ㅠㅠ

    //업로드 버튼 다썼을때 활성화되게 하기
    //임시저장 해둔게 있으면 글쓰기 전에 dialog 띄우기
    //toast 띄우기

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
        val editor2 = sharedPreference2.edit()

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

        //카테고리 선택 버튼
        viewBinding.myCoffee.setOnClickListener {
            if(viewBinding.myBeverage.isSelected || viewBinding.myTea.isSelected || viewBinding.myAde.isSelected
                || viewBinding.mySmudi.isSelected || viewBinding.myHealth.isSelected){

            }else{
                viewBinding.myCoffee.isSelected = !(viewBinding.myCoffee.isSelected == true)
                if (viewBinding.myCoffee.isSelected) {
                    viewBinding.myCoffee.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)

                } else {
                    viewBinding.myCoffee.setBackgroundResource(R.drawable.my_btn_round_notsave)
                }
            }
        }
        viewBinding.myBeverage.setOnClickListener {
            if(viewBinding.myCoffee.isSelected || viewBinding.myTea.isSelected || viewBinding.myAde.isSelected
                || viewBinding.mySmudi.isSelected || viewBinding.myHealth.isSelected) {

            }else{
                viewBinding.myBeverage.isSelected = !(viewBinding.myBeverage.isSelected == true)
                if (viewBinding.myBeverage.isSelected) {
                    viewBinding.myBeverage.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)

                } else {
                    viewBinding.myBeverage.setBackgroundResource(R.drawable.my_btn_round_notsave)

                }
            }
        }
        viewBinding.myTea.setOnClickListener {
            if(viewBinding.myBeverage.isSelected || viewBinding.myCoffee.isSelected || viewBinding.myAde.isSelected
                || viewBinding.mySmudi.isSelected || viewBinding.myHealth.isSelected){

            }else {
                viewBinding.myTea.isSelected = !(viewBinding.myTea.isSelected == true)
                if (viewBinding.myTea.isSelected) {
                    viewBinding.myTea.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)

                } else {
                    viewBinding.myTea.setBackgroundResource(R.drawable.my_btn_round_notsave)

                }
            }
        }
        viewBinding.myAde.setOnClickListener {
            if(viewBinding.myBeverage.isSelected || viewBinding.myCoffee.isSelected || viewBinding.myTea.isSelected
                || viewBinding.mySmudi.isSelected || viewBinding.myHealth.isSelected){

            }else {
                viewBinding.myAde.isSelected = !(viewBinding.myAde.isSelected == true)
                if (viewBinding.myAde.isSelected) {
                    viewBinding.myAde.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)

                } else {
                    viewBinding.myAde.setBackgroundResource(R.drawable.my_btn_round_notsave)

                }
            }
        }
        viewBinding.mySmudi.setOnClickListener {
            if(viewBinding.myBeverage.isSelected || viewBinding.myCoffee.isSelected || viewBinding.myTea.isSelected
                || viewBinding.myAde.isSelected || viewBinding.myHealth.isSelected){

            }else {
                viewBinding.mySmudi.isSelected = !(viewBinding.mySmudi.isSelected == true)
                if (viewBinding.mySmudi.isSelected) {
                    viewBinding.mySmudi.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)
                } else {
                    viewBinding.mySmudi.setBackgroundResource(R.drawable.my_btn_round_notsave)
                }
            }
        }
        viewBinding.myHealth.setOnClickListener {
            if(viewBinding.myBeverage.isSelected || viewBinding.myCoffee.isSelected || viewBinding.myTea.isSelected
                || viewBinding.myAde.isSelected || viewBinding.mySmudi.isSelected){

            }else {
                viewBinding.myHealth.isSelected = !(viewBinding.myHealth.isSelected == true)
                if (viewBinding.myHealth.isSelected) {
                    viewBinding.myHealth.setBackgroundResource(R.drawable.my_btn_round_notsave_selected)

                } else {
                    viewBinding.myHealth.setBackgroundResource(R.drawable.my_btn_round_notsave)

                }
            }
        }

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


//            ingredient_view.findViewById<EditText>(R.id.my_recipe_edt_ingredientname).getText()
//            ingredient_view.findViewById<EditText>(R.id.my_recipe_edt_ingredientqun).getText()

            //editor.putString(ingredient_title_sp,viewBinding.myRecipeEdtIngredientname.text.toString())
//            editor.putString(ingredient_quan_sp,viewBinding.myRecipeEdtIngredientqun.text.toString())
        }
        var picList = arrayListOf<String>()
        var textList = arrayListOf<String>()
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

        //임시저장 버튼 눌렀을때
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

        //뒤로가기 할때
        viewBinding.myBackbtn.setOnClickListener {

        }

        //업로드 버튼 눌렀을때
        viewBinding.myUploadbtn.setOnClickListener {
            editor.putString("title", viewBinding.myRecipeEdtTital.text.toString())
            editor.putString("time", viewBinding.myRecipeEdtTime.text.toString())
            editor.putString("describe", viewBinding.myRecipeEdtDescribe.text.toString())
            editor.putString("aftertip", viewBinding.myRecipeEdtAftertip.text.toString())
            editor.putString("ingridient1_title", viewBinding.myRecipeEdtIngredientname.text.toString())
            editor.putString("ingridient1_quan", viewBinding.myRecipeEdtIngredientqun.text.toString())
            editor.putString("step1_describe", viewBinding.myRecipeEdtStep.text.toString())
            editor.apply()

            sharedPreference.getString("title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("time", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("aftertip", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient1_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient1_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step1_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("category", "@")?.let { Log.e(ContentValues.TAG, it) }

            //카테고리 선택
            if (viewBinding.myCoffee.isSelected) {
                editor.putString("category", "coffee")
                editor.apply()
            } else if (viewBinding.myBeverage.isSelected) {
                editor.putString("category", "beverage")
                editor.apply()
            } else if (viewBinding.myTea.isSelected) {
                editor.putString("category", "tea")
                editor.apply()
            } else if (viewBinding.myAde.isSelected) {
                editor.putString("category", "ade")
                editor.apply()
            } else if (viewBinding.mySmudi.isSelected) {
                editor.putString("category", "smudi")
                editor.apply()
            } else if (viewBinding.myHealth.isSelected) {
                editor.putString("category", "health")
                editor.apply()
            } else {
                editor.putString("category", "")
                editor.apply()
            }

            //업로드하시겠습니까? 다이얼로그 띄우기
            binding_upload = DialogUploadBinding.inflate(layoutInflater)
            val dialog_upload_builder = AlertDialog.Builder(this).setView(binding_upload.root)
            val dialog_upload = dialog_upload_builder.create()

            //dialog_uploadcategory.window?.setFeatureDrawableResource(ColorDrawable(Color.parseColor()))
            dialog_upload.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            dialog_upload.setCanceledOnTouchOutside(true)
            dialog_upload.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog_upload.window?.setGravity(Gravity.BOTTOM)
            dialog_upload.setCancelable(true)

            //업로드 하겠습니다 버튼 눌렀을때
            binding_upload.myUploadbtn.setOnClickListener {
                //여기서 api 호출
                val recipe_list = PostNewRecipeList(
                    1,
                    sharedPreference.getString("title", ""),
                    sharedPreference.getString("describe", ""),
                    sharedPreference.getString("aftertip", ""),
                    sharedPreference.getString("time", ""),
                    sharedPreference.getString("aftertip", "")
                )
                val ingredient_list = arrayListOf<PostNewRecipeIngredient>(
                    PostNewRecipeIngredient(
                        sharedPreference.getString("ingredient1_title", ""),
                        sharedPreference.getString("ingredient1_quan", "")
                    )
                )
                val steps_list = arrayListOf<PostNewRecipeSteps>(
                    PostNewRecipeSteps(
                        sharedPreference.getString("step1_title", ""),
                        sharedPreference.getString("step1_describe", ""),
                        sharedPreference.getString("", "")
                    )
                )

                val body = PostNewRecipeBody(recipe_list, ingredient_list, steps_list)
                /*retrofit.post_newrecipe("x-access-token", body)
                    .enqueue(object : Callback<PostNewRecipeBodyResponse> {
                        override fun onResponse(
                            call: Call<PostNewRecipeBodyResponse>,
                            response: Response<PostNewRecipeBodyResponse>
                        ) {
                            Log.d("통신", "통신은 성공임")
                        }

                        override fun onFailure(
                            call: Call<PostNewRecipeBodyResponse>,
                            t: Throwable
                        ) {
                            Log.d("통신", "통신 실패..")
                        }
                    })*/
                //업로드 성공되었습니다 dialog 띄우기
                dialog_upload.dismiss()
                binding_uploadsuccess = DialogUploadsuccessBinding.inflate(layoutInflater)
                val dialog_uploadsuccess_builder = AlertDialog.Builder(this).setView(binding_uploadsuccess.root)
                val dialog_uploadsuccess = dialog_uploadsuccess_builder.create()

                dialog_uploadsuccess.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                dialog_uploadsuccess.setCanceledOnTouchOutside(true)
                dialog_uploadsuccess.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog_uploadsuccess.window?.setGravity(Gravity.BOTTOM)
                dialog_uploadsuccess.setCancelable(false)

                sharedPreference2.getString("thumbnail", "@")?.let { Log.e(ContentValues.TAG, it) }
                Glide.with(this)
                    .asBitmap()
                    .load(sharedPreference2.getString("thumbnail",""))
                    .centerCrop()
                    .into(binding_uploadsuccess.myUploadimg)

                //업로드 레시피 보러가기 눌렀을때
                binding_uploadsuccess.myUploaddonebtn.setOnClickListener {
                    dialog_uploadsuccess.dismiss()
                    finish()
                    //여기서 상세페이지 넘어가야함!!!!!
                }
                //나중에 보기 눌렀을때
                binding_uploadsuccess.myUploadseelaterbtn.setOnClickListener {
                    dialog_uploadsuccess.dismiss()
                    finish()
                }
                //x버튼 눌렀을때
                binding_uploadsuccess.myXbtn.setOnClickListener {
                    dialog_uploadsuccess.dismiss()
                    finish()
                }

                dialog_uploadsuccess.show()
            }

            //업로드 안하겠습니다 버튼 눌렀을때
            binding_upload.myCancelbtn.setOnClickListener {
                dialog_upload.dismiss()
            }

            dialog_upload.show()
        }


        //썸네일 사진 올리기
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

        //step1 사진 올리기
        viewBinding.myRecipeImageStep.setOnClickListener{
           viewBinding.myRecipeTvImage.setText("사진은 하나만 첨부가능합니다.")
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
    }



    private val imageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data ?: return@registerForActivityResult

            val file = File(absolutelyPath(imageUri, this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
            //sendImage(body)

            Log.d("테스트", file.name)

            val sharedPreference2 = getSharedPreferences("writing", 0)
            val editor2 = sharedPreference2.edit()
            editor2.putString("thumbnail", file.name)
            editor2.apply()
            sharedPreference2.getString("thumbnail", "@")?.let { Log.e(ContentValues.TAG, it) }

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val layout = viewBinding.myImage
                        layout.background = BitmapDrawable(resources, resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
    }
    private val imageResult1 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data ?: return@registerForActivityResult

            val file = File(absolutelyPath(imageUri, this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
            //sendImage(body)

            Log.d("테스트", file.name)

            val sharedPreference2 = getSharedPreferences("writing", 0)
            val editor2 = sharedPreference2.edit()
            editor2.putString("step1_image", file.name)
            editor2.apply()
            sharedPreference2.getString("step1_image", "@")?.let { Log.e(ContentValues.TAG, it) }

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val layout = viewBinding.myRecipeRealimageStep
                        layout.background = BitmapDrawable(resources, resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
    }

    private fun selectGallery(num: Int){
        val writePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)

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
   /* fun sendImage(body: MultipartBody.Part){
        retrofit.post_newrecipe_image("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsQG5hdmVyLmNvbSIsImlhdCI6MTY3NDYyNDA5OCwiZXhwIjoxNjc3MjE2MDk4LCJzdWIiOiJ1c2VySW5mbyJ9.ZEl388-pGKg02xaVO5fq3nVGBtn0QfgTiWEeX3laRl0", body).enqueue(object: Callback<PostNewRecipeImageBodyResponse>{
            override fun onResponse(call: Call<PostNewRecipeImageBodyResponse>, response: Response<PostNewRecipeImageBodyResponse>) {
                if(response.isSuccessful){
                    Log.d("통신","이미지 전송 성공")
                    val result = response.body()
                    val data = result?.image?.image
                    Log.d("로그",data.toString())
                }else{
                    Log.d("통신","이미지 전송 실패")
                }
            }
            override fun onFailure(call: Call<PostNewRecipeImageBodyResponse>, t: Throwable) {
                Log.d("통신",t.message.toString())
            }
        })
    }*/
    companion object{
        const val REQ_GALLERY =1
    }
    fun absolutelyPath(path: Uri?, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

   /* override fun onBackPressed() {
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
    }*/
}
