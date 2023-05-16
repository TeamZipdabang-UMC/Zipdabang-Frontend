package com.UMC.umc_zipdabang.config.src.main.signup

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.UMC.umc_zipdabang.R
import com.UMC.umc_zipdabang.databinding.ActivitySignupSecondBinding
import com.UMC.umc_zipdabang.src.my.CustomToast

class SignupSecondActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignupSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding= ActivitySignupSecondBinding.inflate((layoutInflater))
        setContentView(viewBinding.root)

        var phonenumber = viewBinding.signupRealedtPhonenumber
        var email =viewBinding.signupRealedtEmail
        var birthday =viewBinding.signupRealedtBirthday
        var nextBtn = viewBinding.signupNextbtn

        val intent_email = intent.getStringExtra("email").toString()
        email.text = intent_email

        Log.d("second 스트링", "${intent_email}")

        phonenumber.addTextChangedListener{
            if(phonenumber.text.toString().length > 0 && birthday.text.toString().length>0){
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
//        email.addTextChangedListener{
//            if(phonenumber.text.toString().length > 0 && email.text.toString().length >0 && birthday.text.toString().length>0){
//                nextBtn.setEnabled(true)
//                nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
//                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
//            }
//            else{
//                nextBtn.setEnabled(false)
//                nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
//                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
//            }
//        }
        birthday.addTextChangedListener{
            if(phonenumber.text.toString().length > 0 && birthday.text.toString().length>0){
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
        birthday.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                birthday.removeTextChangedListener(this)
                var string = birthday.getText().toString()
                if(string.length==6 && start != 6){
                    var laststring = string +"-"
                    birthday.setText(laststring)
                    birthday.setSelection(birthday.length())
                }
                birthday.addTextChangedListener(this)
            }
        })

        val sharedPreference = getSharedPreferences("signup",0)
        val editor = sharedPreference.edit()

        var phonenumber_sp =sharedPreference.getString("phonenumber","")
        var email_sp =sharedPreference.getString("email","")
        var birthday_sp =sharedPreference.getString("birthday","")

        if(phonenumber_sp != null && birthday_sp !=null){
            phonenumber.setText(sharedPreference.getString("phonenumber",""))
//            email.setText(sharedPreference.getString("email",""))
            birthday.setText(sharedPreference.getString("birthday",""))
        }else{
            phonenumber.setText(sharedPreference.getString("phonenumber",""))
//            email.setText(sharedPreference.getString("email",""))
            birthday.setText(sharedPreference.getString("birthday",""))
        }

        nextBtn.setOnClickListener {
            if(!validPhonenumber()){
                return@setOnClickListener
            }
            if(!validBirthday()){
                return@setOnClickListener
            }

            editor.putString("phonenumber",phonenumber.text.toString())
            editor.putString("email", intent_email)
            editor.putString("birthday",birthday.text.toString())
            editor.apply()

            sharedPreference.getString("phonenumber","")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("email","")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("birthday","")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("name","")?.let { Log.e(ContentValues.TAG, it) }
            sharedPreference.getString("nickname","")?.let { Log.e(ContentValues.TAG, it) }

            val intent = Intent(this, SignupResearchActivity::class.java)
            //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            finish()
            startActivity(intent)
        }
        viewBinding.signupBackbtn.setOnClickListener {
            editor.putString("phonenumber",phonenumber.text.toString())
            editor.putString("email",intent_email)
            editor.putString("birthday",birthday.text.toString())
            editor.apply()

            val intent = Intent(this, SignupFirstActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

            startActivity(intent)
        }
    }

    private fun validPhonenumber():Boolean{
        val value: String = viewBinding.signupRealedtPhonenumber?.text.toString().trim()
        val phonenumberpattern = "^[0-9]{11}$"
        return if(!value.matches(phonenumberpattern.toRegex())){
            //viewBinding.signupRealedtPhonenumber.error="전화번호 형식이 잘못되었습니다."
            CustomToast.createToast(applicationContext, "전화번호 형식이 잘못되었습니다")?.show()
            false
        } else{
            viewBinding.signupRealedtPhonenumber.error=null
            true
        }
    }
//    private fun validEmail():Boolean{
//        val value: String = viewBinding.signupRealedtEmail?.text.toString().trim()
//        val emailpattern = android.util.Patterns.EMAIL_ADDRESS
//        //val emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
//        return if(!value.matches(emailpattern.toRegex())){
//            viewBinding.signupRealedtEmail.error="이메일 형식이 잘못되었습니다."
//            false
//        } else{
//            viewBinding.signupRealedtEmail.error=null
//            true
//        }
//    }
    private fun validBirthday():Boolean{
        val value: String = viewBinding.signupRealedtBirthday?.text.toString().trim()
        val birthdaypattern = "^[0-9-]{8}$"
        return if(!value.matches(birthdaypattern.toRegex())){
            //viewBinding.signupRealedtBirthday.error="형식이 잘못되었습니다."
            CustomToast.createToast(applicationContext, "생년월일 형식이 잘못되었습니다")?.show()
            false
        } else{
            viewBinding.signupRealedtBirthday.error =null
            true
        }
    }
}