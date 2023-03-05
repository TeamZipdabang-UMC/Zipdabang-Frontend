package com.UMC.zipdabang.src.my

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.UMC.zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.UMC.zipdabang.src.my.data.ItemRecipeChallengeData
import com.UMC.zipdabang.src.my.data.MyChallengedoneRVAdapter
import com.example.umc_zipdabang.databinding.ActivityMyChallengedoneBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyChallengedoneActivity: AppCompatActivity() {

    private lateinit var viewBinding: ActivityMyChallengedoneBinding
    private val retrofit = RetrofitInstance.getInstance().create(APIS_My::class.java)
    var token: String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyChallengedoneBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.myBackbtn.setOnClickListener{
            finish()
        }

        val challengedoneItemList : ArrayList<ItemRecipeChallengeData> = arrayListOf()
        val challengedoneRVAdapter = MyChallengedoneRVAdapter(MyChallengedoneActivity(), challengedoneItemList)

        GlobalScope.launch(Dispatchers.IO) {

            val tokenDb = TokenDatabase.getTokenDatabase(this@MyChallengedoneActivity)
            token = tokenDb.tokenDao().getToken().token.toString()

            //통신
            retrofit.get_challengedonerecipe(token).enqueue(object:
                Callback<GetChallengedoneRecipeResponse> {
                override fun onResponse(
                    call: Call<GetChallengedoneRecipeResponse>,
                    response: Response<GetChallengedoneRecipeResponse>
                ) {
                    val result = response.body()
                    var i = 0

                    Log.d("통신","${result}")

                    while (true) {
                        if (challengedoneItemList.size == result?.data?.myComplete?.size)
                            break
                        challengedoneItemList.add(
                            ItemRecipeChallengeData(
                                result?.data?.myComplete?.get(i)?.recipeId,
                                result?.data?.myComplete?.get(i)?.likes,
                                result?.data?.myComplete?.get(i)?.image,
                                result?.data?.myComplete?.get(i)?.name
                            )
                        )
                        i++
                    }
                    viewBinding.myTvv.text = challengedoneItemList.size.toString()
                    viewBinding.myRv.layoutManager = GridLayoutManager(this@MyChallengedoneActivity, 2)
                    val adapter = MyChallengedoneRVAdapter(MyChallengedoneActivity() ,challengedoneItemList)
                    viewBinding.myRv.adapter = adapter
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<GetChallengedoneRecipeResponse>, t: Throwable) {

                }
            })
        }

    }
}