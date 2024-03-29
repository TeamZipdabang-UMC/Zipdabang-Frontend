package com.UMC.zipdabang.config.src.main.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.UMC.zipdabang.databinding.ActivitySignupServiceagreeChoice1Binding

class SignupServiceagreeChoice1Activity: AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignupServiceagreeChoice1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignupServiceagreeChoice1Binding.inflate((layoutInflater))
        setContentView(viewBinding.root)
        viewBinding.signupBackbtn.setOnClickListener {
            val intent = Intent(this, SignupServiceagreeActivity::class.java)
            startActivity(intent)
        }
    }
}