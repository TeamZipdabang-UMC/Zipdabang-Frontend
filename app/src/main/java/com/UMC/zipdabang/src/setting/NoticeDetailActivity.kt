package com.UMC.zipdabang.src.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.UMC.zipdabang.databinding.ActivityMyNoticeDetailBinding

class NoticeDetailActivity  : AppCompatActivity(){
    private lateinit var viewBinding: ActivityMyNoticeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyNoticeDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)


        val intent = getIntent()
        var title= intent.getStringExtra("타이틀")
        var date= intent.getStringExtra("날짜")
        var body = intent.getStringExtra("바디")
        viewBinding.noticeTitle.text=title
        viewBinding.noticeDate.text=date
        viewBinding.noticeContent.text=body

        viewBinding.noticeBack.setOnClickListener {
            finish()
        }







        viewBinding.myBackbtn.setOnClickListener {
            finish()
        }
    }
}