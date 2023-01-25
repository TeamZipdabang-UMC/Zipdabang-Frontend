package com.example.umc_zipdabang.src.setting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.databinding.ActivityMySettingBinding

class MySettingActivity :AppCompatActivity(){
    private lateinit var viewBinding: ActivityMySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMySettingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)



        //공지사항 이동
        viewBinding.ivToNotice.setOnClickListener {

            val intent = Intent(this,MyNoticeActivity::class.java)
            startActivity(intent)

        }

        viewBinding.myBackbtn.setOnClickListener {
            finish()
        }
    }
}