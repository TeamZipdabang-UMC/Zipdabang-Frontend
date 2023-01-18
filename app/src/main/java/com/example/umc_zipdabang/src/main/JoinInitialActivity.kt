package com.example.umc_zipdabang.src.main

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.umc_zipdabang.databinding.ActivityJoinInitialBinding
import com.example.umc_zipdabang.src.main.KakaoRetrofitManager.Companion.kakaoRetrofit
import com.example.umc_zipdabang.src.main.model.KakaoLogin
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApi
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class JoinInitialActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityJoinInitialBinding
    private val TAG = this.javaClass.simpleName
    private lateinit var service: KakaoService
    private lateinit var retrofit: Retrofit

    // 구글 로그인을 위하여 필요한 변수들
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleEmail: String
    private lateinit var googleProfileImageUrl: String
    private var googleTokenId: String? = ""

    // 카카오 로그인을 위하여 필요한 변수들

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityJoinInitialBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

//        val jipDabangText: TextView = viewBinding.tvZipdabangTitle
//        val jipDabangFont: Typeface? = ResourcesCompat.getFont(this, R.font.poor_story)
//        jipDabangText.typeface = jipDabangFont

        firebaseAuth = FirebaseAuth.getInstance()
        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), ActivityResultCallback { result ->
                Log.e(TAG, "resultCode : ${result.resultCode}")
                Log.e(TAG, "result : $result")
                if (result.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    try {
                        task.getResult(ApiException::class.java)?.let { account ->
                            googleTokenId = account.idToken
                            if (googleTokenId != null && googleTokenId != "") {
                                val credential: AuthCredential =
                                    GoogleAuthProvider.getCredential(account.idToken, null)
                                firebaseAuth.signInWithCredential(credential)
                                    .addOnCompleteListener {
                                        if (firebaseAuth.currentUser != null) {
                                            val user: FirebaseUser = firebaseAuth.currentUser!!
                                            googleEmail = user.email.toString()
                                            googleProfileImageUrl = user.photoUrl.toString()
                                            Log.e(TAG, "google email : $googleEmail")
                                            Log.e(
                                                TAG,
                                                "google profile image url : ${googleProfileImageUrl}"
                                            )
                                            val googleSignInToken = account.idToken ?: ""
                                            if (googleSignInToken != "") {
                                                Log.e(TAG, "googleSignInToken : $googleSignInToken")
                                            } else {
                                                Log.e(TAG, "googleSignInToken이 null입니다.")
                                            }
                                        }
                                    }
                            }
                        } ?: throw Exception()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })

        // 구글로 시작하기 클릭 시 구글과 연동
        viewBinding.run {
            btnGoogleLogin.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("712573004341-2gj5fnig0bqvh1qj01fbbs0ki81ta84c.apps.googleusercontent.com")
                        .requestEmail()
                        .build()
                    val googleSignInClient = GoogleSignIn.getClient(this@JoinInitialActivity, gso)
                    val signInIntent: Intent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                }
            }
        }


        // '카카오 계정으로 시작하기' 클릭 시 카카오와 연동.
        // 카카오톡이 설치되어 있다면 카카오톡으로 이동, 그렇지 않다면 웹 상의 로그인 화면으로 이동
        viewBinding.btnKakaoLogin.setOnClickListener {
            // 만약 카카오톡이 깔려있다면,
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                // 카카오톡 이동하여 로그인
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    // 로그인 실패 부분
                    if (error != null) {
                        Log.e(TAG, "로그인 실패 $error")
                        // 사용자가 취소
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled ) {
                            return@loginWithKakaoTalk
                        }
                        // 다른 오류
                        else {
                            UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback) // 카카오 이메일 로그인
                        }
                    }
                    // 로그인 성공 부분
                    else if (token != null) {
                        // 카카오 닉네임, 카카오 이메일, 카카오 프로필 사진 URL 가져오기
                        UserApiClient.instance.me { user, error ->

                            val kakaoEmail = "${user?.kakaoAccount?.email}"
                            Log.e(TAG, "kakao email : $kakaoEmail")

                            val kakaoProfileImageUrl = "${user?.kakaoAccount?.profile?.profileImageUrl}"
                            Log.e(TAG, "kakao profile image url : ${kakaoProfileImageUrl}")

                            val kakaoUserJson = JSONObject()
                            kakaoUserJson.put("userEmail", "${kakaoEmail}")
                            kakaoUserJson.put("userProfile", "${kakaoProfileImageUrl}")



                        }
                        Log.e(TAG, "로그인 성공 ${token.accessToken}")
                    }
                }
            } else {
                // 카카오 이메일 로그인
                UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
            }
        }
    }

    // 카카오 메시지 콜백 변수
    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "로그인 실패 $error")

        } else if (token != null) {
            Log.e(TAG, "로그인 성공 ${token.accessToken}")
            UserApiClient.instance.me { user, error ->
                if (user?.kakaoAccount?.email != null) {
                    val kakaoEmail = "${user?.kakaoAccount?.email}"
                    Log.e(TAG, "kakao email : $kakaoEmail")
                }
                val kakaoNickname = "${user?.kakaoAccount?.profile?.nickname}"
                val kakaoProfileImageUrl = "${user?.kakaoAccount?.profile?.profileImageUrl}"
                Log.e(TAG, "kakao nickname : $kakaoNickname")
                Log.e(TAG, "kakao profile image url : ${kakaoProfileImageUrl}")
//                val service = kakaoRetrofit.create(KakaoService::class.java)
//                val call = service.getKakaoLoginStatus()
//                call.enqueue(object: Callback<KakaoLogin> {
//                    override fun onFailure(call: Call<KakaoLogin>, t: Throwable) {
//                        Log.d("JoinInitialActivity", "${t.message}")
//                    }
//
//                    override fun onResponse(
//                        call: Call<KakaoLogin>,
//                        response: Response<KakaoLogin>
//                    ) {
//                        if (response.code() == 200) {
//                            val kakaoLoginResponse = response.body()
//                            Log.d("JoinInitialActivity", "${kakaoLoginResponse}")
//                        }
//                    }
//                })
            }
            Log.e(TAG, "로그인 성공 ${token.accessToken}")
            // 로그인 성공 시 다음 화면으로 넘어가기
            // startActivity(intent)
        }
    }
}

