package com.UMC.umc_zipdabang.config.src.main.Jip.src.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.UMC.umc_zipdabang.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

//        val button: Button = findViewById(R.id.btn)
//        button.setOnClickListener {
//            supportFragmentManager
//                .beginTransaction()
//                .replace(viewBinding.frameLayout.id, ZipdabangRecipeFragment())
//                .commitAllowingStateLoss()
//        }


    }


}