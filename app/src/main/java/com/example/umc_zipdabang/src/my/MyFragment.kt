package com.example.umc_zipdabang.src.my

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.FragmentMyBinding
import com.example.umc_zipdabang.src.my.data.IntroChallengedoneRVAdapter
import com.example.umc_zipdabang.src.my.data.IntroChallengingRVAdapter
import com.example.umc_zipdabang.src.my.data.ItemRecipeData
import com.example.umc_zipdabang.src.my.data.ItemRecipeRVAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val challengedoneList: ArrayList<ItemRecipeData> = arrayListOf()
        val challengedoneRVAdapter = IntroChallengedoneRVAdapter(challengedoneList)
        challengedoneList.apply{
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
        }
        viewBinding.myRvChallengedone.adapter = challengedoneRVAdapter
        viewBinding.myRvChallengedone.layoutManager = GridLayoutManager(requireContext(), 2)

        val challengingList: ArrayList<ItemRecipeData> = arrayListOf()
        val challengingRVAdapter = IntroChallengingRVAdapter(challengingList)
        challengingList.apply{
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
        }
        viewBinding.myRvChallenging.adapter = challengingRVAdapter
        viewBinding.myRvChallenging.layoutManager = GridLayoutManager(requireContext(),2)

        val itemRecipeList:ArrayList<ItemRecipeData> = arrayListOf()
        val itemRecipeRVAdapter = ItemRecipeRVAdapter(itemRecipeList)
        itemRecipeList.apply{
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
            add(ItemRecipeData("https://user-images.githubusercontent.com/101035437/212458119-c8b27e99-6208-4109-b041-b7064c213055.png","녹차를 마시는데 20자 정도 들어가야지지",123))
        }
        viewBinding.myRvMyscrap.adapter = itemRecipeRVAdapter
        viewBinding.myRvMyscrap.layoutManager = GridLayoutManager(requireContext(),2)


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

        val sharedPreference = this.getActivity()?.getSharedPreferences("writing",0)
        val editor = sharedPreference?.edit() //제목, 카테고리, 시간, 한줄소개, 재료이름, 재료갯수, 스텝설명, 후기, 재료스탭 갯수

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