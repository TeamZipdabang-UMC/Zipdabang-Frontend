package com.UMC.zipdabang.src.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.UMC.zipdabang.databinding.ActivityMyTosBinding

class ActvityTos: AppCompatActivity(){
    private lateinit var binding: ActivityMyTosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMyTosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.myBackbtn.setOnClickListener {
            onBackPressed()
        }

        binding.layoutBtn01.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW, Uri.parse("https://app.catchsecu.com/document/P/33b1b25663ff8a3"))
            startActivity(intent)


        }
        binding.layoutBtn02.setOnClickListener {
            val intent2= Intent(Intent.ACTION_VIEW, Uri.parse("https://www.notion.so/82b6e4652ec040f49d77cf99839c1bcc"))
            startActivity(intent2)

        }



    }
}