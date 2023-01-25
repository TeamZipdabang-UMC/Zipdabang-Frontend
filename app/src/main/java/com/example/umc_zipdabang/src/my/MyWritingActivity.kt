package com.example.umc_zipdabang.src.my

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityMyWritingBinding
import com.example.umc_zipdabang.databinding.LayoutStepBinding
import com.example.umc_zipdabang.src.my.etc.DeleteDialog
import com.example.umc_zipdabang.src.my.etc.ReallynotsaveDialog
import com.example.umc_zipdabang.src.my.etc.SaveDialog
import com.example.umc_zipdabang.src.my.etc.UploadcategoryDialog

class MyWritingActivity:AppCompatActivity() {
    private lateinit var viewBinding: ActivityMyWritingBinding
    private lateinit var viewBinding2: LayoutStepBinding

    //sharedpreference 쪽 api 연결하기
    //사진 업로드..

    //임시저장, 업로드 누를떄 dialog 제작하기

    //재료 스텝 삭제 버튼 구현
    //업로드 버튼 다썼을때 활성화되게 하기

    //재료 스탭 갯수 view 위치 이동되게
    //(오류 띄우는거 아이콘 만들기)재료 스탭 오류 띄우기
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyWritingBinding.inflate(layoutInflater)
        viewBinding2 = LayoutStepBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val sharedPreference = getSharedPreferences("writing",0)
        val editor = sharedPreference.edit()

        viewBinding.myUploadbtn.setOnClickListener {
            editor.putString("title", viewBinding.myRecipeEdtTital.text.toString())
            editor.putString("time", viewBinding.myRecipeEdtTime.text.toString())
            editor.putString("describe", viewBinding.myRecipeEdtDescribe.text.toString())
            editor.putString("aftertip", viewBinding.myRecipeEdtAftertip.text.toString())
            //재료, step, 사진 저장하기
        }

        viewBinding.myRecipeEdtTital.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if(viewBinding.myRecipeEdtTital.text.toString().length == 20){
                    viewBinding.myRecipeTvTital.error="최대 글자 수를 초과하였습니다."
                }else{
                    viewBinding.myRecipeTvTital.error=null
                }
            }
        })
        viewBinding.myRecipeEdtDescribe.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if(viewBinding.myRecipeEdtDescribe.text.toString().length == 100){
                    viewBinding.myRecipeTvDescribe.error="최대 글자 수를 초과하였습니다."
                }else{
                    viewBinding.myRecipeTvDescribe.error=null
                }
            }
        })
        viewBinding.myRecipeEdtStep.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if(viewBinding.myRecipeEdtStep.text.toString().length == 200){
                    viewBinding.myRecipeTvStep.error="최대 글자 수를 초과하였습니다."
                }else{
                    viewBinding.myRecipeTvStep.error=null
                }
            }
        })
        viewBinding.myRecipeEdtAftertip.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if(viewBinding.myRecipeEdtAftertip.text.toString().length == 200){
                    viewBinding.myRecipeTvAftertip.error="최대 글자 수를 초과하였습니다."
                }else{
                    viewBinding.myRecipeTvAftertip.error=null
                }
            }
        })
        var num : Int = 1
        viewBinding.myIngredientPlusbtn.setOnClickListener {
            val ingredient_view = LayoutInflater.from(this).inflate(R.layout.layout_ingredient, null)
            viewBinding.myLinearIngredientPlusframe.addView(ingredient_view)
            num ++
            viewBinding.myIngredientNumtv.setText(""+num+"/10")
            if(num == 10){
                viewBinding.myIngredientPlusbtn.setVisibility(View.INVISIBLE)
            }
        }
        var num2 : Int = 1
        viewBinding.myStepPlusbtn.setOnClickListener {
            val step_view = LayoutInflater.from(this).inflate(R.layout.layout_step, null)
            viewBinding.myLinearStepPlusframe.addView(step_view)
            num2 ++
            viewBinding.myStepNumtv.setText("Step"+num2+"/Step10")

            step_view.findViewById<TextView>(R.id.my_recipe_step_tv).setText("Step "+num2)
            step_view.findViewById<Button>(R.id.my_recipe_btn).setText("Step"+num2+" 사진 첨부")
            step_view.findViewById<TextView>(R.id.my_recipe_edt_step).setHint("레시피를 만드는 방법의 "+num2+"단계를 설명해 주세요.\n"+"(최대 200자)")

            if(num2 == 10){
                viewBinding.myStepPlusbtn.setVisibility(View.INVISIBLE)
            }
        }

        val dialog_save = SaveDialog(this)
        viewBinding.mySavebtn.setOnClickListener{
            dialog_save.showDialog()
        }

        val dialog_reallynotsave = ReallynotsaveDialog(this)
        viewBinding.myBackbtn.setOnClickListener {
            //내용이 남아있으면 if
            dialog_reallynotsave.showDialog()
            //내용 없으면 -> finish()
        }

        val dialog_uploadcategory = UploadcategoryDialog(this)
        viewBinding.myUploadbtn.setOnClickListener{
            dialog_uploadcategory.showDialog()
        }


        viewBinding2.myRecipeEdtStep.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if(viewBinding.myRecipeEdtStep.text.toString().length == 200){
                    viewBinding.myRecipeTvStep.error="최대 글자 수를 초과하였습니다."
                }else{
                    viewBinding.myRecipeTvStep.error=null
                }
            }
        })
    }

    override fun onBackPressed() {
        //내용 있을떄 띄우는걸로
        ReallynotsaveDialog(this).showDialog()
    }

}