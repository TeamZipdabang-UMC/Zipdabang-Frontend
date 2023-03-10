package com.example.umc_zipdabang.src.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.databinding.ActivitySignupServiceagreeChoice1Binding

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