package com.example.umc_zipdabang.src.setting

import android.content.Intent
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


        binding.layout01.setOnClickListener {
            if(binding.layoutDetail01.visibility == View.VISIBLE) {
                binding.layoutDetail01.visibility = View.GONE
                binding.layoutBtn01.animate().apply {
                    duration = 300
                    rotation(0f)
                }
            } else {
                binding.layoutDetail01.visibility = View.VISIBLE
                binding.layoutBtn01.animate().apply {
                    duration = 300
                    rotation(180f)
                }
            }
        }

        binding.layout02.setOnClickListener {
            if(binding.layoutDetail02.visibility == View.VISIBLE) {
                binding.layoutDetail02.visibility = View.GONE
                binding.layoutBtn02.animate().apply {
                    duration = 300
                    rotation(0f)
                }
            } else {
                binding.layoutDetail02.visibility = View.VISIBLE
                binding.layoutBtn02.animate().apply {
                    duration = 300
                    rotation(180f)
                }
            }
        }

        binding.layout03.setOnClickListener {
            if(binding.layoutDetail03.visibility == View.VISIBLE) {
                binding.layoutDetail03.visibility = View.GONE
                binding.layoutBtn03.animate().apply {
                    duration = 300
                    rotation(0f)
                }
            } else {
                binding.layoutDetail03.visibility = View.VISIBLE
                binding.layoutBtn03.animate().apply {
                    duration = 300
                    rotation(180f)
                }
            }
        }

        binding.layout04.setOnClickListener {
            if(binding.layoutDetail04.visibility == View.VISIBLE) {
                binding.layoutDetail04.visibility = View.GONE
                binding.layoutBtn04.animate().apply {
                    duration = 300
                    rotation(0f)
                }
            } else {
                binding.layoutDetail04.visibility = View.VISIBLE
                binding.layoutBtn04.animate().apply {
                    duration = 300
                    rotation(180f)
                }
            }
        }

    }
}