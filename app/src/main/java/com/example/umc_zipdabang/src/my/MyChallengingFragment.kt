package com.example.umc_zipdabang.src.my

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.FragmentMyChallengingBinding
import com.example.umc_zipdabang.src.my.data.ChallengingRVAdapter
import com.example.umc_zipdabang.src.my.data.ItemRecipeData
import kotlinx.coroutines.*
import java.lang.Runnable

class MyChallengingFragment: Fragment() {
    lateinit var viewBinding: FragmentMyChallengingBinding

    private var isLoading = false
    private var challengingList: ArrayList<ItemRecipeData> = arrayListOf()
    private lateinit var  aadapter: ChallengingRVAdapter
    val mainDispatcher : CoroutineDispatcher = Dispatchers.Main
    private lateinit var llayoutManager: GridLayoutManager

    val activity = context as Activity?
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMyChallengingBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.myBackbtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainfragmentcontainer, MyFragment())
                .commit()
        }

        setData()
        initAdapter()
        initScrollListener()
    }

    private fun setData(){
        challengingList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
        challengingList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
        challengingList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
        challengingList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
        challengingList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
        challengingList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
        challengingList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
        challengingList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
    }
    private fun initAdapter(){
        viewBinding = FragmentMyChallengingBinding.inflate(layoutInflater)

        aadapter = ChallengingRVAdapter(this@MyChallengingFragment, challengingList)
        llayoutManager = GridLayoutManager(activity, 2)

        viewBinding.myRv.layoutManager= llayoutManager
        viewBinding.myRv.adapter = aadapter

        llayoutManager.spanSizeLookup = object:GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                if(position == 0){
                    return 1
                }else if((position%8 == 0) && position == (challengingList.size-1)){
                    return 2
                }else{
                    return 1
                }
            }
        }
    }
    private fun initScrollListener(){
        viewBinding.myRv.setOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!isLoading){
                    if(viewBinding.myRv.layoutManager != null &&
                        (viewBinding.myRv.layoutManager as GridLayoutManager?)!!.findLastVisibleItemPosition() == (challengingList.size-1)){
                        moreItems()
                        isLoading = true
                    }
                }
            }
        })
    }
    private fun moreItems(){
        val runnable = Runnable{
            challengingList.add(ItemRecipeData(null,null,null))
            Log.d("insert before", "msg")
            aadapter.notifyItemInserted(challengingList.size-1)
        }
        viewBinding.myRv.post(runnable)
        CoroutineScope(mainDispatcher).launch{
            delay(2000)
            val runnable2 = Runnable{
                challengingList.removeAt(challengingList.size-1)
                val scrollToPosition = challengingList.size
                aadapter.notifyItemRemoved(scrollToPosition)

                challengingList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12)
                )
                challengingList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12)
                )
                challengingList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12)
                )
                challengingList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12)
                )
                challengingList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12)
                )
                challengingList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12)
                )
                challengingList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12)
                )
                challengingList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg",
                        "어르신도 좋아하실만한 담백한 블루베리 요거트",
                        12)
                )
                Log.d("change before", "msg")

                aadapter.notifyDataSetChanged()
                isLoading = false
            }
            runnable2.run()
        }
    }
}