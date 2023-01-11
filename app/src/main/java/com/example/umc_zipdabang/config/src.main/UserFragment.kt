package com.example.umc_zipdabang.config.src.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_zipdabang.databinding.FragmentUserBinding

class UserFragment: Fragment() {

    private lateinit var  viewBinding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentUserBinding.inflate(layoutInflater)
        return viewBinding.root

    }

}