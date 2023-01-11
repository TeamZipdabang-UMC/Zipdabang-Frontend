package com.example.umc_zipdabang


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.umc_zipdabang.databinding.ActivityLoginBinding

class MainActivity : AppCompatActivity() {

    val least_password=8

    private lateinit var viewbinding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        viewbinding=ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewbinding.root)
        viewbinding.passwordHide.tag="0"


        viewbinding.passwordHide.setOnClickListener {
            //비밀번호 보이게 한다
            if (viewbinding.passwordHide.getTag().equals("0")) {
                viewbinding.passwordHide.setTag("1")
                viewbinding.passwordHide.setImageResource(R.drawable.password_vis)
                viewbinding.etPassword.transformationMethod = null
            } else {
                viewbinding.passwordHide.setTag("0")
                viewbinding.passwordHide.setImageResource(R.drawable.password_unv)
                viewbinding.etPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }     //커서뒤로
            viewbinding.etPassword.setSelection(viewbinding.etPassword.getText().length)
        }



        viewbinding.etEmail.addTextChangedListener{

            if(viewbinding.etEmail.text.isNotEmpty()) {
                viewbinding.etEmail.setTag("1")

                if(viewbinding.etPassword.text.toString().length >= least_password) {
                    viewbinding.btLogin.setBackgroundResource(R.drawable.bg_bt_yellow_login)
                    viewbinding.btLogin.setTextColor(
                        (ContextCompat.getColor(
                            applicationContext,
                            R.color.black
                        ))
                    )
                }

            }else {
                viewbinding.etEmail.setTag("0")
                viewbinding.btLogin.setBackgroundResource(R.drawable.bg_bt_gray_login)
                viewbinding.btLogin.setTextColor((ContextCompat.getColor(applicationContext,R.color.jipdabang_login_edit_gray)))
            }

        }

        viewbinding.etPassword.addTextChangedListener{


            if (viewbinding.etPassword.text.toString().length < least_password) {
                viewbinding.btLogin.setBackgroundResource(R.drawable.bg_bt_gray_login)
                viewbinding.btLogin.setTextColor((ContextCompat.getColor(applicationContext,R.color.jipdabang_login_edit_gray)))

            } else if(viewbinding.etEmail.getTag().equals("1") && viewbinding.etPassword.text.toString().length >= 8) {
                viewbinding.btLogin.setBackgroundResource(R.drawable.bg_bt_yellow_login)
                viewbinding.btLogin.setTextColor((ContextCompat.getColor(applicationContext,R.color.black)))
            }
        }
    }


}


