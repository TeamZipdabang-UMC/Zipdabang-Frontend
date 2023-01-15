package com.example.umc_zipdabang.config.src.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       //val window = window
       // 상태바 투명하게
      //  window.setFlags(
        //WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        //WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding = ActivityMainBinding.inflate(layoutInflater)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)


        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.main_fl, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fl, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.tab_jip_receipe -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fl, JipFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.tab_our_receipe -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fl, OurFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.tab_user -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fl, UserFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                else -> {
                    false
                }
            }

        }
    }
}