package com.example.umc_zipdabang.src.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityMainBinding
import com.example.umc_zipdabang.src.my.MyFragment

class MainActivity : AppCompatActivity() {

    lateinit var viewBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

  //           viewBinding.myBtn.setOnClickListener {
 //           supportFragmentManager.beginTransaction()
   //             .replace(R.id.mainfragmentcontainer, MyFragment())
   //             .commitAllowingStateLoss()
   //     }
    }

}