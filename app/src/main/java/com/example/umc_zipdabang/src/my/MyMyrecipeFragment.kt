package com.example.umc_zipdabang.src.my

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
import com.example.umc_zipdabang.databinding.ActivityMainBinding
import com.example.umc_zipdabang.databinding.FragmentMyMyrecipeBinding
import com.example.umc_zipdabang.src.main.MainActivity
import com.example.umc_zipdabang.src.my.data.ItemRecipeData
import com.example.umc_zipdabang.src.my.data.MyMyrecipeRVAdapter
import kotlinx.coroutines.*
import java.lang.Runnable

class MyMyrecipeFragment : Fragment(){
    lateinit var viewBinding: FragmentMyMyrecipeBinding

    lateinit var adapter: MyMyrecipeRVAdapter
    val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
    val allRecipesList: ArrayList<ItemRecipeData> = arrayListOf()
    var isLoading = false

    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMyMyrecipeBinding.inflate(layoutInflater)

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

        viewBinding.myTvv.setText(allRecipesList.size.toString())
    }

    private fun setData() {
        allRecipesList.add(
           ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
        allRecipesList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
        allRecipesList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
        allRecipesList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
        allRecipesList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
        allRecipesList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
        allRecipesList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
        allRecipesList.add(
            ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
        )
    }

    private fun initAdapter(){
        adapter = MyMyrecipeRVAdapter(activity as MainActivity, allRecipesList)
        val layoutManager = GridLayoutManager(activity as MainActivity, 2)
        viewBinding.myRv.layoutManager=layoutManager
        viewBinding.myRv.adapter = adapter

        layoutManager.setSpanSizeLookup(object: GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int):Int{
                if (position == 0) {
                    return 1
                } else if ((position % 8 == 0) && position == (allRecipesList.size-1)) {
                    return 2
                } else {
                    return 1
                }
            }
        })
    }

    private fun initScrollListener(){
        viewBinding.myRv.setOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    if (viewBinding.myRv.layoutManager != null && (viewBinding.myRv.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() == (allRecipesList.size - 1)) {
                        //리스트 마지막o
                        moreItems()
                        isLoading = true

                    }
                }
            }
        })
    }

    private fun moreItems(){
        val runnable = Runnable{
            allRecipesList.add(ItemRecipeData(null,null,null))
            Log.d("insert before","msg")
            adapter.notifyItemInserted(allRecipesList.size-1)
        }
        viewBinding.myRv.post(runnable)

        CoroutineScope(mainDispatcher).launch{
            delay(2000)
            val runnable2 = Runnable{
                allRecipesList.removeAt(allRecipesList.size-1)
                val scrollToPosition = allRecipesList.size
                adapter.notifyItemRemoved(scrollToPosition)

                allRecipesList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
                )
                allRecipesList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
                )
                allRecipesList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
                )
                allRecipesList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
                )
                allRecipesList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
                )
                allRecipesList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
                )
                allRecipesList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
                )
                allRecipesList.add(
                    ItemRecipeData("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg", "어르신도 좋아하실만한 담백한 블루베리 요거트", 12)
                )

                adapter.notifyDataSetChanged()
                isLoading = false
            }
            runnable2.run()
        }
    }
}