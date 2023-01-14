package com.example.umc_zipdabang.src.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val button: Button = findViewById(R.id.btn)
        button.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(viewBinding.frameLayout.id, ZipdabangRecipeFragment())
                .commitAllowingStateLoss()
        }

    }


}