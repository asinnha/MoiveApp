package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.dataclasses.Cast
import com.example.myapplication.dataclasses.Crew
import com.example.myapplication.dataclasses.Results
import com.example.myapplication.dataclasses.ReviewResult
import com.example.myapplication.dataclasses.SimilarMovieResults

class MoviesViewModel(private val moviesRepo: MoviesRepo): ViewModel() {

    val nowPlayingList: LiveData<ArrayList<Results>> = moviesRepo.movieDetailsArrayList
    fun nowPlayingCall(){
        moviesRepo.nowPlayingCall()
    }

    val movieReviewResult: LiveData<ArrayList<ReviewResult>> = moviesRepo.reviewByMovieId
    fun reviewByMovieId(movieId:Int){
        moviesRepo.reviewsByMovieId(movieId)
    }

    val cast: LiveData<ArrayList<Cast>> = moviesRepo.castCredits
    val crew: LiveData<ArrayList<Crew>> = moviesRepo.crewCredits
    fun creditsByMovieId(movieId: Int){
        moviesRepo.creditsByMovieId(movieId)
    }

    val similarMovies: LiveData<ArrayList<SimilarMovieResults>> = moviesRepo.similarMovies
    fun similarMoviesByMovieId(movieId: Int){
        moviesRepo.similarMoviesByMovieId(movieId)
    }
}