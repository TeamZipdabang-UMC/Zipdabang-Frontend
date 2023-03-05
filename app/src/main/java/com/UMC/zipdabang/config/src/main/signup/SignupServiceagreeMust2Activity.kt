package com.UMC.zipdabang.config.src.main.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.databinding.ActivitySignupServiceagreeMust2Binding

class SignupServiceagreeMust2Activity: AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignupServiceagreeMust2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignupServiceagreeMust2Binding.inflate((layoutInflater))
        setContentView(viewBinding.root)

        viewBinding.signupBackbtn.setOnClickListener {
            val intent = Intent(this, SignupServiceagreeActivity::class.java)
            startActivity(intent)
        }
    }
}