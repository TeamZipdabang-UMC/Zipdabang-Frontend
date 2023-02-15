package com.example.umc_zipdabang.config.src.main.signup

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.proto.ProtoOutputStream
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.config.src.main.Home.HomeMainActivity
import com.example.umc_zipdabang.databinding.ActivitySignupResearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupResearchActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignupResearchBinding
    val api = APIS.create()

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding= ActivitySignupResearchBinding.inflate((layoutInflater))
        setContentView(viewBinding.root)

        val sharedPreference = getSharedPreferences("signup",0)
        val editor = sharedPreference.edit()

        var coffee = viewBinding.signupBtnCoffee
        var coffee_check = viewBinding.signupCheckbtnCoffee
        var noncoffee = viewBinding.signupBtnNoncoffee
        var noncoffee_check = viewBinding.signupCheckbtnNoncoffee
        var tea = viewBinding.signupBtnTea
        var tea_check = viewBinding.signupCheckbtnTea
        var ade = viewBinding.signupBtnAde
        var ade_check = viewBinding.signupCheckbtnAde
        var smudi = viewBinding.signupBtnSmudi
        var smudi_check = viewBinding.signupCheckbtnSmudi
        var juice = viewBinding.signupBtnJuice
        var juice_check = viewBinding.signupCheckbtnJuice
        var juice_background = viewBinding.signupBtnJuice.background
        var health = viewBinding.signupBtnHealth
        var health_check = viewBinding.signupCheckbtnHealth
        var health_background = viewBinding.signupBtnHealth.background
        var etc = viewBinding.signupBtnEtc
        var etc_check = viewBinding.signupCheckbtnEtc
        var nextBtn = viewBinding.signupNextbtn
        var passBtn = viewBinding.signupPassbtn

        coffee.setOnClickListener{
            coffee.isSelected = !(coffee.isSelected == true)
            if(coffee.isSelected){
                coffee.setColorFilter(Color.parseColor("#999999"), PorterDuff.Mode.MULTIPLY)
                coffee_check.setVisibility(View.VISIBLE)

                nextBtn.setEnabled(true)
                nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                nextBtn.setText("확인")

                passBtn.setVisibility(View.INVISIBLE)
            }
            else{
                coffee.setColorFilter(Color.parseColor("#BDBDBD"),PorterDuff.Mode.DST)
                coffee_check.setVisibility(View.INVISIBLE)

                if(noncoffee.isSelected ==false && ade.isSelected ==false && smudi.isSelected==false&& juice.isSelected==false&&
                    health.isSelected==false&& etc.isSelected==false && tea.isSelected == false){
                    nextBtn.setEnabled(false)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
                    nextBtn.setText("다음")

                    passBtn.setVisibility(View.VISIBLE)
                }
                else{
                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                    nextBtn.setText("확인")

                    passBtn.setVisibility(View.INVISIBLE)
                }
            }
        }
        noncoffee.setOnClickListener{
            noncoffee.isSelected = !(noncoffee.isSelected == true)
            if(noncoffee.isSelected){
                noncoffee.setColorFilter(Color.parseColor("#999999"), PorterDuff.Mode.MULTIPLY)
                noncoffee_check.setVisibility(View.VISIBLE)

                nextBtn.setEnabled(true)
                nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                nextBtn.setText("확인")

                passBtn.setVisibility(View.INVISIBLE)
            }
            else{
                noncoffee.setColorFilter(Color.parseColor("#BDBDBD"),PorterDuff.Mode.DST)
                noncoffee_check.setVisibility(View.INVISIBLE)

                if(coffee.isSelected ==false && ade.isSelected==false&& smudi.isSelected==false&& juice.isSelected==false&&
                    health.isSelected==false&& etc.isSelected==false&& tea.isSelected == false){
                    nextBtn.setEnabled(false)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
                    nextBtn.setText("다음")

                    passBtn.setVisibility(View.VISIBLE)
                }
                else{
                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                    nextBtn.setText("확인")

                    passBtn.setVisibility(View.INVISIBLE)
                }
            }
        }
        tea.setOnClickListener{
            tea.isSelected = !(tea.isSelected == true)
            if(tea.isSelected){
                tea.setColorFilter(Color.parseColor("#999999"), PorterDuff.Mode.MULTIPLY)
                tea_check.setVisibility(View.VISIBLE)

                nextBtn.setEnabled(true)
                nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                nextBtn.setText("확인")

                passBtn.setVisibility(View.INVISIBLE)
            }
            else{
                tea.setColorFilter(Color.parseColor("#BDBDBD"),PorterDuff.Mode.DST)
                tea_check.setVisibility(View.INVISIBLE)

                if(noncoffee.isSelected==false && ade.isSelected==false&& smudi.isSelected==false&& juice.isSelected==false&&
                    health.isSelected==false&& etc.isSelected==false&& coffee.isSelected == false){
                    nextBtn.setEnabled(false)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
                    nextBtn.setText("다음")

                    passBtn.setVisibility(View.VISIBLE)
                }
                else{
                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                    nextBtn.setText("확인")

                    passBtn.setVisibility(View.INVISIBLE)
                }
            }
        }
        ade.setOnClickListener{
            ade.isSelected = !(ade.isSelected == true)
            if(ade.isSelected){
                ade.setColorFilter(Color.parseColor("#999999"), PorterDuff.Mode.MULTIPLY)
                ade_check.setVisibility(View.VISIBLE)

                nextBtn.setEnabled(true)
                nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                nextBtn.setText("확인")

                passBtn.setVisibility(View.INVISIBLE)
            }
            else{
                ade.setColorFilter(Color.parseColor("#BDBDBD"),PorterDuff.Mode.DST)
                ade_check.setVisibility(View.INVISIBLE)

                if(noncoffee.isSelected==false && coffee.isSelected==false&& smudi.isSelected==false&& juice.isSelected==false&&
                    health.isSelected==false&& etc.isSelected==false&& tea.isSelected == false){
                    nextBtn.setEnabled(false)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
                    nextBtn.setText("다음")

                    passBtn.setVisibility(View.VISIBLE)
                }
                else{
                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                    nextBtn.setText("확인")

                    passBtn.setVisibility(View.INVISIBLE)
                }
            }
        }
        smudi.setOnClickListener{
            smudi.isSelected = !(smudi.isSelected == true)
            if(smudi.isSelected){
                smudi.setColorFilter(Color.parseColor("#999999"), PorterDuff.Mode.MULTIPLY)
                smudi_check.setVisibility(View.VISIBLE)

                nextBtn.setEnabled(true)
                nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                nextBtn.setText("확인")

                passBtn.setVisibility(View.INVISIBLE)
            }
            else{
                smudi.setColorFilter(Color.parseColor("#BDBDBD"),PorterDuff.Mode.DST)
                smudi_check.setVisibility(View.INVISIBLE)

                if(noncoffee.isSelected==false && ade.isSelected==false&& coffee.isSelected==false&& juice.isSelected==false&&
                    health.isSelected==false&& etc.isSelected==false&& tea.isSelected == false){
                    nextBtn.setEnabled(false)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
                    nextBtn.setText("다음")

                    passBtn.setVisibility(View.VISIBLE)
                }
                else{
                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                    nextBtn.setText("확인")

                    passBtn.setVisibility(View.INVISIBLE)
                }
            }
        }
        juice.setOnClickListener{
            juice.isSelected = !(juice.isSelected == true)
            if(juice.isSelected){
                juice.setColorFilter(Color.parseColor("#999999"), PorterDuff.Mode.MULTIPLY)
                juice_background.setColorFilter(Color.parseColor("#999999"), PorterDuff.Mode.MULTIPLY)
                juice_check.setVisibility(View.VISIBLE)

                nextBtn.setEnabled(true)
                nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                nextBtn.setText("확인")

                passBtn.setVisibility(View.INVISIBLE)
            }
            else{
                juice.setColorFilter(Color.parseColor("#FFFFFF"),PorterDuff.Mode.DST)
                juice_background.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.DST)
                juice_check.setVisibility(View.INVISIBLE)

                if(noncoffee.isSelected ==false&& ade.isSelected==false&& smudi.isSelected==false&& coffee.isSelected==false&&
                    health.isSelected==false&& etc.isSelected==false&& tea.isSelected == false){
                    nextBtn.setEnabled(false)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
                    nextBtn.setText("다음")

                    passBtn.setVisibility(View.VISIBLE)
                }
                else{
                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                    nextBtn.setText("확인")

                    passBtn.setVisibility(View.INVISIBLE)
                }
            }
        }
        health.setOnClickListener{
            health.isSelected = !(health.isSelected == true)
            if(health.isSelected){
                health.setColorFilter(Color.parseColor("#999999"), PorterDuff.Mode.MULTIPLY)
                health_background.setColorFilter(Color.parseColor("#999999"), PorterDuff.Mode.MULTIPLY)
                health_check.setVisibility(View.VISIBLE)

                nextBtn.setEnabled(true)
                nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                nextBtn.setText("확인")

                passBtn.setVisibility(View.INVISIBLE)
            }
            else{
                health.setColorFilter(Color.parseColor("#FFFFFF"),PorterDuff.Mode.DST)
                health_background.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.DST)
                health_check.setVisibility(View.INVISIBLE)

                if(noncoffee.isSelected==false && ade.isSelected==false&& smudi.isSelected==false&& juice.isSelected==false&&
                    coffee.isSelected==false&& etc.isSelected==false&& tea.isSelected == false){
                    nextBtn.setEnabled(false)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
                    nextBtn.setText("다음")

                    passBtn.setVisibility(View.VISIBLE)
                }
                else{
                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                    nextBtn.setText("확인")

                    passBtn.setVisibility(View.INVISIBLE)
                }
            }
        }
        etc.setOnClickListener{
            etc.isSelected = !(etc.isSelected == true)
            if(etc.isSelected){
                etc.setColorFilter(Color.parseColor("#999999"), PorterDuff.Mode.MULTIPLY)
                etc_check.setVisibility(View.VISIBLE)

                nextBtn.setEnabled(true)
                nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                nextBtn.setText("확인")

                passBtn.setVisibility(View.INVISIBLE)
            }
            else{
                etc.setColorFilter(Color.parseColor("#BDBDBD"),PorterDuff.Mode.DST)
                etc_check.setVisibility(View.INVISIBLE)

                if(noncoffee.isSelected==false && ade.isSelected==false&& smudi.isSelected==false&& juice.isSelected==false&&
                    health.isSelected==false&& coffee.isSelected==false&& tea.isSelected == false){
                    nextBtn.setEnabled(false)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_sign_text_gray)))
                    nextBtn.setText("다음")

                    passBtn.setVisibility(View.VISIBLE)
                }
                else{
                    nextBtn.setEnabled(true)
                    nextBtn.setBackgroundResource(R.drawable.sign_btn_round_yellow)
                    nextBtn.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_black)))
                    nextBtn.setText("확인")

                    passBtn.setVisibility(View.INVISIBLE)
                }
            }
        }

        passBtn.setOnClickListener {
            editor.clear()
            editor.apply()
            val intent = Intent(this, HomeMainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        nextBtn.setOnClickListener {
            var name_sp =sharedPreference.getString("name","")
            var nickname_sp =sharedPreference.getString("nickname","")
            var phonenumber_sp =sharedPreference.getString("phonenumber","")
            var email_sp =sharedPreference.getString("email","")
            var birthday_sp =sharedPreference.getString("birthday","")

            Log.d("통신",name_sp+ nickname_sp+ phonenumber_sp+ birthday_sp+ email_sp)
            val data = PostNewuserBody(name_sp, nickname_sp, phonenumber_sp, birthday_sp, email_sp)
            api.post_signup_newuser(data).enqueue(object: Callback<PostNewuserBodyResponse>{
                override fun onResponse(call: Call<PostNewuserBodyResponse>, response: Response<PostNewuserBodyResponse>) {
                    if(response.body()?.success == true){
                        Log.d("통신", "통신 success "+response.body()?.user)
                    }else{
                        Log.d("통신", "통신 success"+response.body()?.success)
                    }
                }
                override fun onFailure(call: Call<PostNewuserBodyResponse>, t: Throwable) {
                    Log.d("통신","통신 failㅠㅠ")
                }
            })

            /*

            api.get_signup_existnickname(nickname_sp).enqueue(object: Callback<GetNicknameExistResponse>{
                override fun onResponse(call: Call<GetNicknameExistResponse>, response: Response<GetNicknameExistResponse>) {
                    if(response.isSuccessful){
                        Log.d("통신","success "+response.body()?.exist.toString()+response.body()?.nickname.toString())
                        //nickname 중복 ㅠㅠ
                    }else{
                        Log.d("통신","fail "+response.body()?.exist.toString()+response.body()?.nickname.toString())
                    }
                }
                override fun onFailure(call: Call<GetNicknameExistResponse>, t: Throwable) {
                    Log.d("통신","통신 failㅠㅠ")
                }
            })
             */

            editor.clear()
            editor.apply()

            val intent = Intent(this, HomeMainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        viewBinding.signupBackbtn.setOnClickListener{
            val intent = Intent(this, SignupSecondActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }
}

