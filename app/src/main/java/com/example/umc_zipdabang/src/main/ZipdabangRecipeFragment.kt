package com.example.umc_zipdabang.src.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_zipdabang.databinding.FragmentZipdabangRecipeBinding

class ZipdabangRecipeFragment: Fragment() {
    private lateinit var viewBinding: FragmentZipdabangRecipeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentZipdabangRecipeBinding.inflate(inflater, container, false)
        return viewBinding.root
    }
}