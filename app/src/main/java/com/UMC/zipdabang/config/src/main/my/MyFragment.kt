package com.UMC.zipdabang.config.src.main.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.UMC.zipdabang.databinding.FragmentUserBinding

class MyFragment: Fragment() {

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