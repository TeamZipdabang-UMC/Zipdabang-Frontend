package com.example.umc_zipdabang.src.my

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.config.src.main.Home.HomeMainActivity
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.example.umc_zipdabang.databinding.ActivityMyChallengingBinding
import com.example.umc_zipdabang.src.my.data.ItemRecipeChallengeData
import com.example.umc_zipdabang.src.my.data.MyChallengingRVAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyChallengingActivity: AppCompatActivity() {

    private lateinit var viewBinding: ActivityMyChallengingBinding
    private val retrofit = RetrofitInstance.getInstance().create(APIS_My::class.java)
    var token: String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyChallengingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)


        viewBinding.myBackbtn.setOnClickListener{
            finish()
        }

        val challengingItemList: ArrayList<ItemRecipeChallengeData> = arrayListOf()
        val challengingRVAdapter = MyChallengingRVAdapter(MyChallengingActivity(),challengingItemList)

        GlobalScope.launch(Dispatchers.IO) {

            val tokenDb = TokenDatabase.getTokenDatabase(this@MyChallengingActivity)
            token = tokenDb.tokenDao().getToken().token.toString()

            //통신
            retrofit.get_challengingrecipe(token)
                .enqueue(object : Callback<GetChallengingRecipeResponse> {
                    override fun onResponse(
                        call: Call<GetChallengingRecipeResponse>,
                        response: Response<GetChallengingRecipeResponse>
                    ) {
                        val result = response.body()
                        var i = 0

                        Log.d("통신", "${result}")
                        Log.d("통신","${token}")


                        while (true) {
                            if (challengingItemList.size == result?.data?.myChallenging?.size)
                                break
                            challengingItemList.add(
                                ItemRecipeChallengeData(
                                    result?.data?.myChallenging?.get(i)?.recipeId,
                                    result?.data?.myChallenging?.get(i)?.likes,
                                    result?.data?.myChallenging?.get(i)?.image,
                                    result?.data?.myChallenging?.get(i)?.name
                                )
                            )
                            i++
                        }
                        viewBinding.myTvv.text = challengingItemList.size.toString()
                        viewBinding.myRv.layoutManager = GridLayoutManager(this@MyChallengingActivity, 2)
                        val adapter =
                            MyChallengingRVAdapter(MyChallengingActivity(), challengingItemList)
                        viewBinding.myRv.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<GetChallengingRecipeResponse>, t: Throwable) {

                    }
                })

        }

    }
}