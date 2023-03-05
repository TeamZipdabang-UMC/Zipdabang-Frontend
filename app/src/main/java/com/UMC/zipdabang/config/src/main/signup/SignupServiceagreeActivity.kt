package com.UMC.zipdabang.config.src.main.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivitySignupServiceagreeBinding

class SignupServiceagreeActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignupServiceagreeBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding= ActivitySignupServiceagreeBinding.inflate((layoutInflater))
        setContentView(viewBinding.root)

        val sharedPreference = getSharedPreferences("signup",0)
        val editor = sharedPreference.edit()

        var checkbtnmust1 = viewBinding.signupCheckbtnMust1
        var checkbtnmust2 =viewBinding.signupCheckbtnMust2
        var checkbtnchoice1 = viewBinding.signupCheckbtnChoice1
        var checkbtnchoice2 = viewBinding.signupCheckbtnChoice2
        var checkbtnallagree = viewBinding.signupCheckbtnAllagree
        var nextBtn = viewBinding.signupNextbtn
        var must1Btn = viewBinding.signupFramebtnMust1
        var must2Btn = viewBinding.signupFramebtnMust2
        var choice1Btn = viewBinding.signupFramebtnChoice1
        var choice2Btn = viewBinding.signupFramebtnChoice2

        checkbtnmust1.setOnClickListener{
            checkbtnmust1.isSelected = !(checkbtnmust1.isSelected == true)
            if(checkbtnmust1.isSelected){
                checkbtnmust1.setImageResource(R.drawable.sign_checkbtn_black)
            }
            else{
                checkbtnmust1.setImageResource(R.drawable.sign_checkbtn)
            }
            if(checkbtnmust1.isSelected && checkbtnmust2.isSelected){
                if(checkbtnmust1.isSelected && checkbtnmust2.isSelected && checkbtnchoice1.isSelected && checkbtnchoice2.isSelected){
                    checkbtnallagree.isSelected = true
                    checkbtnallagree.setImageResource(R.drawable.sign_checkbtn_black)

                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                }
                else{
                    checkbtnallagree.isSelected = false
                    checkbtnallagree.setImageResource(R.drawable.sign_checkbtn)

                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                }
            }
            else{
                nextBtn.setEnabled(false)
                nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
            }
        }
        checkbtnmust2.setOnClickListener{
            checkbtnmust2.isSelected = !(checkbtnmust2.isSelected == true)
            if(checkbtnmust2.isSelected){
                checkbtnmust2.setImageResource(R.drawable.sign_checkbtn_black)
            }
            else{
                checkbtnmust2.setImageResource(R.drawable.sign_checkbtn)
            }
            if(checkbtnmust1.isSelected && checkbtnmust2.isSelected){
                if(checkbtnmust1.isSelected && checkbtnmust2.isSelected && checkbtnchoice1.isSelected && checkbtnchoice2.isSelected){
                    checkbtnallagree.isSelected = true
                    checkbtnallagree.setImageResource(R.drawable.sign_checkbtn_black)

                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                }
                else{
                    checkbtnallagree.isSelected = false
                    checkbtnallagree.setImageResource(R.drawable.sign_checkbtn)

                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                }
            }
            else{
                nextBtn.setEnabled(false)
                nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
            }
        }
        checkbtnchoice1.setOnClickListener{
            checkbtnchoice1.isSelected = !(checkbtnchoice1.isSelected == true)
            if(checkbtnchoice1.isSelected){
                checkbtnchoice1.setImageResource(R.drawable.sign_checkbtn_black)
            }
            else{
                checkbtnchoice1.setImageResource(R.drawable.sign_checkbtn)
            }
            if(checkbtnmust1.isSelected && checkbtnmust2.isSelected){
                if(checkbtnmust1.isSelected && checkbtnmust2.isSelected && checkbtnchoice1.isSelected && checkbtnchoice2.isSelected){
                    checkbtnallagree.isSelected = true
                    checkbtnallagree.setImageResource(R.drawable.sign_checkbtn_black)

                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                }
                else{
                    checkbtnallagree.isSelected = false
                    checkbtnallagree.setImageResource(R.drawable.sign_checkbtn)

                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                }
            }
            else{
                nextBtn.setEnabled(false)
                nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
            }
        }
        checkbtnchoice2.setOnClickListener{
            checkbtnchoice2.isSelected = !(checkbtnchoice2.isSelected == true)
            if(checkbtnchoice2.isSelected){
                checkbtnchoice2.setImageResource(R.drawable.sign_checkbtn_black)
            }
            else{
                checkbtnchoice2.setImageResource(R.drawable.sign_checkbtn)
            }
            if(checkbtnmust1.isSelected && checkbtnmust2.isSelected){
                if(checkbtnmust1.isSelected && checkbtnmust2.isSelected && checkbtnchoice1.isSelected && checkbtnchoice2.isSelected){
                    checkbtnallagree.isSelected = true
                    checkbtnallagree.setImageResource(R.drawable.sign_checkbtn_black)

                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                }
                else{
                    checkbtnallagree.isSelected = false
                    checkbtnallagree.setImageResource(R.drawable.sign_checkbtn)

                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                }
            }
            else{
                nextBtn.setEnabled(false)
                nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
            }
        }
        checkbtnallagree.setOnClickListener{
            checkbtnallagree.isSelected = !(checkbtnallagree.isSelected == true)
            if(checkbtnallagree.isSelected){
                checkbtnmust1.isSelected = true
                checkbtnmust2.isSelected = true
                checkbtnchoice1.isSelected = true
                checkbtnchoice2.isSelected = true
                checkbtnmust1.setImageResource(R.drawable.sign_checkbtn_black)
                checkbtnmust2.setImageResource(R.drawable.sign_checkbtn_black)
                checkbtnchoice1.setImageResource(R.drawable.sign_checkbtn_black)
                checkbtnchoice2.setImageResource(R.drawable.sign_checkbtn_black)
                checkbtnallagree.setImageResource(R.drawable.sign_checkbtn_black)
            }
            else{
                checkbtnallagree.setImageResource(R.drawable.sign_checkbtn)
            }
            if(checkbtnmust1.isSelected && checkbtnmust2.isSelected){ //필수 버튼들 선택했을때
                nextBtn.setEnabled(true)
                nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
            }
            else{
                nextBtn.setEnabled(false)
                nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
            }
        }

        val intent_email = intent.getStringExtra("email").toString()
        Log.d("service 스트링", "${intent_email}")

        nextBtn.setOnClickListener {
            editor.putBoolean("must1", checkbtnmust1.isSelected)
            editor.putBoolean("must2",checkbtnmust2.isSelected)
            editor.putBoolean("choice1",checkbtnchoice1.isSelected)
            editor.putBoolean("choice2",checkbtnchoice2.isSelected)
            editor.apply()
            //sharedPreference.getString("name","")?.let { Log.e(ContentValues.TAG, it) }
            //sharedPreference.getString("nickname","")?.let { Log.e(ContentValues.TAG, it) }

            val intent = Intent(this, SignupCameraagreeActivity::class.java)
            intent.putExtra("email", intent_email)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            finish()
            startActivity(intent)
        }

        must1Btn.setOnClickListener {
//            val intent = Intent(this, SignupServiceagreeMust1Activity::class.java)
//            startActivity(intent)
            val agree_intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.notion.so/82b6e4652ec040f49d77cf99839c1bcc"))
            startActivity(agree_intent)
        }
        must2Btn.setOnClickListener {
//            val intent = Intent(this, SignupServiceagreeMust2Activity::class.java)
//            startActivity(intent)
            val agree_intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://app.catchsecu.com/document/P/33b1b25663ff8a3"))
            startActivity(agree_intent)
        }
        choice1Btn.setOnClickListener {
//            val intent = Intent(this, SignupServiceagreeChoice1Activity::class.java)
//            startActivity(intent)
            val agree_intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.notion.so/82b6e4652ec040f49d77cf99839c1bcc"))
            startActivity(agree_intent)
        }
        choice2Btn.setOnClickListener {
//            val intent = Intent(this, SignupServiceagreeChoice2Activity::class.java)
//            startActivity(intent)
            val agree_intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.notion.so/82b6e4652ec040f49d77cf99839c1bcc"))
            startActivity(agree_intent)
        }

        viewBinding.signupBackbtn.setOnClickListener {
            finish()
        }
    }
}