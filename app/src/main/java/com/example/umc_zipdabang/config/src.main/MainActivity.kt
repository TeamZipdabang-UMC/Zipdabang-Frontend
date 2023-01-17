package com.example.umc_zipdabang.config.src.main



import com.example.umc_zipdabang.config.src.main.Home.HomeFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.umc_zipdabang.R
import com.example.umc_zipdabang.config.src.main.Our.OurFragment
import com.example.umc_zipdabang.config.src.main.Jip.JipFragment
import com.example.umc_zipdabang.config.src.main.User.UserFragment
import com.example.umc_zipdabang.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var homeFragment : HomeFragment? = null
    private var  jipFragment : JipFragment? = null
    private var ourFragment : OurFragment? = null
   private var userFragment : UserFragment? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       //val window = window
       // 상태바 투명하게
      //  window.setFlags(
        //WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        //WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)



        binding = ActivityMainBinding.inflate(layoutInflater)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)


        setContentView(binding.root)

        homeFragment= HomeFragment()

        supportFragmentManager.beginTransaction().replace(R.id.main_fl, homeFragment!!)
            .commitAllowingStateLoss()

        binding.mainNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tab_home -> {
                    if(homeFragment==null) {
                        homeFragment= HomeFragment()
                        supportFragmentManager.beginTransaction()
                            .add(R.id.main_fl, homeFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(homeFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .show(homeFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(jipFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .hide(jipFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(ourFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .hide(ourFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(userFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .hide(userFragment!!)
                            .commitAllowingStateLoss()
                    }
                    return@setOnItemSelectedListener true
                }
                R.id.tab_jip_receipe -> {
                    if(jipFragment==null) {
                        jipFragment= JipFragment()
                        supportFragmentManager.beginTransaction()
                            .add(R.id.main_fl, jipFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(jipFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .show(jipFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(homeFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .hide(homeFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(ourFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .hide(ourFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(userFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .hide(userFragment!!)
                            .commitAllowingStateLoss()
                    }
                    return@setOnItemSelectedListener true
                }
                R.id.tab_our_receipe -> {
                    if(ourFragment==null) {
                        ourFragment= OurFragment()
                        supportFragmentManager.beginTransaction()
                            .add(R.id.main_fl, ourFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(ourFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .show(ourFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(homeFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .hide(homeFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(jipFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .hide(jipFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(userFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .hide(userFragment!!)
                            .commitAllowingStateLoss()
                    }
                    return@setOnItemSelectedListener true
                }
                R.id.tab_user -> {
                    if(userFragment==null) {
                        userFragment= UserFragment()
                        supportFragmentManager.beginTransaction()
                            .add(R.id.main_fl, userFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(userFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .show(userFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(homeFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .hide(homeFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(ourFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .hide(ourFragment!!)
                            .commitAllowingStateLoss()
                    }
                    if(jipFragment!=null){
                        supportFragmentManager.beginTransaction()
                            .hide(jipFragment!!)
                            .commitAllowingStateLoss()
                    }
                    return@setOnItemSelectedListener true
                }
                else -> {
                    false
                }
            }

        }
    }
}