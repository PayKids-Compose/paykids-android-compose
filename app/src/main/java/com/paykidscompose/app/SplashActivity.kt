package com.paykidscompose.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import com.paykidscompose.app.databinding.ActivitySplashBinding
import com.paykidscompose.data.database.PayKidsPreference

class SplashActivity : ComponentActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater).also{
            setContentView(it.root)
        }
    }
    override fun onResume() {
        super.onResume()
        handler.postDelayed({
            Log.d(TAG, "초기화 작업 시작!")
            PayKidsPreference.init(applicationContext)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 1500L)
    }

    companion object {
        private const val TAG = "Splash Activity"
    }
}