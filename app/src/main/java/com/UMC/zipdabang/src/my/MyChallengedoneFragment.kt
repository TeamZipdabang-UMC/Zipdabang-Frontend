/*
package com.example.umc_zipdabang.src.my

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.config.src.main.Home.HomeMainActivity
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.example.umc_zipdabang.databinding.FragmentMyChallengedoneBinding
import com.example.umc_zipdabang.src.my.data.ItemRecipeChallengeData
import com.example.umc_zipdabang.src.my.data.ItemRecipeData
import com.example.umc_zipdabang.src.my.data.MyChallengedoneRVAdapter
import com.example.umc_zipdabang.src.my.data.MyChallengingRVAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyChallengedoneFragment: Fragment() {

    lateinit var viewBinding: FragmentMyChallengedoneBinding
    private val retrofit = RetrofitInstance.getInstance().create(APIS_My::class.java)
    var token: String = " "

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMyChallengedoneBinding.inflate(layoutInflater)
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

        val challengedoneItemList : ArrayList<ItemRecipeChallengeData> = arrayListOf()
        val challengedoneRVAdapter = MyChallengedoneRVAdapter(HomeMainActivity(), challengedoneItemList)

        GlobalScope.launch(Dispatchers.IO) {

            val tokenDb = TokenDatabase.getTokenDatabase(activity as HomeMainActivity)
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
                    viewBinding.myRv.layoutManager = GridLayoutManager(context, 2)
                    val adapter = MyChallengedoneRVAdapter(HomeMainActivity() ,challengedoneItemList)
                    viewBinding.myRv.adapter = adapter
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<GetChallengedoneRecipeResponse>, t: Throwable) {

                }
            })
        }


//        viewBinding.myRv.adapter = challengedoneRVAdapter
//        viewBinding.myRv.layoutManager = GridLayoutManager(requireContext(),2)
//
//        viewBinding.myTvv.setText(challengedoneItemList.size.toString())
    }
}*/
