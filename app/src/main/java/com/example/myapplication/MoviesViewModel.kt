package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.dataclasses.Results

class MoviesViewModel(private val moviesRepo: MoviesRepo): ViewModel() {

    val nowPlayingList: LiveData<ArrayList<Results>> = moviesRepo.movieDetailsArrayList

    fun nowPlayingCall(){
        moviesRepo.nowPlayingCall()
    }

}