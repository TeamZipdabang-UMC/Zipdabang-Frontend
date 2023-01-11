package com.example.umc_zipdabang.config.src.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_zipdabang.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var  scraps : ArrayList<Scrap> = arrayListOf(
        Scrap("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg","어르신도 좋아하실만한 담백한 블루베리 요거트",1),
        Scrap("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg","어르신도 좋아하실만한 담백한 블루베리 요거트",12)
    )

    private var coffee : ArrayList<Scrap> = arrayListOf( Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",1),
        Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12))

    private var beverage: ArrayList<Scrap> = arrayListOf( Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",1),
        Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12))

    private var tea : ArrayList<Scrap> = arrayListOf( Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",1),
        Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12))

    private var ade : ArrayList<Scrap> = arrayListOf( Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",1),
        Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12))

    private var smootie_juice : ArrayList<Scrap> = arrayListOf( Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",1),
        Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12))

    private var health : ArrayList<Scrap> = arrayListOf( Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",1),
        Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12))


    private val scraplist : ArrayList<ArrayList<Scrap>> = arrayListOf(scraps)

    private val category : ArrayList<Home_receipe> =  arrayListOf(Home_receipe("커피", coffee),
                                                                  Home_receipe("음료",beverage),
                                                                  Home_receipe("티",tea),
                                                                  Home_receipe("에이드",ade),
                                                                  Home_receipe("스무디/주스",smootie_juice),
                                                                  Home_receipe("건강음료",health))





    private lateinit var  viewBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater)
        viewBinding.homeRvMyScrap.layoutManager= LinearLayoutManager(activity as MainActivity, LinearLayoutManager.VERTICAL,false)
        val adapter1 = ScrapAdapter(activity as MainActivity, scraplist)
        viewBinding.homeRvMyScrap.adapter= adapter1



        viewBinding.homeRvReceipe.layoutManager= LinearLayoutManager(activity as MainActivity, LinearLayoutManager.VERTICAL,false)
        val adapter2 = ReceipeAdapter(activity as MainActivity,category)
        viewBinding.homeRvReceipe.adapter= adapter2
        adapter2.notifyDataSetChanged()
        viewBinding.homeRvReceipe.isNestedScrollingEnabled=false


        return viewBinding.root


    }

}