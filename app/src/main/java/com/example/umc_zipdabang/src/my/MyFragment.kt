package com.example.umc_zipdabang.src.my

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.FragmentMyBinding
import com.example.umc_zipdabang.src.my.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFragment : Fragment(){

    lateinit var viewBinding: FragmentMyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding= FragmentMyBinding.inflate(layoutInflater)
        return viewBinding.root
    }


    private  var challenging: ArrayList<ItemRecipeChallengeData> = arrayListOf()
    private  var complete: ArrayList<ItemRecipeChallengeData> = arrayListOf()
    private  var scrap: ArrayList<ItemRecipeChallengeData> = arrayListOf()

    private val retrofit = RetrofitInstance.getInstance().create(APIS_My::class.java)
    private var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsMUBnbWFpbC5jb20iLCJpYXQiOjE2NzUwMDc2ODUsImV4cCI6MTY3NzU5OTY4NSwic3ViIjoidXNlckluZm8ifQ.38w5k86aZsM1qiRu2EGjN7wB2C4AMNluX_UAV1NcxGY"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val challengedoneList: ArrayList<ItemRecipeData> = arrayListOf()
        val challengedoneRVAdapter = IntroChallengedoneRVAdapter(challengedoneList)

        viewBinding.myRvChallengedone.adapter = challengedoneRVAdapter
        viewBinding.myRvChallengedone.layoutManager = GridLayoutManager(requireContext(), 2)

        val challengingList: ArrayList<ItemRecipeData> = arrayListOf()
        val challengingRVAdapter = IntroChallengingRVAdapter(challengingList)

        viewBinding.myRvChallenging.adapter = challengingRVAdapter
        viewBinding.myRvChallenging.layoutManager = GridLayoutManager(requireContext(),2)

        val itemRecipeList:ArrayList<ItemRecipeData> = arrayListOf()
        val itemRecipeRVAdapter = ItemRecipeRVAdapter(itemRecipeList)

        viewBinding.myRvMyscrap.adapter = itemRecipeRVAdapter
        viewBinding.myRvMyscrap.layoutManager = GridLayoutManager(requireContext(),2)*/


        GlobalScope.launch(Dispatchers.IO){
            //viewBinding.etSearch.setText("")


            retrofit.get_recipe_two(token).enqueue(object: Callback<GetRecipeTwoResponse>{
                override fun onResponse(
                    call: Call<GetRecipeTwoResponse>,
                    response: Response<GetRecipeTwoResponse>
                ) {
                    Log.d("통신", "통신 성공")
                    val result = response.body()

                    //도전중
                    if(result?.data?.myChallengingOverView?.size != 0){
                        challenging.add(
                            ItemRecipeChallengeData(
                                result?.data?.myChallengingOverView?.get(0)?.recipeId,
                                result?.data?.myChallengingOverView?.get(0)?.likes,
                                result?.data?.myChallengingOverView?.get(0)?.image,
                                result?.data?.myChallengingOverView?.get(0)?.name,
                            )
                        )
                        if(result?.data?.myChallengingOverView?.size != 1){
                            challenging.add(
                                ItemRecipeChallengeData(
                                    result?.data?.myChallengingOverView?.get(1)?.recipeId,
                                    result?.data?.myChallengingOverView?.get(1)?.likes,
                                    result?.data?.myChallengingOverView?.get(1)?.image,
                                    result?.data?.myChallengingOverView?.get(1)?.name,
                                )
                            )
                        }
                    }

                    //도전완료
                    if(result?.data?.myCompleteOverView?.size !=0){
                        complete.add(
                            ItemRecipeChallengeData(
                                result?.data?.myCompleteOverView?.get(0)?.recipeId,
                                result?.data?.myCompleteOverView?.get(0)?.likes,
                                result?.data?.myCompleteOverView?.get(0)?.image,
                                result?.data?.myCompleteOverView?.get(0)?.name,
                            )
                        )

                        if(result?.data?.myCompleteOverView?.size !=1){
                            complete.add(
                                ItemRecipeChallengeData(
                                    result?.data?.myCompleteOverView?.get(1)?.recipeId,
                                    result?.data?.myCompleteOverView?.get(1)?.likes,
                                    result?.data?.myCompleteOverView?.get(1)?.image,
                                    result?.data?.myCompleteOverView?.get(1)?.name,
                                )
                            )

                        }
                    }
                    //내스크랩
                    if(result?.data?.myScrapOverView?.size != 0){
                        scrap.add(
                            ItemRecipeChallengeData(
                                result?.data?.myScrapOverView?.get(0)?.recipeId,
                                result?.data?.myScrapOverView?.get(0)?.likes,
                                result?.data?.myScrapOverView?.get(0)?.image,
                                result?.data?.myScrapOverView?.get(0)?.name,
                            )
                        )

                        if(result?.data?.myScrapOverView?.size != 1){
                            scrap.add(
                                ItemRecipeChallengeData(
                                    result?.data?.myScrapOverView?.get(1)?.recipeId,
                                    result?.data?.myScrapOverView?.get(1)?.likes,
                                    result?.data?.myScrapOverView?.get(1)?.image,
                                    result?.data?.myScrapOverView?.get(1)?.name,
                                )
                            )
                        }
                    }


                    viewBinding.myRvChallenging.layoutManager = GridLayoutManager(context, 2)
                    val adapter1 = IntroChallengingRVAdapter(challenging)
                    viewBinding.myRvChallenging.adapter = adapter1
                    adapter1.notifyDataSetChanged()

                    viewBinding.myRvChallengedone.layoutManager = GridLayoutManager(context, 2)
                    val adapter2 = IntroChallengedoneRVAdapter(complete)
                    viewBinding.myRvChallengedone.adapter = adapter2
                    adapter2.notifyDataSetChanged()

                    viewBinding.myRvMyscrap.layoutManager = GridLayoutManager(context, 2)
                    val adapter3 = ItemRecipeRVAdapter(scrap)
                    viewBinding.myRvMyscrap.adapter = adapter3
                    adapter3.notifyDataSetChanged()

                        /////
                   // adapter1.setOnItemClickListener1(object: IntroChallengingRVAdapter)

                }

                override fun onFailure(call: Call<GetRecipeTwoResponse>, t: Throwable) {
                    Log.d("통신", "통신 실패")
                }
            })
        }




        

        viewBinding.myBtnChallenging.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainfragmentcontainer, MyChallengingFragment())
                .addToBackStack(null)
                .commit()
        }
        viewBinding.myBtnChallengedone.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainfragmentcontainer, MyChallengedoneFragment())
                .addToBackStack(null)
                .commit()
        }
/*      //Myscrap 버튼 눌렀을때 리스너->하현과 연결!
        viewBinding.myBtnMyscrap.setOnClickListener {
            val intent = Intent(activity, //activity이름::class.java)
            startActivity(intent)
        }*/


        viewBinding.myBtnIcon1.setOnClickListener {
            val intent = Intent(activity, MyWritingActivity::class.java)
            startActivity(intent)
        }
        viewBinding.myBtnIcon2.setOnClickListener {
            val intent = Intent(activity, MySaveActivity::class.java)
            startActivity(intent)
        }
        viewBinding.myBtnIcon3.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainfragmentcontainer, MyMyrecipeFragment())
                .addToBackStack(null)
                .commit()
        }
       //프로필설정 버튼 눌렀을떄 리스너->하현과 연결
        viewBinding.myBtnIcon4.setOnClickListener {
            val intent = Intent(activity, MySettingActivity::class.java)
            startActivity(intent)
        }
    }
}