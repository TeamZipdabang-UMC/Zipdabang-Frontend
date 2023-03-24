package com.UMC.zipdabang.config.src.main.Jip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.UMC.zipdabang.databinding.FragmentJipBinding

class JipFragment:Fragment() {

    private lateinit var  viewBinding: FragmentJipBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentJipBinding.inflate(layoutInflater)
        return viewBinding.root


    }

}