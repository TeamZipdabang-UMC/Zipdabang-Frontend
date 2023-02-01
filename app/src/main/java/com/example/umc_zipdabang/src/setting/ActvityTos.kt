package com.example.umc_zipdabang.src.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.databinding.ActivityMySettingBinding
import com.example.umc_zipdabang.databinding.ActivityMyTosBinding

class ActvityTos: AppCompatActivity(){
    private lateinit var binding: ActivityMyTosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMyTosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.myBackbtn.setOnClickListener {
            onBackPressed()
        }



    }
}