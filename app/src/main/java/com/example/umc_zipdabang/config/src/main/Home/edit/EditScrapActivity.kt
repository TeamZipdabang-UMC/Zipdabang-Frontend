package com.example.umc_zipdabang.config.src.main.Home.edit

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.config.src.main.Home.CustomDialogDialog
import com.example.umc_zipdabang.config.src.main.Home.Scrap.MyScapActivity
import com.example.umc_zipdabang.config.src.main.Home.Scrap.My_Scrap
import com.example.umc_zipdabang.config.src.main.Home.custom_toast
import com.example.umc_zipdabang.databinding.ActivityEditscrapBinding


class EditScrapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditscrapBinding
    private var scraps: ArrayList<My_Scrap> = arrayListOf()

    private var deletelist: ArrayList<My_Scrap> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityEditscrapBinding.inflate(layoutInflater)

        setContentView(binding.root)


        var intent= getIntent()

        scraps = intent.getSerializableExtra("array") as ArrayList<My_Scrap>


        binding.cancelCompleteBt.setOnClickListener()
        {
            val intent = Intent(this, MyScapActivity::class.java)
                .setFlags(FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }

        val adapter = EditScrapAdapter(this, scraps)
        binding.myscrapEditRv.layoutManager =
            GridLayoutManager(this, 2)
        binding.myscrapEditRv.adapter = adapter




        adapter.setOnItemClickListener(object : EditScrapAdapter.OnItemClickListener {
            override fun onItemClick(v: View?, pos: Int, tag: String) {
                if (tag == "1") {
                    deletelist.add(scraps[pos])
                } else deletelist.remove(scraps[pos])

                if (deletelist.size == 0) {
                    binding.button.setBackgroundResource(R.drawable.bg_home_gray_delete)
                    binding.selectTv.text="레시피 선택"
                    binding.button.setTextColor(
                        (ContextCompat.getColor(
                            applicationContext,
                            R.color.jipdabang_login_edit_gray
                        ))
                    )

                } else {
                    binding.selectTv.text= deletelist.size.toString() + "개의 레시피가 선택됨"
                    binding.button.setBackgroundResource(R.drawable.bg_home_yellow_delete)
                    binding.button.setTextColor(
                        (ContextCompat.getColor(
                            applicationContext,
                            R.color.jipdabaing_black
                        ))
                    )

                }

            }

        })


        val dialog = CustomDialogDialog(this)
        binding.button.setOnClickListener {
            if (deletelist.isNotEmpty()) {

                dialog.showDialog()

            }


        }
        dialog.setOnClickListener(object : CustomDialogDialog.ButtonClickListener {
            override fun onClicked() {
                val a = deletelist.size
                for (i: Int in 0..a - 1) {
                    scraps.remove(deletelist[i])
                    adapter.notifyDataSetChanged()
                }

                custom_toast.createToast(applicationContext, "My 레시피에서 삭제되었어요")?.show()
                deletelist.clear()
                binding.selectTv.text="레시피 선택"
                binding.button.setBackgroundResource(R.drawable.bg_home_gray_delete)
                binding.button.setTextColor((ContextCompat.getColor(applicationContext, R.color.jipdabang_login_edit_gray)))
                binding.cancelCompleteTv.text="완료"

            }



        })





}


}
