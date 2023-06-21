package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySplashScreeenBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    lateinit var binding: ActivitySplashScreeenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreeenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch {
            delay(1200)
            startActivity(Intent(this@SplashScreen,MainActivity::class.java))
        }
    }
}