package com.example.umc_zipdabang.config.src.main.Home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_zipdabang.config.src.main.Home.Scrap.MainScrapAdapter
import com.example.umc_zipdabang.config.src.main.Home.Scrap.Main_Scrap
import com.example.umc_zipdabang.config.src.main.Home.Scrap.MyScapActivity
import com.example.umc_zipdabang.config.src.main.Home.category.*
import com.example.umc_zipdabang.config.src.main.Home.reciepe.Home_receipe
import com.example.umc_zipdabang.config.src.main.Home.reciepe.ReceipeAdapter
import com.example.umc_zipdabang.config.src.main.Home.search.SearchActivity
import com.example.umc_zipdabang.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var  scraps : ArrayList<Main_Scrap> = arrayListOf(
        Main_Scrap("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg","어르신도 좋아하실만한 담백한 블루베리 요거트",1),
        Main_Scrap("https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788946473478.jpg","어르신도 좋아하실만한 담백한 블루베리 요거트",100000)
    )

    private var coffee : ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","어르신도 좋아하실만한 담백한 블루베리 요거트",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12)
    )

    private var beverage: ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12)
    )

    private var tea : ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12)
    )

    private var ade : ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","연인과 함께 먹기 좋은 딸기초코 파르페",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12)
    )

    private var smootie_juice : ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12)
    )

    private var health : ArrayList<Main_Scrap> = arrayListOf( Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","상콤달콤한 딸기샤베트",1),
        Main_Scrap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-pq7ZzXFO-oPP1bf-rcnsFAVbTIRQgyNwfDQHwvBn2CHmVVNfHD9EgaP2ChFlzdQc1ds&usqp=CAU","맛있게 만들자",12)
    )


    private val scraplist : ArrayList<ArrayList<Main_Scrap>> = arrayListOf(scraps)

    private val category : ArrayList<Home_receipe> =  arrayListOf(
                                                                  Home_receipe("커피", coffee),
                                                                  Home_receipe("beverage",beverage),
                                                                  Home_receipe("티",tea),
                                                                  Home_receipe("에이드",ade),
                                                                  Home_receipe("스무디/주스",smootie_juice),
                                                                  Home_receipe("건강음료",health)
    )





    private lateinit var  viewBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater)

        //마이스크랩 부분
        viewBinding.homeRvMyScrap.layoutManager= LinearLayoutManager(activity as HomeMainActivity, LinearLayoutManager.VERTICAL,false)
        val adapter1 = MainScrapAdapter(activity as HomeMainActivity, scraplist)
        viewBinding.homeRvMyScrap.adapter= adapter1

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        viewBinding.btTomyscrap.setOnClickListener{
            val intent= Intent(activity, MyScapActivity::class.java)
            startActivity(intent)
        }

      //카테고리별 음료
        viewBinding.homeRvReceipe.layoutManager= LinearLayoutManager(activity as HomeMainActivity, LinearLayoutManager.VERTICAL,false)
        val adapter2 = ReceipeAdapter(activity as HomeMainActivity,category)
        viewBinding.homeRvReceipe.adapter= adapter2
        adapter2.notifyDataSetChanged()
        viewBinding.homeRvReceipe.isNestedScrollingEnabled=false

        //검색(엔터키 사용)
        viewBinding.etSearch.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(view: View?, i: Int, keyEvent: KeyEvent?): Boolean {
                when (i) {
                    KeyEvent.KEYCODE_ENTER -> {
                        val search= Intent(context,SearchActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        search.putExtra("search",viewBinding.etSearch.text.toString())
                        startActivity(search)
                        viewBinding.etSearch.setText("")

                    }
                }
                return false
            }
        })


        //검색(검색 이미지 누름)
        viewBinding.ivSearch.setOnClickListener{

            val intent= Intent(context, SearchActivity::class.java)
            intent.putExtra("search",viewBinding.etSearch.text.toString())

            startActivity(intent)
            viewBinding.etSearch.setText("")
        }


        //카테고리별 목록 이동
        adapter2.setOnItemClickListener(object : ReceipeAdapter.OnItemClickListener {
            override fun onItemClick(v: View?, pos: Int) {

                when(pos){

                    0 ->{
                        val intent = Intent(context, CategoryCoffeeActivity::class.java)
                        startActivity(intent)
                    }
                    1 ->{
                        val intent = Intent(context, CategoryBeverageActivity::class.java)
                        startActivity(intent)
                    } 2 ->{
                    val intent = Intent(context, CategoryTeaActivity::class.java)
                    startActivity(intent)
                } 3 ->{
                    val intent = Intent(context, CategoryAdeActivity::class.java)
                    startActivity(intent)
                } 4 ->{
                    val intent = Intent(context, CategorySmoothieActivity::class.java)
                    startActivity(intent)
                } 5 ->{
                    val intent = Intent(context, CategoryHealthActivity::class.java)
                    startActivity(intent)
                }



                }



    }

})

        return viewBinding.root
    }
}