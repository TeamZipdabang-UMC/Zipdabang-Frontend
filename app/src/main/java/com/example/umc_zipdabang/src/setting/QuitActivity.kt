package com.example.umc_zipdabang.src.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityMyEditQuestionBinding
import com.example.umc_zipdabang.databinding.ActivityMyQuitBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuitActivity  :  AppCompatActivity(){
    private lateinit var viewbinding: ActivityMyQuitBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        viewbinding = ActivityMyQuitBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewbinding.root)

        var agree : Boolean = false


        viewbinding.agreeBt.setOnClickListener {
                if(!agree){

                    viewbinding.agreeBt.setBackgroundResource(R.drawable.sign_roundbtn_yellow)
                    agree=true
                }
                else{
                    viewbinding.agreeBt.setBackgroundResource(R.drawable.sign_roundbtn_gray)
                    agree=false

                }
            }

            val dialog =  Dialog_quit(this)
            viewbinding.btQuestion.setOnClickListener {
                if(agree){
                    dialog.showDialog()
                    }

            var token1  =
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsQG5hdmVyLmNvbSIsImlhdCI6MTY3NDYyNDA5OCwiZXhwIjoxNjc3MjE2MDk4LCJzdWIiOiJ1c2VySW5mbyJ9.ZEl388-pGKg02xaVO5fq3nVGBtn0QfgTiWEeX3laRl0"
            val service = Retrofit.retrofit.create(RetrofitQuitService::class.java)



            dialog.setOnClickListener(object : Dialog_quit.ButtonClickListener {
                override fun onClicked() {

                    service.quit(token1).enqueue(object :
                        Callback<QUit_Respsonse> {
                        override fun onResponse(
                            call: Call<QUit_Respsonse>,
                            response: Response<QUit_Respsonse>
                        ) {
                            val result = response.body()
                            Log.d("성공", "${result}")

                            if(result?.success==true)
                            {
                                //첫화면으로 이동  val intent =
                                //     Intent(applicationContext, FirstQuestionActivity::class.java)
                                // startActivity(intent)


                            }




                        }

                        override fun onFailure(call: Call<QUit_Respsonse>, t: Throwable) {
                            Log.d("실패","실패2")
                        }


                        //백엔드에 문의 내용 전달

                    })


                }

            })

        viewbinding.myBackbtn.setOnClickListener {
            finish()


    }
        }
    }
}
