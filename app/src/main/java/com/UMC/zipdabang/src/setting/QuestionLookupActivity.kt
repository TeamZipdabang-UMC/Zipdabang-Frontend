package com.UMC.zipdabang.src.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.UMC.umc_zipdabang.databinding.ActivityMyQuestonLookupBinding

class QuestionLookupActivity :AppCompatActivity(){
    private lateinit var viewBinding: ActivityMyQuestonLookupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyQuestonLookupBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)



        val intent = getIntent()
        var email= intent.getStringExtra("이메일")
        var date= intent.getStringExtra("날짜")
        var body = intent.getStringExtra("바디")
        viewBinding.emailContent.text=email
        viewBinding.dateContent.text=date
        viewBinding.contentContent.text=body

        viewBinding.myBackbtn.setOnClickListener {
            finish()
        }
    }
}