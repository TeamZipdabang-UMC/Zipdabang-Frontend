package com.example.umc_zipdabang.src.signup

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivitySignupFirstBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupFirstActivity:AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignupFirstBinding
    val api = APIS.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivitySignupFirstBinding.inflate((layoutInflater))
        setContentView(viewBinding.root)

        var name = viewBinding.signupRealedtName
        var nickname =viewBinding.signupRealedtNickname
        var nextBtn = viewBinding.signupNextbtn

        name.addTextChangedListener{
            if(name.text.toString().length > 0 && nickname.text.toString().length >0){
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
        nickname.addTextChangedListener{
            if(name.text.toString().length > 0 && nickname.text.toString().length >0){
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

        //sp ??????
        val sharedPreference = getSharedPreferences("signup",0)
        val editor = sharedPreference.edit()
        //editor.clear()
        //editor.apply()

        var name_sp =sharedPreference.getString("name","")
        var nickname_sp =sharedPreference.getString("nickname","")
        if(name_sp != null && nickname_sp !=null){
            name.setText(sharedPreference.getString("name",""))
            nickname.setText(sharedPreference.getString("nickname",""))
        }else{
            name.setText("")
            nickname.setText("")
        }

        val intent_email = intent.getStringExtra("email").toString()
        Log.d("first ?????????", "${intent_email}")

        nextBtn.setOnClickListener{
            if(!validNickname()){
                return@setOnClickListener
            }
            editor.putString("name",name.text.toString())
            editor.putString("nickname",nickname.text.toString())
            editor.apply()
            sharedPreference.getString("name","")?.let { Log.e(TAG, it) }
            sharedPreference.getString("nickname","")?.let { Log.e(TAG, it) }

            /*
           //json ????????????
           val nickname_json = assets.open("~.json").reader().readText()

           for(i in 0 until nickname_json.length()){
               val jsonObject = nickname_json.getJSONObject(i)
               val nickname_exist = jsonObject.getString("nickname")
               if(nickname_sp != nickname_exist){
                   //?????? ????????? ?????????
               }else{
                   //?????? ????????????
               }
           }
            */
            var nickname_sp =sharedPreference.getString("nickname","")
            api.get_signup_existnickname(nickname_sp).enqueue(object: Callback<GetNicknameExistResponse>{
                override fun onResponse(call: Call<GetNicknameExistResponse>, response: Response<GetNicknameExistResponse>) {
                    if(response.body()?.exist == true){ //????????? ?????? ??????
                        Log.d("??????","success "+response.body()?.exist.toString() +response.body()?.nickname.toString())
                        nextBtn.setEnabled(false)
                        nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                        nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
                        //!!!!!?????? ????????? ????????????!!!!!
                    }else{ //????????? ?????? ??????
                        Log.d("??????","fail "+response.body()?.exist.toString())
                        val intent = Intent(this@SignupFirstActivity, SignupSecondActivity::class.java)
                        intent.putExtra("email", intent_email)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                    }
                }
                override fun onFailure(call: Call<GetNicknameExistResponse>, t: Throwable) {
                    Log.d("??????","?????? fail??????")
                }
            })


        }

        viewBinding.signupBackbtn.setOnClickListener{
            editor.putString("name",name.text.toString())
            editor.putString("nickname",nickname.text.toString())
            editor.apply()

            val intent = Intent(this,SignupCameraagreeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }

    private fun validNickname():Boolean{
        val value: String = viewBinding.signupRealedtNickname?.text.toString().trim()
        val nicknamepattern = "^[a-zA-Z0-9???-??????-??????-???]{2,10}$"
        return if(!value.matches(nicknamepattern.toRegex())){
            viewBinding.signupRealedtNickname.error="????????? ????????? ?????????????????????."
            false
        } else{
            viewBinding.signupRealedtNickname.error=null
            true
        }
    }
}