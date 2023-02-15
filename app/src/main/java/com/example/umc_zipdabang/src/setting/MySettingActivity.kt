package com.example.umc_zipdabang.src.setting
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.umc_zipdabang.config.src.main.Home.HomeMainActivity
import com.example.umc_zipdabang.config.src.main.Jip.src.main.roomDb.TokenDatabase
import com.example.umc_zipdabang.config.src.main.SocialLogin.InitialActivity
import com.example.umc_zipdabang.databinding.ActivityMyQuitBinding
import com.example.umc_zipdabang.databinding.ActivityMySettingBinding
import com.example.umc_zipdabang.src.my.APIS_My
import com.example.umc_zipdabang.src.my.GetNicknameEmailResponse
import com.example.umc_zipdabang.src.my.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MySettingActivity :AppCompatActivity(){
    private lateinit var viewBinding: ActivityMySettingBinding

    private val retrofit = RetrofitInstance.getInstance().create(APIS_My::class.java)
    var token: String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMySettingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        var token1: String? = null

        //닉네임 & 이메일 받아오기
        GlobalScope.launch(Dispatchers.IO) {

            val tokenDb = TokenDatabase.getTokenDatabase(this@MySettingActivity)
            token = tokenDb.tokenDao().getToken().token.toString()

            retrofit.get_nickname_email(token).enqueue(object : Callback<GetNicknameEmailResponse>{
                override fun onResponse(
                    call: Call<GetNicknameEmailResponse>,
                    response: Response<GetNicknameEmailResponse>
                ) {
                    Log.d("통신","성공")
                    val result = response.body()
                    val data = result?.data
                    Log.d("통신","${result}")

                    viewBinding.etNickname.setText(data?.nickname)
                    viewBinding.tvEmail.setText(data?.email)
                }

                override fun onFailure(call: Call<GetNicknameEmailResponse>, t: Throwable) {
                    Log.d("통신","실패")
                }

            })
        }

        val sharedPreference = getSharedPreferences("signup", 0)
        val editor = sharedPreference.edit()
        //editor.clear()
        //editor.apply()
        var nickname_sp = sharedPreference.getString("nickname", "")
        var email_sp = sharedPreference.getString("email", "")
        Log.d("닉네임","${nickname_sp}")
        Log.d("이메일","email_sp")


        //공지사항 이동
        viewBinding.ivToNotice.setOnClickListener {
            Log.d("이동", "?")

            val intent = Intent(this, MyNoticeActivity::class.java)
            startActivity(intent)

        }
        //이용약관 이동
        viewBinding.ivToTos.setOnClickListener {

            val intent = Intent(this, ActvityTos::class.java)
            startActivity(intent)

        }

        viewBinding.ivToQuestion.setOnClickListener {

            val intent = Intent(this, FirstQuestionActivity::class.java)
            startActivity(intent)

        }

        viewBinding.ivToFAQ.setOnClickListener {

            val intent = Intent(this, MyFAQActivity::class.java)
            startActivity(intent)

        }
        val dialog = Dialog_nickname(this@MySettingActivity)


        viewBinding.tvQuit.setOnClickListener {

            val intent = Intent(this, QuitActivity::class.java)


            startActivity(intent)

        }

        viewBinding.tvLogout.setOnClickListener {

            val intent= Intent(this, InitialActivity::class.java)


            val tokenDb = TokenDatabase.getTokenDatabase(this)
            GlobalScope.launch(Dispatchers.IO) {
                tokenDb.tokenDao().deleteAll()
            }
            startActivity(intent)


        }

        GlobalScope.launch(Dispatchers.IO) {


            val tokenDb = TokenDatabase.getTokenDatabase(this@MySettingActivity)
            token1 = tokenDb.tokenDao().getToken().token.toString()


            //      var token1  =
            //          "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEsInVzZXJFbWFpbCI6ImVtYWlsQG5hdmVyLmNvbSIsImlhdCI6MTY3NDYyNDA5OCwiZXhwIjoxNjc3MjE2MDk4LCJzdWIiOiJ1c2VySW5mbyJ9.ZEl388-pGKg02xaVO5fq3nVGBtn0QfgTiWEeX3laRl0"
            val service = Retrofit.retrofit.create(RetrofitNicknameService::class.java)
            //닉네임 변경
            viewBinding.etNickname.addTextChangedListener {
                if (viewBinding.etNickname.text.length >= 2) {


                    viewBinding.btSave.setOnClickListener {

                        if (validNickname()) {

                            dialog.showDialog()
                            val nickname = NickName_Patch(viewBinding.etNickname.text.toString())
                            dialog.setOnClickListener(object : Dialog_nickname.ButtonClickListener {
                                override fun onClicked() {
                                    service.change_nickname(token1, nickname).enqueue(object :
                                        retrofit2.Callback<NickName_Respsonse> {
                                        //api 호출
                                        override fun onResponse(
                                            call: Call<NickName_Respsonse>,
                                            response: Response<NickName_Respsonse>
                                        ) {

                                            val result = response.body()
                                            if (result?.success == true) {
                                                custom_toast.createToast(
                                                    applicationContext,
                                                    "닉네임이 변경되었습니다."
                                                )?.show()
                                                //닉네임 sharedprefenence 변경!
                                                val edit_nick=viewBinding.etNickname.text.toString()
                                                viewBinding.ivWarning.visibility = View.GONE
                                                viewBinding.tvWarning.visibility = View.GONE
                                                editor.putString("nickname",edit_nick)
                                                editor.apply()
                                                Log.d("닉네임","${edit_nick}")


                                            } else {
                                                custom_toast.createToast(
                                                    applicationContext,
                                                    "이미 사용 중인 닉네임입니다."
                                                )?.show()
                                            }


                                        }

                                        override fun onFailure(
                                            call: Call<NickName_Respsonse>,
                                            t: Throwable
                                        ) {
                                        }


                                    })
                                }
                            })


                        } else {

                            viewBinding.ivWarning.visibility = View.VISIBLE
                            viewBinding.tvWarning.visibility = View.VISIBLE

                        }

                    }
                }


            }

            viewBinding.ivClear.setOnClickListener {

                viewBinding.etNickname.text.clear()
            }

            viewBinding.myBackbtn.setOnClickListener {
                finish()
            }
        }
    }
        private  fun validNickname():Boolean{
            val value: String = viewBinding.etNickname?.text.toString().trim()
            val nicknamepattern = "^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]{2,10}$"
            return if(!value.matches(nicknamepattern.toRegex())){
                false
            } else{
                return true
            }
        }
}