package com.example.umc_zipdabang.src.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.databinding.FragmentMySaveBinding

class MySaveFragment: Fragment() {
    lateinit var viewBinding: FragmentMySaveBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMySaveBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.myBackbtn.setOnClickListener {
            viewBinding.myBackbtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.mainfragmentcontainer, MyFragment())
                    .commit()
            }
        }
    }
}