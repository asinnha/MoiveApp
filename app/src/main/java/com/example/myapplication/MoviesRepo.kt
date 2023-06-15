package com.example.myapplication

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.MainActivity.Companion.API_KEY
import com.example.myapplication.dataclasses.Movie
import com.example.myapplication.dataclasses.MovieReview
import com.example.myapplication.dataclasses.Results
import com.example.myapplication.dataclasses.ReviewResult
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

    var reviewByMovieId = MutableLiveData<ArrayList<ReviewResult>>()
    fun reviewsByMovieId(movieId: Int): MutableLiveData<ArrayList<ReviewResult>> {

        val review = retrofitCall.reviewsByMovieId(movieId)
        review.enqueue(object : Callback<MovieReview> {
            override fun onResponse(call: Call<MovieReview>, response: Response<MovieReview>) {
                if (response.isSuccessful) {
                    val reviewDetails: MovieReview? = response.body()
                    reviewDetails?.let { movieReview ->
                        reviewByMovieId.value = movieReview.results
                    }
                }
            }

            override fun onFailure(call: Call<MovieReview>, t: Throwable) {
                Log.e("E: review response -> ", t.message.toString())
            }
        })
        return reviewByMovieId
    }

}