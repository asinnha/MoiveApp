package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dataclasses.Cast
import com.example.myapplication.dataclasses.Crew
import com.example.myapplication.dataclasses.Results
import com.example.myapplication.dataclasses.ReviewResult
import com.example.myapplication.dataclasses.SimilarMovieResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(private val moviesRepo: MoviesRepo): ViewModel() {

    fun getSessionId(): String? {
        return moviesRepo.getSessionId()
    }

    val sessionId: LiveData<String> = moviesRepo.sessionId
    var tokenResponse:LiveData<String> = moviesRepo.token
    fun requestToken(){
        viewModelScope.launch(Dispatchers.IO){
            moviesRepo.requestToken()
        }
    }

    val nowPlayingList: LiveData<ArrayList<Results>> = moviesRepo.movieDetailsArrayList
    fun nowPlayingCall(){
        viewModelScope.launch(Dispatchers.IO){ moviesRepo.nowPlayingCall() }
    }

    val upcomingMovies:LiveData<ArrayList<Results>> = moviesRepo.upcomingMovieList
    fun upcomingMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepo.upcomingMovies()
        }
    }

    val movieReviewResult: LiveData<ArrayList<ReviewResult>> = moviesRepo.reviewByMovieId
    fun reviewByMovieId(movieId:Int){
        viewModelScope.launch(Dispatchers.IO){ moviesRepo.reviewsByMovieId(movieId) }
    }

    val cast: LiveData<ArrayList<Cast>> = moviesRepo.castCredits
    val crew: LiveData<ArrayList<Crew>> = moviesRepo.crewCredits
    fun creditsByMovieId(movieId: Int){
        viewModelScope.launch(Dispatchers.IO) { moviesRepo.creditsByMovieId(movieId) }
    }

    val similarMovies: LiveData<ArrayList<SimilarMovieResults>> = moviesRepo.similarMovies
    fun similarMoviesByMovieId(movieId: Int){
        viewModelScope.launch(Dispatchers.IO) { moviesRepo.similarMoviesByMovieId(movieId) }
    }

    fun createSessionId(requestToken: String) {
        moviesRepo.createSessionId(requestToken)
    }
}