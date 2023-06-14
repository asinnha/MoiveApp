package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.dataclasses.Results
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class MainActivity : AppCompatActivity() {

    companion object{
        const val API_KEY = "05679fcbecd828bb8ed74f83e6da99c9"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }

    private lateinit var binding:ActivityMainBinding
    var nowPlayingMovieList =  ArrayList<Results>()

    val viewModel: MoviesViewModel by viewModel()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //to change the status bar color
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor = resources.getColor(com.google.android.material.R.color.design_default_color_on_primary,null)
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        //koin init
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        //recycler view init
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(this,2)
        val recyclerViewAdapter = RecyclerViewAdapter(nowPlayingMovieList,this)
        recyclerView.adapter = recyclerViewAdapter

        viewModel.nowPlayingCall()
        //observing the now playing arraylist
        viewModel.nowPlayingList.observe(this){
            if(it == null){
                Toast.makeText(this@MainActivity,"loading",Toast.LENGTH_LONG).show()
            }else{
                nowPlayingMovieList.addAll(it)
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }

}