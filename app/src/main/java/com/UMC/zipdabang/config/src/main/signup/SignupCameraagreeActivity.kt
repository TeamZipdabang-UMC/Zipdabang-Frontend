package com.UMC.zipdabang.config.src.main.signup

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.UMC.zipdabang.databinding.ActivitySignupCameraagreeBinding

class SignupCameraagreeActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignupCameraagreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding= ActivitySignupCameraagreeBinding.inflate((layoutInflater))
        setContentView(viewBinding.root)

        val sharedPreference = getSharedPreferences("signup",0)
        val editor = sharedPreference.edit()

        val cameraPermissionCheck = ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.CAMERA)

        if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) { // 권한이 없는 경우
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.CAMERA), 1000)
        } else { //권한이 있는 경우

        }

        val intent_email = intent.getStringExtra("email").toString()
        Log.d("카메라 스트링", "${intent_email}")

        viewBinding.signupBackbtn.setOnClickListener {
            val intent = Intent(this, SignupServiceagreeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        viewBinding.signupOkaybtn.setOnClickListener {
            val intent = Intent(this, SignupFirstActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.putExtra("email", intent_email)
            finish()
            startActivity(intent)
        }
    }
}