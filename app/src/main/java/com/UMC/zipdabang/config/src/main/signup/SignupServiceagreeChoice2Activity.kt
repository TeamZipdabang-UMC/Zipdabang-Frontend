package com.UMC.zipdabang.config.src.main.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.UMC.umc_zipdabang.databinding.ActivitySignupServiceagreeChoice2Binding

class SignupServiceagreeChoice2Activity: AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignupServiceagreeChoice2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignupServiceagreeChoice2Binding.inflate((layoutInflater))
        setContentView(viewBinding.root)

        viewBinding.signupBackbtn.setOnClickListener {
            val intent = Intent(this, SignupServiceagreeActivity::class.java)
            startActivity(intent)
        }
    }
}