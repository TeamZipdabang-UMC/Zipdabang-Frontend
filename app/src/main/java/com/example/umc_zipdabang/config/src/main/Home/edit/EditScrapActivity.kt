package com.example.umc_zipdabang.config.src.main.Home.edit

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.config.src.main.Home.CustomDialogDialog
import com.example.umc_zipdabang.config.src.main.Home.Scrap.MyScapActivity
import com.example.umc_zipdabang.config.src.main.Home.Scrap.MyScrapAdapter
import com.example.umc_zipdabang.config.src.main.Home.Scrap.My_Scrapp
import com.example.umc_zipdabang.config.src.main.Home.custom_toast
import com.example.umc_zipdabang.config.src.main.Retrofit.Delete
import com.example.umc_zipdabang.config.src.main.Retrofit.RetrofitMainService
import com.example.umc_zipdabang.config.src.main.Retrofit.Scrap_Delete_Response
import com.example.umc_zipdabang.config.src.main.Retrofit.Scrap_Response
import com.example.umc_zipdabang.databinding.ActivityEditscrapBinding
import com.example.umc_zipdabang.src.main.roomDb.TokenDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditScrapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditscrapBinding
    private var scraps: ArrayList<My_Scrapp> = arrayListOf()
    private var delete: ArrayList<My_Scrapp> = arrayListOf()
    var token1: String? = null
    val service = com.example.umc_zipdabang.config.src.main.Retrofit.Retrofit.retrofit.create(
        RetrofitMainService::class.java
    )
    private var deletelist: ArrayList<Int> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityEditscrapBinding.inflate(layoutInflater)

        setContentView(binding.root)



        GlobalScope.launch(Dispatchers.IO) {


            val tokenDb = TokenDatabase.getTokenDatabase(this@EditScrapActivity)
            //   token1 = tokenDb.tokenDao().getToken().token.toString()

            token1 =
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsQG5hdmVyLmNvbSIsImlhdCI6MTY3NDYyNDA5OCwiZXhwIjoxNjc3MjE2MDk4LCJzdWIiOiJ1c2VySW5mbyJ9.ZEl388-pGKg02xaVO5fq3nVGBtn0QfgTiWEeX3laRl0"


            var intent = getIntent()
            scraps = intent.getSerializableExtra("array") as ArrayList<My_Scrapp>

            val adapter = EditScrapAdapter(this@EditScrapActivity, scraps)
            binding.myscrapEditRv.layoutManager =
                GridLayoutManager(this@EditScrapActivity, 2)
            binding.myscrapEditRv.adapter = adapter
            adapter.setOnItemClickListener(object : EditScrapAdapter.OnItemClickListener {
                override fun onItemClick(v: View?, pos: Int, tag: String) {
                    if (tag == "1") {
                        scraps[pos].id?.let { deletelist.add(it) }
                        delete.add(scraps[pos])
                    } else {
                        deletelist.remove(scraps[pos].id)
                        delete.remove(scraps[pos])
                    }


                    if (deletelist.size == 0) {
                        binding.button.setBackgroundResource(R.drawable.bg_home_gray_delete)
                        binding.selectTv.text = "????????? ??????"
                        binding.button.setTextColor(
                            (ContextCompat.getColor(
                                applicationContext,
                                R.color.jipdabang_login_edit_gray
                            ))
                        )

                    } else {
                        binding.selectTv.text = deletelist.size.toString() + "?????? ???????????? ?????????"
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


            val handler : Handler = Handler(Looper.getMainLooper())
               handler.postDelayed(object: Runnable {
                   override fun run() {


                       val dialog = CustomDialogDialog(this@EditScrapActivity)
                       //???????????? ??????
                       binding.button.setOnClickListener {
                           if (deletelist.isNotEmpty()) {

                               dialog.showDialog()
                               dialog.setOnClickListener(object :
                                   CustomDialogDialog.ButtonClickListener {
                                   override fun onClicked() {


                                       var deletearray = deletelist.toTypedArray()
                                       val json = Delete(deletearray)


                                       //??????
                                       service.deleteScrap(token1,json)?.enqueue(object :
                                           Callback<Scrap_Delete_Response> {

                                           override fun onResponse(
                                               call: Call<Scrap_Delete_Response>,
                                               response: Response<Scrap_Delete_Response>

                                           ) {
                                               // ??????????????? ????????? ????????? ??????
                                               val result = response.body()


                                           }


                                           override fun onFailure(
                                               call: Call<Scrap_Delete_Response>,
                                               t: Throwable
                                           ) {
                                           }
                                       })

                                       var a=delete.size
                                       for(i: Int in 0..a-1){
                                           scraps.remove(delete[i])
                                       }
                                       adapter.notifyDataSetChanged()

                                       custom_toast.createToast(applicationContext, "My ??????????????? ??????????????????")?.show()


                                       deletelist.clear()
                                       delete.clear()
                                       binding.selectTv.text = "????????? ??????"
                                       binding.button.setBackgroundResource(R.drawable.bg_home_gray_delete)
                                       binding.button.setTextColor(
                                           (ContextCompat.getColor(
                                               applicationContext,
                                               R.color.jipdabang_login_edit_gray
                                           ))
                                       )
                                       binding.cancelCompleteTv.text = "??????"

                                   }






                                   })


                               }
                           }


                   }
                   },0)
        }







            binding.cancelCompleteBt.setOnClickListener()
            {
                val intent = Intent(applicationContext, MyScapActivity::class.java)
                    .setFlags(FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)

            }
        }


    }




