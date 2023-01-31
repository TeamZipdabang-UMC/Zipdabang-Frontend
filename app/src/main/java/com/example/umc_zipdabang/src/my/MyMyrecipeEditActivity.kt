package com.example.umc_zipdabang.src.my

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.ActivityMyMyrecipeEditBinding
import com.example.umc_zipdabang.databinding.ToastMyDeleteBinding
import com.example.umc_zipdabang.src.my.data.ItemRecipeChallengeData
import com.example.umc_zipdabang.src.my.data.ItemRecipeData
import com.example.umc_zipdabang.src.my.data.MyMyrecipeEditRVAdapter
import com.example.umc_zipdabang.src.my.etc.DeleteDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class MyMyrecipeEditActivity: AppCompatActivity() {
    private lateinit var viewBinding: ActivityMyMyrecipeEditBinding
    private lateinit var binding_toast_my_delete : ToastMyDeleteBinding

    private var scraps: ArrayList<ItemRecipeChallengeData> = arrayListOf()
    private var delete: ArrayList<ItemRecipeChallengeData> = arrayListOf()
    var token1: String? = null
    private val retrofit = RetrofitInstance.getInstance().create(APIS_My::class.java)
    private var deletelist: ArrayList<Int> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyMyrecipeEditBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.myCancelbtn.setOnClickListener {
            onBackPressed()
        }
        viewBinding.myToolbar.bringToFront()


        GlobalScope.launch(Dispatchers.IO) {


            //val tokenDb = TokenDatabase.getTokenDatabase(this)
            //   token1 = tokenDb.tokenDao().getToken().token.toString()
            token1 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsMUBnbWFpbC5jb20iLCJpYXQiOjE2NzUwMDc2ODUsImV4cCI6MTY3NzU5OTY4NSwic3ViIjoidXNlckluZm8ifQ.38w5k86aZsM1qiRu2EGjN7wB2C4AMNluX_UAV1NcxGY"


            var intent = getIntent()
            scraps = intent.getSerializableExtra("array") as ArrayList<ItemRecipeChallengeData>
            Log.d("확인","${scraps}")

            val adapter = MyMyrecipeEditRVAdapter(this@MyMyrecipeEditActivity, scraps)
            viewBinding.myRv.layoutManager = GridLayoutManager(this@MyMyrecipeEditActivity, 2)
            viewBinding.myRv.adapter = adapter
            adapter.setOnItemClickListener(object : MyMyrecipeEditRVAdapter.OnItemClickListener {
                override fun onItemClick(v: View?, pos: Int, tag: String) {
                    if (tag == "1") {
                        scraps[pos].userId?.let { deletelist.add(it) }
                        delete.add(scraps[pos])
                    } else {
                        deletelist.remove(scraps[pos].userId)
                        delete.remove(scraps[pos])
                    }


                    if (deletelist.size == 0) {
                        viewBinding.myItemdeletebtn.setBackgroundResource(R.drawable.my_btn_round)
                        viewBinding.myTv.text = "레시피 선택"
                        viewBinding.myCancelbtn.setTextColor(
                            (ContextCompat.getColor(
                                applicationContext,
                                R.color.jipdabang_login_edit_gray
                            ))
                        )
                        viewBinding.myCancelbtn.setTextColor(
                            (ContextCompat.getColor(
                                applicationContext,
                                R.color.jipdabang_black
                            ))
                        )

                    } else {
                        viewBinding.myTv.text = deletelist.size.toString() + "개의 레시피가 선택됨"
                        viewBinding.myItemdeletebtn.setBackgroundResource(R.drawable.my_btn_round_yellow)
                        viewBinding.myItemdeletebtn.setTextColor(
                            (ContextCompat.getColor(
                                applicationContext,
                                R.color.jipdabang_black
                            ))
                        )
                        viewBinding.myCancelbtn.setTextColor(
                            (ContextCompat.getColor(
                                applicationContext,
                                R.color.jipdabang_black
                            ))
                        )

                    }

                }

            })


            val handler : Handler = Handler(Looper.getMainLooper())
            handler.postDelayed(object: Runnable {
                override fun run() {

                    val dialog = DeleteDialog(this@MyMyrecipeEditActivity)
                    //삭제하기 버튼
                    viewBinding.myItemdeletebtn.setOnClickListener {
                        if (deletelist.isNotEmpty()) {

                            dialog.showDialog()
                            dialog.setOnClickListener(object :
                                DeleteDialog.ButtonClickListener {
                                override fun onClicked() {

                                    var deletearray = deletelist.toTypedArray()
                                    val json = Delete(deletearray)


                                    //통신
                                    retrofit.deleteScrap(token1,json)?.enqueue(object :
                                        Callback<Scrap_Delete_Response> {

                                        override fun onResponse(
                                            call: Call<Scrap_Delete_Response>,
                                            response: Response<Scrap_Delete_Response>

                                        ) {
                                            // 정상적으로 통신이 성공된 경우
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

                                    CustomToast.createToast(applicationContext, "내가 쓴 레시피를 삭제하였어요")?.show()

                                    deletelist.clear()
                                    delete.clear()
                                    viewBinding.myTv.text = "레시피 선택"
                                    viewBinding.myItemdeletebtn.setBackgroundResource(R.drawable.my_btn_round)
                                    viewBinding.myItemdeletebtn.setTextColor(
                                        (ContextCompat.getColor(
                                            applicationContext,
                                            R.color.jipdabang_login_edit_gray
                                        ))
                                    )
                                    viewBinding.myCancelbtn.text = "완료"

                                }

                            })

                        }
                    }


                }
            },0)
        }

    }

}

