package com.example.umc_zipdabang.config.src.main.Home



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.config.src.main.Jip.JipFragment
import com.example.umc_zipdabang.config.src.main.Jip.src.main.zipdabang_recipe_activities_fragments.ZipdabangRecipeFragment
import com.example.umc_zipdabang.config.src.main.Our.OurFragment
import com.example.umc_zipdabang.config.src.main.Our.OurRecipeFragment
import com.example.umc_zipdabang.databinding.ActivityMainBinding
import com.example.umc_zipdabang.src.my.MyFragment


class HomeMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var homeFragment : HomeFragment? = HomeFragment()
    private var jipFragment : ZipdabangRecipeFragment? = null
    private var ourFragment : OurRecipeFragment? = null
    private var userFragment : com.example.umc_zipdabang.src.my.MyFragment? = MyFragment()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       //val window = window
       // 상태바 투명하게
      //  window.setFlags(
        //WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        //WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)


        val frag= intent.getStringExtra("GOBACK")
        binding = ActivityMainBinding.inflate(layoutInflater)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        setContentView(binding.root)

        homeFragment= HomeFragment()

        supportFragmentManager.beginTransaction().replace(R.id.main_fl, homeFragment!!)
            .commitAllowingStateLoss()





        binding.mainNav.itemIconTintList=null
        binding.mainNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fl, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.tab_jip_receipe -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fl, ZipdabangRecipeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.tab_our_receipe -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fl, OurRecipeFragment())
                        .commitAllowingStateLoss()

                    return@setOnItemSelectedListener true
                }
                R.id.tab_user -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fl, MyFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                else -> {
                    false
                }
            }
        }

            if(frag=="GOBACK"){
                supportFragmentManager.beginTransaction()
                    .show(userFragment!!)
                    .commitAllowingStateLoss()
            }
        }
    }
