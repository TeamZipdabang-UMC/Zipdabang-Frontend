package com.UMC.zipdabang.src.my

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.UMC.zipdabang.databinding.ActivityMyChallengingBinding
import com.UMC.zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.UMC.zipdabang.src.my.data.ItemRecipeChallengeData
import com.UMC.zipdabang.src.my.data.MyChallengingRVAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyChallengingActivity: AppCompatActivity() {

    private lateinit var viewBinding: ActivityMyChallengingBinding
    private val retrofit = RetrofitInstance.getInstance().create(APIS_My::class.java)
    var token: String = " "



    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMyChallengingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)


        viewBinding.myBackbtn.setOnClickListener{
            finish()
        }

      /*  val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val pxWidth = displayMetrics.widthPixels
        val pxHeight = displayMetrics.heightPixels*/

        fun getScreenWidth(context: Context): Int {
          val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
          return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
              val windowMetrics = wm.currentWindowMetrics
              val insets = windowMetrics.windowInsets
                  .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
              windowMetrics.bounds.width() - insets.left - insets.right
          } else {
              val displayMetrics = DisplayMetrics()
              wm.defaultDisplay.getMetrics(displayMetrics)
              displayMetrics.widthPixels
          }
      }

        fun getScreenHeight(context: Context): Int {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val windowMetrics = wm.currentWindowMetrics
                val insets = windowMetrics.windowInsets
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
                windowMetrics.bounds.height() - insets.bottom - insets.top
            } else {
                val displayMetrics = DisplayMetrics()
                wm.defaultDisplay.getMetrics(displayMetrics)
                displayMetrics.heightPixels
            }
        }
        //var width = getScreenWidth(this).toDouble()
        //var height = getScreenHeight(this)

        val challengingItemList: ArrayList<ItemRecipeChallengeData> = arrayListOf()
        val challengingRVAdapter = MyChallengingRVAdapter(MyChallengingActivity(),challengingItemList)

        GlobalScope.launch(Dispatchers.IO) {

            val tokenDb = TokenDatabase.getTokenDatabase(this@MyChallengingActivity)
            token = tokenDb.tokenDao().getToken().token.toString()

            //통신
            retrofit.get_challengingrecipe(token)
                .enqueue(object : Callback<GetChallengingRecipeResponse> {
                    override fun onResponse(
                        call: Call<GetChallengingRecipeResponse>,
                        response: Response<GetChallengingRecipeResponse>
                    ) {
                        val result = response.body()
                        var i = 0

                        Log.d("통신", "${result}")
                        Log.d("통신","${token}")


                        while (true) {
                            if (challengingItemList.size == result?.data?.myChallenging?.size)
                                break
                            challengingItemList.add(
                                ItemRecipeChallengeData(
                                    result?.data?.myChallenging?.get(i)?.recipeId,
                                    result?.data?.myChallenging?.get(i)?.likes,
                                    result?.data?.myChallenging?.get(i)?.image,
                                    result?.data?.myChallenging?.get(i)?.name
                                )
                            )
                            i++
                        }
                        viewBinding.myTvv.text = challengingItemList.size.toString()
                        viewBinding.myRv.layoutManager = GridLayoutManager(this@MyChallengingActivity, 2)
                        val adapter =
                            MyChallengingRVAdapter(MyChallengingActivity(), challengingItemList)
                        viewBinding.myRv.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<GetChallengingRecipeResponse>, t: Throwable) {

                    }
                })

        }

    }
}