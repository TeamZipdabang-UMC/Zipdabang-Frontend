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
        //이용약관 이동
        viewBinding.ivToTos.setOnClickListener {

            val intent = Intent(this,ActvityTos::class.java)
            startActivity(intent)

        }

        viewBinding.ivToQuestion.setOnClickListener {

            val intent = Intent(this,FirstQuestionActivity::class.java)
            startActivity(intent)

        }

        viewBinding.ivToFAQ.setOnClickListener {

            val intent = Intent(this,MyFAQActivity::class.java)
            startActivity(intent)

        }

        viewBinding.myBackbtn.setOnClickListener {
            finish()
        }
    }
}