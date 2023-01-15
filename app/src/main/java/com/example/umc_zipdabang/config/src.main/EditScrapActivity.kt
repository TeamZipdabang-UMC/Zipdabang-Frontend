package com.example.umc_zipdabang.config.src.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.databinding.ActivityEditscrapBinding

class EditScrapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditscrapBinding

    private var scraps: ArrayList<My_Scrap> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditscrapBinding.inflate(layoutInflater)

        var intent= getIntent()

        scraps=intent.getSerializableExtra("array") as ArrayList<My_Scrap>

        binding.cancelCompleteBt.setOnClickListener()
        {
            val intent= Intent(this,MyScapActivity::class.java)
            startActivity(intent)


        }









        setContentView(binding.root)

    }
}