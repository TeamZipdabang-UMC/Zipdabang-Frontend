package com.example.umc_zipdabang.src.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.Layer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.umc_zipdabang.databinding.ActivityMyNoticeBinding
import com.example.umc_zipdabang.databinding.ActivityMySettingBinding

class MyNoticeActivity : AppCompatActivity(){
    private lateinit var viewBinding: ActivityMyNoticeBinding


    var noticelist : ArrayList<Notice> = arrayListOf(
         Notice("공지사항1", "2023년 1월 25일"),
         Notice("공지사항2", "2023년 1월 28일"),
        Notice("공지사항3", "2023년 1월 29일"),
        Notice("공지사항4", "2023년 1월 30일"),
        Notice("공지사항5", "2023년 2월 28일"))

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyNoticeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

         viewBinding.noticeRv.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        val adapter= NoticeAdapter(this as MyNoticeActivity, noticelist)
        viewBinding.noticeRv.adapter=adapter



        viewBinding.myBackbtn.setOnClickListener {
            finish()
        }
    }
}