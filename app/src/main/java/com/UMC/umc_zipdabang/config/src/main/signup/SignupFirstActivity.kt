package com.UMC.umc_zipdabang.config.src.main.signup

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.UMC.umc_zipdabang.R
import com.UMC.umc_zipdabang.databinding.ActivitySignupFirstBinding
import com.UMC.umc_zipdabang.src.my.CustomToast
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

        //sp 사용
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
        Log.d("first 스트링", "${intent_email}")

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
           //json 파싱하기
           val nickname_json = assets.open("~.json").reader().readText()

           for(i in 0 until nickname_json.length()){
               val jsonObject = nickname_json.getJSONObject(i)
               val nickname_exist = jsonObject.getString("nickname")
               if(nickname_sp != nickname_exist){
                   //ㅇㅇ 닉네임 써도돼
               }else{
                   //오류 띄워야함
               }
           }
            */
            var nickname_sp =sharedPreference.getString("nickname","")
            api.get_signup_existnickname(nickname_sp).enqueue(object: Callback<GetNicknameExistResponse>{
                override fun onResponse(call: Call<GetNicknameExistResponse>, response: Response<GetNicknameExistResponse>) {
                    if(response.body()?.exist == true){ //닉네임 중복 ㅠㅠ
                        Log.d("통신","success "+response.body()?.exist.toString() +response.body()?.nickname.toString())
                        nextBtn.setEnabled(false)
                        nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                        nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))

                        //!!!!!오류 메세지 띄워야함!!!!!
                        CustomToast.createToast(applicationContext, "닉네임이 중복되었습니다")?.show()

                    }else{ //닉네임 중복 아님
                        Log.d("통신","fail "+response.body()?.exist.toString())
                        val intent = Intent(this@SignupFirstActivity, SignupSecondActivity::class.java)
                        intent.putExtra("email", intent_email)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        finish()
                        startActivity(intent)
                    }
                }
                override fun onFailure(call: Call<GetNicknameExistResponse>, t: Throwable) {
                    Log.d("통신","통신 failㅠㅠ")
                }
            })


        }

        viewBinding.signupBackbtn.setOnClickListener{
            editor.putString("name",name.text.toString())
            editor.putString("nickname",nickname.text.toString())
            editor.apply()

            val intent = Intent(this, SignupCameraagreeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }

    private fun validNickname():Boolean{
        val value: String = viewBinding.signupRealedtNickname?.text.toString().trim()
        val nicknamepattern = "^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]{2,10}$"
        return if(!value.matches(nicknamepattern.toRegex())){
            // viewBinding.signupRealedtNickname.error="닉네임 형식이 잘못되었습니다."
            CustomToast.createToast(applicationContext, "닉네임 형식이 잘못되었습니다")?.show()
            false
        } else{
            viewBinding.signupRealedtNickname.error=null
            true
        }
    }
}