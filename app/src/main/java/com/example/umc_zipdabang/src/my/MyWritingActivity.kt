package com.example.umc_zipdabang.src.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityMyWritingBinding
import com.example.umc_zipdabang.databinding.LayoutStepBinding

class MyWritingActivity:AppCompatActivity() {
    private lateinit var viewBinding: ActivityMyWritingBinding
    private lateinit var viewBinding2: LayoutStepBinding

    //sharedpreference 연동하기->임시저장 만들자->api 통신
    //임시저장, 업로드 누를떄 dialog 제작하기

    //업로드 버튼 다 썼을때 활성화되게 하기
    //재료 삭제 버튼 만들기, 스텝 삭제 버튼 있나?


    //오류 메세지 띄우기
    ////layoutinflater text 왜 안변해...

    ////임시저장 하나만 할수 있다고 하기
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyWritingBinding.inflate(layoutInflater)
        viewBinding2 = LayoutStepBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

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
            viewBinding2.myRecipeBtn.setText("Step"+num2+" 사진 첨부")
            viewBinding2.myRecipeEdtStep.setHint("레시피를 만드는 방법의 "+num2+"단계를 설명해 주세요.\n"+"(최대 200자)")
            if(num2 == 10){
                viewBinding.myStepPlusbtn.setVisibility(View.INVISIBLE)
            }
        }

        viewBinding.myBackbtn.setOnClickListener {
            finish()
        }
    }
}