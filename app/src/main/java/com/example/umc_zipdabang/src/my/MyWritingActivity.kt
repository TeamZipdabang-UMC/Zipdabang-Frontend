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
import androidx.core.view.isVisible
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

    //sharedpreference, api 코드작성
    //step 지웠는데 왜 사진 남아있냐 ㅠㅠ null 처리했는데...

    //업로드 버튼 다썼을때 활성화되게 하기
    //임시저장 해둔게 있으면 글쓰기 전에 dialog 띄우기

    //카메라 권한 받아오기

    //jpg 오류 -> base64 인코딩해야하냐 -> jpg 오류 안나는데 뭐임

    //toast 띄우기
    ////내레시피 안됨 ㅠㅠ

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
        val editor = sharedPreference.edit() //제목, 카테고리, 시간, 한줄소개, 재료이름, 재료갯수, 스텝설명, 후기, 재료스탭 갯수
        val sharedPreference2 = getSharedPreferences("writing_image", 0)
        val editor2 = sharedPreference2.edit() //이미지의 url을 담음 //썸네일, stp1사진, step2사진, ...


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


        //재료 + 버튼 눌렀을때
        var num: Int = 1
        viewBinding.myIngredientPlusbtn.setOnClickListener {
            num++
            if(num> 10){
                num--
            }
            viewBinding.myIngredientNumtv.setText("" + num + "/10")
            if(num == 2){
                viewBinding.myLinearIngredient2User.visibility = View.VISIBLE
            }else if(num==3){
                viewBinding.myLinearIngredient3User.visibility = View.VISIBLE
            }else if(num==4){
                viewBinding.myLinearIngredient4User.visibility = View.VISIBLE
            }else if(num==5){
                viewBinding.myLinearIngredient5User.visibility = View.VISIBLE
            }else if(num==6){
                viewBinding.myLinearIngredient6User.visibility = View.VISIBLE
            }else if(num==7){
                viewBinding.myLinearIngredient7User.visibility = View.VISIBLE
            }else if(num==8){
                viewBinding.myLinearIngredient8User.visibility = View.VISIBLE
            }else if(num==9){
                viewBinding.myLinearIngredient9User.visibility = View.VISIBLE
            }else if(num==10){
                viewBinding.myLinearIngredient10User.visibility = View.VISIBLE
            }
            Log.d("TAG","${num}")
        }
        //재료 - 버튼 눌렀을때
        viewBinding.myIngredientMinusbtn.setOnClickListener{
            if(num==10){
                viewBinding.myRecipeEdtIngredient10name.setText(null)
                viewBinding.myRecipeEdtIngredient10qun.setText(null)
                viewBinding.myLinearIngredient10User.visibility = View.GONE
            }else if(num == 9){
                viewBinding.myRecipeEdtIngredient9name.setText(null)
                viewBinding.myRecipeEdtIngredient9qun.setText(null)
                viewBinding.myLinearIngredient9User.visibility= View.GONE
            }else if(num == 8){
                viewBinding.myRecipeEdtIngredient8name.setText(null)
                viewBinding.myRecipeEdtIngredient8qun.setText(null)
                viewBinding.myLinearIngredient8User.visibility= View.GONE
            }else if(num == 7){
                viewBinding.myRecipeEdtIngredient7name.setText(null)
                viewBinding.myRecipeEdtIngredient7qun.setText(null)
                viewBinding.myLinearIngredient7User.visibility= View.GONE
            }else if(num == 6){
                viewBinding.myRecipeEdtIngredient6name.setText(null)
                viewBinding.myRecipeEdtIngredient6qun.setText(null)
                viewBinding.myLinearIngredient6User.visibility= View.GONE
            }else if(num == 5){
                viewBinding.myRecipeEdtIngredient5name.setText(null)
                viewBinding.myRecipeEdtIngredient5qun.setText(null)
                viewBinding.myLinearIngredient5User.visibility= View.GONE
            }else if(num == 4){
                viewBinding.myRecipeEdtIngredient4name.setText(null)
                viewBinding.myRecipeEdtIngredient4qun.setText(null)
                viewBinding.myLinearIngredient4User.visibility= View.GONE
            }else if(num == 3){
                viewBinding.myRecipeEdtIngredient3name.setText(null)
                viewBinding.myRecipeEdtIngredient3qun.setText(null)
                viewBinding.myLinearIngredient3User.visibility= View.GONE
            }else if(num == 2){
                viewBinding.myRecipeEdtIngredient2name.setText(null)
                viewBinding.myRecipeEdtIngredient2qun.setText(null)
                viewBinding.myLinearIngredient2User.visibility= View.GONE
            }
            num--
            if(num == 0){
                num++
            }
            viewBinding.myIngredientNumtv.setText("" + num + "/10")
            Log.d("TAG","${num}")
        }
        //스텝 +버튼 눌렀을때
        var num2: Int = 1
        viewBinding.myStepPlusbtn.setOnClickListener{
            num2++
            if(num2 > 10){
                num2--
            }
            Log.d("TAG","${num2}")
            viewBinding.myStepNumtv.setText("Step" + num2 + "/Step10")

            if(num2 == 2){
                viewBinding.myStep2.visibility = View.VISIBLE
            }else if(num2==3){
                viewBinding.myStep3.visibility = View.VISIBLE
            }else if(num2==4){
                viewBinding.myStep4.visibility = View.VISIBLE
            }else if(num2==5){
                viewBinding.myStep5.visibility = View.VISIBLE
            }else if(num2==6){
                viewBinding.myStep6.visibility = View.VISIBLE
            }else if(num2==7){
                viewBinding.myStep7.visibility = View.VISIBLE
            }else if(num2==8){
                viewBinding.myStep8.visibility = View.VISIBLE
            }else if(num2==9){
                viewBinding.myStep9.visibility = View.VISIBLE
            }else if(num2==10){
                viewBinding.myStep10.visibility = View.VISIBLE
            }
        }
        //스텝 -버튼 눌렀을때
        viewBinding.myStepMinusbtn.setOnClickListener{
            if(num2==10){
                viewBinding.myRecipeRealimageXbtn10.visibility = View.INVISIBLE
                viewBinding.myRecipeRealimageStep10.setImageBitmap(null)
                viewBinding.myRecipeEdtStep10.setText(null)
                viewBinding.myStep10.visibility = View.GONE
            }else if(num2 == 9){
                viewBinding.myRecipeRealimageXbtn9.visibility = View.INVISIBLE
                viewBinding.myRecipeRealimageStep9.setImageBitmap(null)
                viewBinding.myRecipeEdtStep9.setText(null)
                viewBinding.myStep9.visibility= View.GONE
            }else if(num2 == 8){
                viewBinding.myRecipeRealimageXbtn8.visibility = View.INVISIBLE
                viewBinding.myRecipeRealimageStep8.setImageBitmap(null)
                viewBinding.myRecipeEdtStep8.setText(null)
                viewBinding.myStep8.visibility= View.GONE
            }else if(num2 == 7){
                viewBinding.myRecipeRealimageXbtn7.visibility = View.INVISIBLE
                viewBinding.myRecipeRealimageStep7.setImageBitmap(null)
                viewBinding.myRecipeEdtStep7.setText(null)
                viewBinding.myStep7.visibility= View.GONE
            }else if(num2 == 6){
                viewBinding.myRecipeRealimageXbtn6.visibility = View.INVISIBLE
                viewBinding.myRecipeRealimageStep6.setImageBitmap(null)
                viewBinding.myRecipeEdtStep6.setText(null)
                viewBinding.myStep6.visibility= View.GONE
            }else if(num2 == 5){
                viewBinding.myRecipeRealimageXbtn5.visibility = View.INVISIBLE
                viewBinding.myRecipeRealimageStep5.setImageBitmap(null)
                viewBinding.myRecipeEdtStep5.setText(null)
                viewBinding.myStep5.visibility= View.GONE
            }else if(num2 == 4){
                viewBinding.myRecipeRealimageXbtn4.visibility = View.INVISIBLE
                viewBinding.myRecipeRealimageStep4.setImageBitmap(null)
                viewBinding.myRecipeEdtStep4.setText(null)
                viewBinding.myStep4.visibility= View.GONE
            }else if(num2 == 3){
                viewBinding.myRecipeRealimageXbtn3.visibility = View.INVISIBLE
                viewBinding.myRecipeRealimageStep3.setImageBitmap(null)
                viewBinding.myRecipeEdtStep3.setText(null)
                viewBinding.myStep3.visibility= View.GONE
            }else if(num2 == 2){
                viewBinding.myRecipeRealimageXbtn2.visibility = View.INVISIBLE
                viewBinding.myRecipeRealimageStep2.setImageBitmap(null)
                viewBinding.myRecipeEdtStep2.setText(null)
                viewBinding.myStep2.visibility= View.GONE
            }
            num2--
            if(num2 == 0){
                num2++
            }
            viewBinding.myStepNumtv.setText("Step" + num2 + "/Step10")
            Log.d("TAG","${num2}")
        }


        //뒤로가기 할때
        viewBinding.myBackbtn.setOnClickListener {
            //onbackpressed 누를때랑 똑같이 복붙
            editor.clear()
            editor.apply()
            editor2.clear()
            editor2.apply()
        }


        //임시저장 버튼 눌렀을때
        viewBinding.mySavebtn.setOnClickListener {
            editor.putString("ingredient",num.toString())
            editor.putString("step",num2.toString())
            editor.putString("title", viewBinding.myRecipeEdtTital.text.toString())
            editor.putString("time", viewBinding.myRecipeEdtTime.text.toString())
            editor.putString("describe", viewBinding.myRecipeEdtDescribe.text.toString())
            editor.putString("aftertip", viewBinding.myRecipeEdtAftertip.text.toString())
            editor.putString("ingredient1_title",viewBinding.myRecipeEdtIngredientname.text.toString())
            editor.putString("ingredient1_quan",viewBinding.myRecipeEdtIngredientqun.text.toString())
            editor.putString("ingredient2_title",viewBinding.myRecipeEdtIngredient2name.text.toString())
            editor.putString("ingredient2_quan",viewBinding.myRecipeEdtIngredient2qun.text.toString())
            editor.putString("ingredient3_title",viewBinding.myRecipeEdtIngredient3name.text.toString())
            editor.putString("ingredient3_quan",viewBinding.myRecipeEdtIngredient3qun.text.toString())
            editor.putString("ingredient4_title",viewBinding.myRecipeEdtIngredient4name.text.toString())
            editor.putString("ingredient4_quan",viewBinding.myRecipeEdtIngredient4qun.text.toString())
            editor.putString("ingredient5_title",viewBinding.myRecipeEdtIngredient5name.text.toString())
            editor.putString("ingredient5_quan",viewBinding.myRecipeEdtIngredient5qun.text.toString())
            editor.putString("ingredient6_title",viewBinding.myRecipeEdtIngredient6name.text.toString())
            editor.putString("ingredient6_quan",viewBinding.myRecipeEdtIngredient6qun.text.toString())
            editor.putString("ingredient7_title",viewBinding.myRecipeEdtIngredient7name.text.toString())
            editor.putString("ingredient7_quan",viewBinding.myRecipeEdtIngredient7qun.text.toString())
            editor.putString("ingredient8_title",viewBinding.myRecipeEdtIngredient8name.text.toString())
            editor.putString("ingredient8_quan",viewBinding.myRecipeEdtIngredient8qun.text.toString())
            editor.putString("ingredient9_title",viewBinding.myRecipeEdtIngredient9name.text.toString())
            editor.putString("ingredient9_quan",viewBinding.myRecipeEdtIngredient9qun.text.toString())
            editor.putString("ingredient10_title",viewBinding.myRecipeEdtIngredient10name.text.toString())
            editor.putString("ingredient10_quan",viewBinding.myRecipeEdtIngredient10qun.text.toString())
            editor.putString("step1_describe",viewBinding.myRecipeEdtStep.text.toString())
            editor.putString("step2_describe",viewBinding.myRecipeEdtStep2.text.toString())
            editor.putString("step3_describe",viewBinding.myRecipeEdtStep3.text.toString())
            editor.putString("step4_describe",viewBinding.myRecipeEdtStep4.text.toString())
            editor.putString("step5_describe",viewBinding.myRecipeEdtStep5.text.toString())
            editor.putString("step6_describe",viewBinding.myRecipeEdtStep6.text.toString())
            editor.putString("step7_describe",viewBinding.myRecipeEdtStep7.text.toString())
            editor.putString("step8_describe",viewBinding.myRecipeEdtStep8.text.toString())
            editor.putString("step9_describe",viewBinding.myRecipeEdtStep9.text.toString())
            editor.putString("step10_describe",viewBinding.myRecipeEdtStep10.text.toString())
            editor.apply()

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

            sharedPreference.getString("ingredient", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("category", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("time", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("aftertip", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient1_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient1_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient2_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient2_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient3_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient3_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient4_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient4_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient5_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient5_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient6_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient6_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient7_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient7_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient8_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient8_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient9_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient9_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient10_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient10_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step1_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step2_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step3_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step4_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step5_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step6_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step7_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step8_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step9_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step10_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step1_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step2_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step3_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step4_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step5_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step6_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step7_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step8_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step9_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step10_image", "@")?.let { Log.e(ContentValues.TAG, it) }

            //임시저장 dialog 띄우기
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
                editor2.clear()
                editor2.apply()
                dialog_save.onBackPressed()
            }
            binding_save.mySavebtn.setOnClickListener{
                dialog_save.dismiss()
                finish()
            }
            dialog_save.show()
        }
        //업로드 버튼 눌렀을때
        viewBinding.myUploadbtn.setOnClickListener {
            //업로드 할때 sp 필요없음 sp2는 api에서 필요함
            editor.putInt("ingredient",num)
            editor.putInt("step",num2)
            editor.putString("title", viewBinding.myRecipeEdtTital.text.toString())
            editor.putString("time", viewBinding.myRecipeEdtTime.text.toString())
            editor.putString("describe", viewBinding.myRecipeEdtDescribe.text.toString())
            editor.putString("aftertip", viewBinding.myRecipeEdtAftertip.text.toString())
            editor.putString("ingredient1_title",viewBinding.myRecipeEdtIngredientname.text.toString())
            editor.putString("ingredient1_quan",viewBinding.myRecipeEdtIngredientqun.text.toString())
            editor.putString("ingredient2_title",viewBinding.myRecipeEdtIngredient2name.text.toString())
            editor.putString("ingredient2_quan",viewBinding.myRecipeEdtIngredient2qun.text.toString())
            editor.putString("ingredient3_title",viewBinding.myRecipeEdtIngredient3name.text.toString())
            editor.putString("ingredient3_quan",viewBinding.myRecipeEdtIngredient3qun.text.toString())
            editor.putString("ingredient4_title",viewBinding.myRecipeEdtIngredient4name.text.toString())
            editor.putString("ingredient4_quan",viewBinding.myRecipeEdtIngredient4qun.text.toString())
            editor.putString("ingredient5_title",viewBinding.myRecipeEdtIngredient5name.text.toString())
            editor.putString("ingredient5_quan",viewBinding.myRecipeEdtIngredient5qun.text.toString())
            editor.putString("ingredient6_title",viewBinding.myRecipeEdtIngredient6name.text.toString())
            editor.putString("ingredient6_quan",viewBinding.myRecipeEdtIngredient6qun.text.toString())
            editor.putString("ingredient7_title",viewBinding.myRecipeEdtIngredient7name.text.toString())
            editor.putString("ingredient7_quan",viewBinding.myRecipeEdtIngredient7qun.text.toString())
            editor.putString("ingredient8_title",viewBinding.myRecipeEdtIngredient8name.text.toString())
            editor.putString("ingredient8_quan",viewBinding.myRecipeEdtIngredient8qun.text.toString())
            editor.putString("ingredient9_title",viewBinding.myRecipeEdtIngredient9name.text.toString())
            editor.putString("ingredient9_quan",viewBinding.myRecipeEdtIngredient9qun.text.toString())
            editor.putString("ingredient10_title",viewBinding.myRecipeEdtIngredient10name.text.toString())
            editor.putString("ingredient10_quan",viewBinding.myRecipeEdtIngredient10qun.text.toString())
            editor.putString("step1_describe",viewBinding.myRecipeEdtStep.text.toString())
            editor.putString("step2_describe",viewBinding.myRecipeEdtStep2.text.toString())
            editor.putString("step3_describe",viewBinding.myRecipeEdtStep3.text.toString())
            editor.putString("step4_describe",viewBinding.myRecipeEdtStep4.text.toString())
            editor.putString("step5_describe",viewBinding.myRecipeEdtStep5.text.toString())
            editor.putString("step6_describe",viewBinding.myRecipeEdtStep6.text.toString())
            editor.putString("step7_describe",viewBinding.myRecipeEdtStep7.text.toString())
            editor.putString("step8_describe",viewBinding.myRecipeEdtStep8.text.toString())
            editor.putString("step9_describe",viewBinding.myRecipeEdtStep9.text.toString())
            editor.putString("step10_describe",viewBinding.myRecipeEdtStep10.text.toString())
            editor.apply()

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

            sharedPreference.getString("ingredient", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("category", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("time", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("aftertip", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient1_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient1_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient2_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient2_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient3_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient3_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient4_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient4_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient5_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient5_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient6_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient6_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient7_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient7_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient8_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient8_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient9_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient9_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient10_title", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("ingredient10_quan", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step1_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step2_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step3_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step4_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step5_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step6_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step7_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step8_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step9_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("step10_describe", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step1_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step2_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step3_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step4_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step5_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step6_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step7_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step8_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step9_image", "@")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference2.getString("step10_image", "@")?.let { Log.e(ContentValues.TAG, it) }

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
                editor.clear()
                editor.apply()
                editor2.clear()
                editor2.apply()

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
                editor.clear()
                editor.apply()
                editor2.clear()
                editor2.apply()
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
                selectGallery(0)
                viewBinding.myImage.bringToFront()
                dialog_camera.dismiss()
            }

            dialog_camera.show()
        }
        //step1 사진 올리기
        viewBinding.myRecipeImageStep.setOnClickListener{
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
               selectGallery(1)
               viewBinding.myRecipeRealimageStep.bringToFront()
               viewBinding.myRecipeRealimageXbtn.visibility = View.VISIBLE
               viewBinding.myRecipeRealimageXbtn.bringToFront()
               dialog_camera.dismiss()
           }
           dialog_camera.show()
       }
        //step2 사진 올리기
        viewBinding.myRecipeImageStep2.setOnClickListener{
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
                selectGallery(2)
                viewBinding.myRecipeRealimageStep2.bringToFront()
                viewBinding.myRecipeRealimageXbtn2.visibility = View.VISIBLE
                viewBinding.myRecipeRealimageXbtn2.bringToFront()
                dialog_camera.dismiss()
            }
            dialog_camera.show()
        }
        //step3 사진 올리기
        viewBinding.myRecipeImageStep3.setOnClickListener{
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
                selectGallery(3)
                viewBinding.myRecipeRealimageStep3.bringToFront()
                viewBinding.myRecipeRealimageXbtn3.visibility = View.VISIBLE
                viewBinding.myRecipeRealimageXbtn3.bringToFront()
                dialog_camera.dismiss()
            }
            dialog_camera.show()
        }
        //step4 사진 올리기
        viewBinding.myRecipeImageStep4.setOnClickListener{
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
                selectGallery(4)
                viewBinding.myRecipeRealimageStep4.bringToFront()
                viewBinding.myRecipeRealimageXbtn4.visibility = View.VISIBLE
                viewBinding.myRecipeRealimageXbtn4.bringToFront()
                dialog_camera.dismiss()
            }
            dialog_camera.show()
        }
        //step5 사진 올리기
        viewBinding.myRecipeImageStep5.setOnClickListener{
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
                selectGallery(5)
                viewBinding.myRecipeRealimageStep5.bringToFront()
                viewBinding.myRecipeRealimageXbtn5.visibility = View.VISIBLE
                viewBinding.myRecipeRealimageXbtn5.bringToFront()
                dialog_camera.dismiss()
            }
            dialog_camera.show()
        }
        //step6 사진 올리기
        viewBinding.myRecipeImageStep6.setOnClickListener{
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
                selectGallery(6)
                viewBinding.myRecipeRealimageStep6.bringToFront()
                viewBinding.myRecipeRealimageXbtn6.visibility = View.VISIBLE
                viewBinding.myRecipeRealimageXbtn6.bringToFront()
                dialog_camera.dismiss()
            }
            dialog_camera.show()
        }
        //step7 사진 올리기
        viewBinding.myRecipeImageStep7.setOnClickListener{
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
                selectGallery(7)
                viewBinding.myRecipeRealimageStep7.bringToFront()
                viewBinding.myRecipeRealimageXbtn7.visibility = View.VISIBLE
                viewBinding.myRecipeRealimageXbtn7.bringToFront()
                dialog_camera.dismiss()
            }
            dialog_camera.show()
        }
        //step8 사진 올리기
        viewBinding.myRecipeImageStep8.setOnClickListener{
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
                selectGallery(8)
                viewBinding.myRecipeRealimageStep8.bringToFront()
                viewBinding.myRecipeRealimageXbtn8.visibility = View.VISIBLE
                viewBinding.myRecipeRealimageXbtn8.bringToFront()
                dialog_camera.dismiss()
            }
            dialog_camera.show()
        }
        //step9 사진 올리기
        viewBinding.myRecipeImageStep9.setOnClickListener{
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
                selectGallery(9)
                viewBinding.myRecipeRealimageStep9.bringToFront()
                viewBinding.myRecipeRealimageXbtn9.visibility = View.VISIBLE
                viewBinding.myRecipeRealimageXbtn9.bringToFront()
                dialog_camera.dismiss()
            }
            dialog_camera.show()
        }
        //step10 사진 올리기
        viewBinding.myRecipeImageStep10.setOnClickListener{
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
                selectGallery(10)
                viewBinding.myRecipeRealimageStep10.bringToFront()
                viewBinding.myRecipeRealimageXbtn10.visibility = View.VISIBLE
                viewBinding.myRecipeRealimageXbtn10.bringToFront()
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

            val sharedPreference2 = getSharedPreferences("writing_image", 0)
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

            val sharedPreference2 = getSharedPreferences("writing_image", 0)
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
    private val imageResult2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data ?: return@registerForActivityResult

            val file = File(absolutelyPath(imageUri, this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
            //sendImage(body)

            Log.d("테스트", file.name)

            val sharedPreference2 = getSharedPreferences("writing_image", 0)
            val editor2 = sharedPreference2.edit()
            editor2.putString("step2_image", file.name)
            editor2.apply()
            sharedPreference2.getString("step2_image", "@")?.let { Log.e(ContentValues.TAG, it) }

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val layout = viewBinding.myRecipeRealimageStep2
                        layout.background = BitmapDrawable(resources, resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
    }
    private val imageResult3 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data ?: return@registerForActivityResult

            val file = File(absolutelyPath(imageUri, this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
            //sendImage(body)

            Log.d("테스트", file.name)

            val sharedPreference2 = getSharedPreferences("writing_image", 0)
            val editor2 = sharedPreference2.edit()
            editor2.putString("step3_image", file.name)
            editor2.apply()
            sharedPreference2.getString("step3_image", "@")?.let { Log.e(ContentValues.TAG, it) }

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val layout = viewBinding.myRecipeRealimageStep3
                        layout.background = BitmapDrawable(resources, resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
    }
    private val imageResult4 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data ?: return@registerForActivityResult

            val file = File(absolutelyPath(imageUri, this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
            //sendImage(body)

            Log.d("테스트", file.name)

            val sharedPreference2 = getSharedPreferences("writing_image", 0)
            val editor2 = sharedPreference2.edit()
            editor2.putString("step4_image", file.name)
            editor2.apply()
            sharedPreference2.getString("step4_image", "@")?.let { Log.e(ContentValues.TAG, it) }

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val layout = viewBinding.myRecipeRealimageStep4
                        layout.background = BitmapDrawable(resources, resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
    }
    private val imageResult5 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data ?: return@registerForActivityResult

            val file = File(absolutelyPath(imageUri, this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
            //sendImage(body)

            Log.d("테스트", file.name)

            val sharedPreference2 = getSharedPreferences("writing_image", 0)
            val editor2 = sharedPreference2.edit()
            editor2.putString("step5_image", file.name)
            editor2.apply()
            sharedPreference2.getString("step5_image", "@")?.let { Log.e(ContentValues.TAG, it) }

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val layout = viewBinding.myRecipeRealimageStep5
                        layout.background = BitmapDrawable(resources, resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
    }
    private val imageResult6 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data ?: return@registerForActivityResult

            val file = File(absolutelyPath(imageUri, this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
            //sendImage(body)

            Log.d("테스트", file.name)

            val sharedPreference2 = getSharedPreferences("writing_image", 0)
            val editor2 = sharedPreference2.edit()
            editor2.putString("step6_image", file.name)
            editor2.apply()
            sharedPreference2.getString("step6_image", "@")?.let { Log.e(ContentValues.TAG, it) }

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val layout = viewBinding.myRecipeRealimageStep6
                        layout.background = BitmapDrawable(resources, resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
    }
    private val imageResult7 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data ?: return@registerForActivityResult

            val file = File(absolutelyPath(imageUri, this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
            //sendImage(body)

            Log.d("테스트", file.name)

            val sharedPreference2 = getSharedPreferences("writing_image", 0)
            val editor2 = sharedPreference2.edit()
            editor2.putString("step7_image", file.name)
            editor2.apply()
            sharedPreference2.getString("step7_image", "@")?.let { Log.e(ContentValues.TAG, it) }

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val layout = viewBinding.myRecipeRealimageStep7
                        layout.background = BitmapDrawable(resources, resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
    }
    private val imageResult8 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data ?: return@registerForActivityResult

            val file = File(absolutelyPath(imageUri, this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
            //sendImage(body)

            Log.d("테스트", file.name)

            val sharedPreference2 = getSharedPreferences("writing_image", 0)
            val editor2 = sharedPreference2.edit()
            editor2.putString("step8_image", file.name)
            editor2.apply()
            sharedPreference2.getString("step8_image", "@")?.let { Log.e(ContentValues.TAG, it) }

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val layout = viewBinding.myRecipeRealimageStep8
                        layout.background = BitmapDrawable(resources, resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
    }
    private val imageResult9 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data ?: return@registerForActivityResult

            val file = File(absolutelyPath(imageUri, this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
            //sendImage(body)

            Log.d("테스트", file.name)

            val sharedPreference2 = getSharedPreferences("writing_image", 0)
            val editor2 = sharedPreference2.edit()
            editor2.putString("step9_image", file.name)
            editor2.apply()
            sharedPreference2.getString("step9_image", "@")?.let { Log.e(ContentValues.TAG, it) }

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val layout = viewBinding.myRecipeRealimageStep9
                        layout.background = BitmapDrawable(resources, resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
    }
    private val imageResult10 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data ?: return@registerForActivityResult

            val file = File(absolutelyPath(imageUri, this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
            val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
            //sendImage(body)

            Log.d("테스트", file.name)

            val sharedPreference2 = getSharedPreferences("writing_image", 0)
            val editor2 = sharedPreference2.edit()
            editor2.putString("step10_image", file.name)
            editor2.apply()
            sharedPreference2.getString("step10_image", "@")?.let { Log.e(ContentValues.TAG, it) }

            Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val layout = viewBinding.myRecipeRealimageStep10
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
            }else if(num == 2){
                imageResult2.launch(intent)
            } else if(num == 3){
                imageResult3.launch(intent)
            }else if(num == 4){
                imageResult4.launch(intent)
            }else if(num == 5){
                imageResult5.launch(intent)
            }else if(num == 6){
                imageResult6.launch(intent)
            }else if(num == 7){
                imageResult7.launch(intent)
            }else if(num == 8){
                imageResult8.launch(intent)
            }else if(num == 9){
                imageResult9.launch(intent)
            }else if(num == 10){
                imageResult10.launch(intent)
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
