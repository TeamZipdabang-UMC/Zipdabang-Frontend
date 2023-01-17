package com.example.umc_zipdabang.src.signup

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.umc_zipdabang.databinding.ActivitySignupCameraagreeBinding

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
            val REQUEST_IMAGE_CAPTURE = 1
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE) }
            }
        }

        viewBinding.signupOkaybtn.setOnClickListener {
            val intent = Intent(this, SignupFirstActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        viewBinding.signupBackbtn.setOnClickListener {
            val intent = Intent(this, SignupServiceagreeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }
}