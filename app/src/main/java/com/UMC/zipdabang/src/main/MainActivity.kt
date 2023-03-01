package com.UMC.zipdabang.src.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.UMC.umc_zipdabang.databinding.ActivityMainBinding

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