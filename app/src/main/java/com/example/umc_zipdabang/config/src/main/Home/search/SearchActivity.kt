package com.example.umc_zipdabang.config.src.main.Home.search

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_zipdabang.config.src.main.Home.Scrap.Main_Scrap
import com.example.umc_zipdabang.config.src.main.Home.reciepe.Home_receipe
import com.example.umc_zipdabang.databinding.ActivitySearchBinding

class SearchActivity: AppCompatActivity() {


    private lateinit var viewbinding: ActivitySearchBinding

    private var coffee : ArrayList<Main_Scrap> = arrayListOf(
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12),
            Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12),
            Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12)
        )


    private var beverage: ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12)
    )

    private var tea : ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12)
    )
    private var health : ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12)
    )

    private val category : ArrayList<Home_receipe> =  arrayListOf(
        Home_receipe("커피", coffee),
        Home_receipe("beverage",beverage),
        Home_receipe("티",tea),
        Home_receipe("건강음료",health),
    )


    override fun onCreate(savedInstanceState: Bundle?) {


        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        super.onCreate(savedInstanceState)

        viewbinding=ActivitySearchBinding.inflate(layoutInflater)

        viewbinding.searchRv.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        val adapter= SearchAdpater_1(this,category)

        viewbinding.searchRv.adapter=adapter



        var string=intent.getStringExtra("search")

        viewbinding.etSearch.setText(string)

        var renew=intent.getStringExtra("renew")

        if(renew!=null)
        {
            viewbinding.etSearch.setText(renew)


        }





        viewbinding.etSearch.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(view: View?, i: Int, keyEvent: KeyEvent?): Boolean {
                when (i) {
                    KeyEvent.KEYCODE_ENTER -> {
                        val search= Intent(applicationContext,SearchActivity::class.java)
                            .setFlags( FLAG_ACTIVITY_CLEAR_TOP)
                        search.putExtra("renew",viewbinding.etSearch.text.toString())
                        startActivity(search)
                    }
                }
                return false
            }
        })




        viewbinding.myscrapIvBack.setOnClickListener{
            onBackPressed()
        }





        setContentView(viewbinding.root)








    }


}