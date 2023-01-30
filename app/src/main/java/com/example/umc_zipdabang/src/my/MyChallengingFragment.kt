package com.example.umc_zipdabang.src.my

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.FragmentMyChallengingBinding
import com.example.umc_zipdabang.src.my.data.ItemRecipeChallengeData
import com.example.umc_zipdabang.src.my.data.ItemRecipeData
import com.example.umc_zipdabang.src.my.data.MyChallengingRVAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyChallengingFragment: Fragment() {
    lateinit var viewBinding: FragmentMyChallengingBinding
    private val retrofit = RetrofitInstance.getInstance().create(APIS_My::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMyChallengingBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    ////레시피 총 갯수 서버한테 받기
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.myBackbtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainfragmentcontainer, MyFragment())
                .commit()
        }
        viewBinding.myToolbar.bringToFront()

        val challengingItemList: ArrayList<ItemRecipeChallengeData> = arrayListOf()
        val challengingRVAdapter = MyChallengingRVAdapter(challengingItemList)

        GlobalScope.launch(Dispatchers.IO) {

            //val tokenDb = TokenDatabase.getTokenDatabase(this@MyChallengingFragment)
            //       token1 = tokenDb.tokenDao().getToken().token.toString()
            val token1="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsMUBnbWFpbC5jb20iLCJpYXQiOjE2NzUwMDc2ODUsImV4cCI6MTY3NzU5OTY4NSwic3ViIjoidXNlckluZm8ifQ.38w5k86aZsM1qiRu2EGjN7wB2C4AMNluX_UAV1NcxGY"

            //통신
            retrofit.get_challengingrecipe(token1).enqueue(object: Callback<GetChallengingRecipeResponse>{
                override fun onResponse(
                    call: Call<GetChallengingRecipeResponse>,
                    response: Response<GetChallengingRecipeResponse>
                ) {
                    val result = response.body()
                    var i = 0

                    Log.d("통신","${result}")

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
                    viewBinding.myRv.layoutManager = GridLayoutManager(context, 2)
                    val adapter = MyChallengingRVAdapter(challengingItemList)
                    viewBinding.myRv.adapter = adapter
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<GetChallengingRecipeResponse>, t: Throwable) {

                }
            })
        }


//        viewBinding.myRv.adapter = challengingRVAdapter
//        viewBinding.myRv.layoutManager = GridLayoutManager(requireContext(),2)
//
//        viewBinding.myTvv.setText(challengingItemList.size.toString())
    }
}