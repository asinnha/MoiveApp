package com.example.myapplication

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class MainActivity : AppCompatActivity() {

    companion object{
        const val ACCESS_TOKEN_AUTH = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwNTY3OWZjYmVjZDgyOGJiOGVkNzRmODNlNmRhOTljOSIsInN1YiI6IjY0ODAzMDU1OTkyNTljMDBjNWIyOGFlOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.q_N2vKaZiq1fXSyghJo-O1X2T9g4VOw8B0XblV8BOFA"
        const val API_KEY = "05679fcbecd828bb8ed74f83e6da99c9"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780"
    }

    private lateinit var binding:ActivityMainBinding
//    var nowPlayingMovieList =  ArrayList<Results>()
//    var upcomingMoviesList =  ArrayList<Results>()
//    var favoriteMoviesList =  ArrayList<Results>()
//
    val viewModel: MoviesViewModel by viewModel()
//    lateinit var navController: NavController

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = resources.getColor(com.google.android.material.R.color.primary_dark_material_dark)

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        //one time auth for session id
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
        viewModel.nowPlayingCall()
        viewModel.upcomingMovies()
        viewModel.getFavMovie()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }

}