package com.example.umc_zipdabang.src.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.FragmentMyChallengingBinding
import com.example.umc_zipdabang.src.my.data.ItemRecipeData
import com.example.umc_zipdabang.src.my.data.MyChallengingRVAdapter

class MyChallengingFragment: Fragment() {
    lateinit var viewBinding: FragmentMyChallengingBinding

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

        val challengingItemList: ArrayList<ItemRecipeData> = arrayListOf()
        val challengingRVAdapter = MyChallengingRVAdapter(challengingItemList)

        challengingItemList.apply{
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
        }

        viewBinding.myRv.adapter = challengingRVAdapter
        viewBinding.myRv.layoutManager = GridLayoutManager(requireContext(),2)

        viewBinding.myTvv.setText(challengingItemList.size.toString())
    }
}