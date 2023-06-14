package com.example.myapplication

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.MainActivity.Companion.API_KEY
import com.example.myapplication.dataclasses.Movie
import com.example.myapplication.dataclasses.Results
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepo(private val retrofitCall: RetrofitCall) {


    private val nowPlaying = retrofitCall.nowPlaying(API_KEY)
    var movieDetailsArrayList = MutableLiveData<ArrayList<Results>>()
    fun nowPlayingCall(): MutableLiveData<ArrayList<Results>>{

        nowPlaying.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if(response.isSuccessful){
                    val movie: Movie? = response.body()
                    movie?.let {
                        movieDetailsArrayList.value = it.results
                    }
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e("E: now playing -> ", t.message.toString())
            }
        })
        return movieDetailsArrayList
    }

}