package com.example.myapplication

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.dataclasses.Results
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class MainActivity : AppCompatActivity() {

    companion object{
        const val API_KEY = "05679fcbecd828bb8ed74f83e6da99c9"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780"
    }

    private lateinit var binding:ActivityMainBinding
    var nowPlayingMovieList =  ArrayList<Results>()
    var upcomingMoviesList =  ArrayList<Results>()

    val viewModel: MoviesViewModel by viewModel()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "CubeMovies"

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        if(viewModel.getSessionId().isNullOrEmpty()){
            viewModel.requestToken()
            viewModel.tokenResponse.observe(this) {
                println("act $it")

                AlertDialog.Builder(this)
                    .setTitle("One Time Auth")
                    .setMessage("enter 'asinnha' as username and 'akshat16' as password. Remember to close the web view by clicking on the cross. Click the close button after login and after the session is approved.")
                    .setPositiveButton("OK") { d, _ ->
                        val url = "https://www.themoviedb.org/authenticate/$it"
                        val customTabsIntentBuilder = CustomTabsIntent.Builder()
                        customTabsIntentBuilder
                            .build()
                            .launchUrl(this, Uri.parse(url))
                            GlobalScope.launch{
                                while (viewModel.getSessionId().isNullOrEmpty()) {
                                    delay(200)
                                    viewModel.tokenResponse.value?.let {token->
                                        viewModel.createSessionId(
                                            token
                                        )
                                    }
                                }
                            }
                    }
                    .setNegativeButton("CLOSE"){d,_->
                        d.dismiss()
                    }
                    .create().show()

            }
        }else{
            println("sid -> "+viewModel.getSessionId())
        }

        //recycler view init
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val recyclerViewAdapter = RecyclerViewAdapter(nowPlayingMovieList,this)
        recyclerView.adapter = recyclerViewAdapter

        val upcomingMoviesRecyclerView = binding.upcomingRecyclerView
        upcomingMoviesRecyclerView.layoutManager= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val upcomingRecyclerViewAdapter = RecyclerViewAdapter(upcomingMoviesList,this)
        upcomingMoviesRecyclerView.adapter = upcomingRecyclerViewAdapter

        viewModel.nowPlayingCall()
        viewModel.upcomingMovies()
        //observing the now playing arraylist
        viewModel.nowPlayingList.observe(this){
            if(it == null){
                Toast.makeText(this@MainActivity,"loading",Toast.LENGTH_LONG).show()
            }else{
                nowPlayingMovieList.addAll(it)
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }
        viewModel.upcomingMovies.observe(this){
            upcomingMoviesList.addAll(it)
            upcomingRecyclerViewAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }

}