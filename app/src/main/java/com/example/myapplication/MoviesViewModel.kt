package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.dataclasses.Results
import com.example.myapplication.dataclasses.ReviewResult

class MoviesViewModel(private val moviesRepo: MoviesRepo): ViewModel() {

    val nowPlayingList: LiveData<ArrayList<Results>> = moviesRepo.movieDetailsArrayList

    fun nowPlayingCall(){
        moviesRepo.nowPlayingCall()
    }

    val movieReviewResult: LiveData<ArrayList<ReviewResult>> = moviesRepo.reviewByMovieId
    fun reviewByMovieId(movieId:Int){
        moviesRepo.reviewsByMovieId(movieId)
    }

}