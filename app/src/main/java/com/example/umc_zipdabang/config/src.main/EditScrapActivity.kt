package com.example.umc_zipdabang.config.src.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.databinding.ActivityEditscrapBinding


class EditScrapActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityEditscrapBinding
    private var scraps: ArrayList<My_Scrap> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityEditscrapBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var intent= getIntent()

        scraps=intent.getSerializableExtra("array") as ArrayList<My_Scrap>

        binding.cancelCompleteBt.setOnClickListener()
        {
            val intent= Intent(this,MyScapActivity::class.java)
            startActivity(intent)


        }
        val adapter = EditScrapAdapter(this,scraps)
        binding.myscrapEditRv.layoutManager =
           GridLayoutManager(this, 2)
        binding.myscrapEditRv.adapter=adapter



       adapter.setOnItemClickListener(object: EditScrapAdapter.OnItemClickListener{
           override fun onItemClick(v: View?, pos: Int) {
           binding.button.text="바뀜"
           }
    })






    }

}