package com.example.myapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.MainActivity.Companion.API_KEY
import com.example.myapplication.dataclasses.Cast
import com.example.myapplication.dataclasses.Credits
import com.example.myapplication.dataclasses.Crew
import com.example.myapplication.dataclasses.Movie
import com.example.myapplication.dataclasses.MovieReview
import com.example.myapplication.dataclasses.Results
import com.example.myapplication.dataclasses.ReviewResult
import com.example.myapplication.dataclasses.SimilarMovieResults
import com.example.myapplication.dataclasses.SimilarMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepo(private val retrofitCall: RetrofitCall) {


    private val nowPlaying = retrofitCall.nowPlaying(API_KEY)

    var movieDetailsArrayList = MutableLiveData<ArrayList<Results>>()
    fun nowPlayingCall(){

        nowPlaying.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if(response.isSuccessful){
                    val movie: Movie? = response.body()
                    movie?.let {
                        movieDetailsArrayList.value = it.results
                        Log.i("movie details","successful")
                    }
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e("E: now playing -> ", t.message.toString())
            }
        })

    }

    var reviewByMovieId = MutableLiveData<ArrayList<ReviewResult>>()
    fun reviewsByMovieId(movieId: Int) {

        val review = retrofitCall.reviewsByMovieId(movieId, API_KEY)
        review.enqueue(object : Callback<MovieReview> {
            override fun onResponse(call: Call<MovieReview>, response: Response<MovieReview>) {
                if (response.isSuccessful) {
                    val reviewDetails: MovieReview? = response.body()
                    reviewDetails?.let { movieReview ->
                        reviewByMovieId.value = movieReview.results
                        Log.i("review response success"," SUCCESS")
                    }
                }
            }

            override fun onFailure(call: Call<MovieReview>, t: Throwable) {
                Log.e("review response", t.message.toString())
            }
        })
    }

    val castCredits = MutableLiveData<ArrayList<Cast>>()
    val crewCredits = MutableLiveData<ArrayList<Crew>>()
    fun creditsByMovieId(movieId: Int){

        val credits = retrofitCall.creditsByMovieId(
            movieId, API_KEY
        )
        credits.enqueue(object :Callback<Credits>{
            override fun onResponse(call: Call<Credits>, response: Response<Credits>) {
                if(response.isSuccessful){
                    val credits = response.body()
                    credits?.let {
                        castCredits.value = it.cast
                        crewCredits.value = it.crew
                    }
                    Log.i("Credits Call","SUCCESS")
                }
            }

            override fun onFailure(call: Call<Credits>, t: Throwable) {
                Log.i("Credits Call","Failed\t ${t.message.toString()}")
            }

        })

    }

    val similarMovies = MutableLiveData<ArrayList<SimilarMovieResults>>()
    fun similarMoviesByMovieId(movieId: Int){

        val callSimilarMovies = retrofitCall.similarMoviesByMovieId(movieId, API_KEY)
        callSimilarMovies.enqueue(object: Callback<SimilarMovies>{
            override fun onResponse(call: Call<SimilarMovies>, response: Response<SimilarMovies>) {
                if(response.isSuccessful){
                    val movies = response.body()
                    movies?.let {
                        similarMovies.value = it.results
                    }
                    Log.i("Similar Movies","SUCCESS")
                }
            }

            override fun onFailure(call: Call<SimilarMovies>, t: Throwable) {
                Log.e("Similar Movies","FAILED")
            }

        })
    }


}