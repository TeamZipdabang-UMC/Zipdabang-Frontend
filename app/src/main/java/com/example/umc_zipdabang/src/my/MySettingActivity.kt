package com.example.umc_zipdabang.src.my

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.databinding.ActivityMySettingBinding

class MySettingActivity :AppCompatActivity(){
    private lateinit var viewBinding: ActivityMySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMySettingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.myBackbtn.setOnClickListener {
            finish()
        }
    }
}