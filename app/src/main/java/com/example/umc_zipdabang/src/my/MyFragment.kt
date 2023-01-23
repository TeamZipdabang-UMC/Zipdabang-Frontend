package com.example.umc_zipdabang.src.my

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.FragmentMyBinding

class MyFragment : Fragment(){

    lateinit var viewBinding: FragmentMyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding= FragmentMyBinding.inflate(layoutInflater)

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
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainfragmentcontainer, MySaveFragment())
                .addToBackStack(null)
                .commit()
        }

        viewBinding.myBtnIcon3.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainfragmentcontainer, MyMyrecipeFragment())
                .addToBackStack(null)
                .commit()
        }

/*      //프로필설정 버튼 눌렀을떄 리스너->하현과 연결
        viewBinding.myBtnIcon4.setOnClickListener {
            val intent = Intent(activity, //activity이름::class.java)
                startActivity(intent)
        }*/

        return viewBinding.root
    }
}