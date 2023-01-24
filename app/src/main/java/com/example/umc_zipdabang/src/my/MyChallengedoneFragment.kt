package com.example.umc_zipdabang.src.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.FragmentMyChallengedoneBinding
import com.example.umc_zipdabang.src.my.data.ItemRecipeData
import com.example.umc_zipdabang.src.my.data.MyChallengedoneRVAdapter
import com.example.umc_zipdabang.src.my.data.MyChallengingRVAdapter

class MyChallengedoneFragment: Fragment() {

    lateinit var viewBinding: FragmentMyChallengedoneBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMyChallengedoneBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.myBackbtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainfragmentcontainer, MyFragment())
                .commit()
        }

/*        viewBinding.myToolbar.setOnMenuItemClickListener{ menuItem: MenuItem->
            when(menuItem.itemId){
                R.id.menu_back ->{
                    true
                }
                else -> false
            }
        }*/

        val challengedoneItemList : ArrayList<ItemRecipeData> = arrayListOf()
        val challengedoneRVAdapter = MyChallengedoneRVAdapter(challengedoneItemList)

        challengedoneItemList.apply{
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
        }

        viewBinding.myRv.adapter = challengedoneRVAdapter
        viewBinding.myRv.layoutManager = GridLayoutManager(requireContext(),2)

        viewBinding.myTvv.setText(challengedoneItemList.size.toString())
    }
}