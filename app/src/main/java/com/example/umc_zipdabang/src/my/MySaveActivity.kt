package com.example.umc_zipdabang.src.my

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_zipdabang.databinding.ActivityMySaveBinding

class MySaveActivity:AppCompatActivity() {
    private lateinit var viewBinding: ActivityMySaveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMySaveBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val sharedPreference = getSharedPreferences("signup",0)
        val editor = sharedPreference.edit()

        var title_sp = sharedPreference.getString("title","")
        var time_sp = sharedPreference.getString("time","")
        var describe_sp = sharedPreference.getString("describe","")
        var aftertip_sp = sharedPreference.getString("aftertip","")

        if(title_sp != null || time_sp != null || describe_sp !=null || aftertip_sp != null){
            viewBinding.myRecipeEdtTital.setText(sharedPreference.getString("title_sp",""))
        }



        viewBinding.myBackbtn.setOnClickListener {
            finish()
        }
    }
}