package com.UMC.umc_zipdabang.src.my

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.UMC.umc_zipdabang.databinding.ActivityMyMyrecipeBinding
import com.UMC.umc_zipdabang.config.src.main.Home.HomeMainActivity
import com.UMC.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.UMC.umc_zipdabang.src.my.data.ItemRecipeChallengeData
import com.UMC.umc_zipdabang.src.my.data.MyMyrecipeRVAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyMyrecipeActivity: AppCompatActivity() {

    private lateinit var viewBinding: ActivityMyMyrecipeBinding
    private val retrofit = RetrofitInstance.getInstance().create(APIS_My::class.java)
    var token: String = " "
    private var scraps: ArrayList<ItemRecipeChallengeData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyMyrecipeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)




        val myRecipeItemList: ArrayList<ItemRecipeChallengeData> = arrayListOf()
        val myRecipeRVAdapter = MyMyrecipeRVAdapter(MyMyrecipeActivity(), myRecipeItemList)

        GlobalScope.launch(Dispatchers.IO) {

            val tokenDb = TokenDatabase.getTokenDatabase(this@MyMyrecipeActivity)
            token = tokenDb.tokenDao().getToken().token.toString()

            //통신
            retrofit.get_myrecipe(token).enqueue(object :
                Callback<GetMyRecipeResponse> {
                override fun onResponse(
                    call: Call<GetMyRecipeResponse>,
                    response: Response<GetMyRecipeResponse>
                ) {
                    val result = response.body()
                    var i = 0

                    Log.d("통신", "${result}")

                    while (true) {
                        if (scraps.size == result?.data?.size)
                            break
                        scraps.add(
                            ItemRecipeChallengeData(
                                result?.data?.get(i)?.recipeId,
                                result?.data?.get(i)?.likes,
                                result?.data?.get(i)?.image,
                                result?.data?.get(i)?.name
                            )
                        )
                        i++
                    }
                    viewBinding.myTvv.text = scraps.size.toString()
                    viewBinding.myRv.layoutManager = GridLayoutManager(this@MyMyrecipeActivity, 2)
                    val adapter = MyMyrecipeRVAdapter(MyMyrecipeActivity(), scraps)
                    viewBinding.myRv.adapter = adapter
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<GetMyRecipeResponse>, t: Throwable) {

                }
            })
        }

        viewBinding.myBackbtn.bringToFront()
        viewBinding.myBackbtn.setOnClickListener{
            val intent = Intent(this@MyMyrecipeActivity, HomeMainActivity::class.java)
            startActivity(intent)
        }


        viewBinding.myFixbtn.setOnClickListener {
            val intent = Intent(this@MyMyrecipeActivity, MyMyrecipeEditActivity::class.java)
            intent.putExtra("array", scraps)
            startActivity(intent)
        }

    }
}