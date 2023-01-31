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
import com.example.umc_zipdabang.databinding.FragmentMyMyrecipeBinding
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
        val myRecipeRVAdapter = MyMyrecipeRVAdapter(myRecipeItemList)

        GlobalScope.launch(Dispatchers.IO) {

            //val tokenDb = TokenDatabase.getTokenDatabase(this)
            //       token1 = tokenDb.tokenDao().getToken().token.toString()
            var token1 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsMUBnbWFpbC5jb20iLCJpYXQiOjE2NzUwMDc2ODUsImV4cCI6MTY3NzU5OTY4NSwic3ViIjoidXNlckluZm8ifQ.38w5k86aZsM1qiRu2EGjN7wB2C4AMNluX_UAV1NcxGY"
            //통신
            retrofit.get_myrecipe(token1).enqueue(object:
                Callback<GetMyRecipeResponse> {
                override fun onResponse(
                    call: Call<GetMyRecipeResponse>,
                    response: Response<GetMyRecipeResponse>
                ) {
                    val result = response.body()
                    var i = 0

                    Log.d("통신","${result}")

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
                    val adapter = MyMyrecipeRVAdapter(scraps)
                    viewBinding.myRv.adapter = adapter
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<GetMyRecipeResponse>, t: Throwable) {

                }
            })

            viewBinding.myFixbtn.setOnClickListener {
                val intent = Intent(activity, MyMyrecipeEditActivity::class.java)
                intent.putExtra("array", scraps)
                startActivity(intent)
            }
        }

//        viewBinding.myRv.adapter = myRecipeRVAdapter
//        viewBinding.myRv.layoutManager = GridLayoutManager(requireContext(),2)
//
//        viewBinding.myTvv.setText(myRecipeItemList.size.toString())


    }

}