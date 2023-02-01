package com.example.umc_zipdabang.src.my

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.config.src.main.Home.HomeMainActivity
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.example.umc_zipdabang.databinding.ActivityMainBinding
import com.example.umc_zipdabang.databinding.FragmentMyMyrecipeBinding
import com.example.umc_zipdabang.src.main.MainActivity
import com.example.umc_zipdabang.src.my.data.ItemRecipeChallengeData
import com.example.umc_zipdabang.src.my.data.ItemRecipeData
import com.example.umc_zipdabang.src.my.data.MyChallengingRVAdapter
import com.example.umc_zipdabang.src.my.data.MyMyrecipeRVAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyMyrecipeFragment : Fragment(){
    lateinit var viewBinding: FragmentMyMyrecipeBinding
    private val retrofit = RetrofitInstance.getInstance().create(APIS_My::class.java)
    private var scraps: ArrayList<ItemRecipeChallengeData> = arrayListOf()

    var token: String = " "

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMyMyrecipeBinding.inflate(layoutInflater)

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


        val myRecipeItemList: ArrayList<ItemRecipeChallengeData> = arrayListOf()
        val myRecipeRVAdapter = MyMyrecipeRVAdapter(HomeMainActivity(), myRecipeItemList)

        GlobalScope.launch(Dispatchers.IO) {

            val tokenDb = TokenDatabase.getTokenDatabase(activity as HomeMainActivity)
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
                    viewBinding.myRv.layoutManager = GridLayoutManager(context, 2)
                    val adapter = MyMyrecipeRVAdapter(HomeMainActivity(), scraps)
                    viewBinding.myRv.adapter = adapter
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<GetMyRecipeResponse>, t: Throwable) {

                }
            })
        }

            viewBinding.myFixbtn.setOnClickListener {
                val intent = Intent(activity, MyMyrecipeEditActivity::class.java)
                intent.putExtra("array", scraps)
                startActivity(intent)
            }
        }

}

