package com.example.myapplication

import com.example.myapplication.dataclasses.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitCall {

    @GET("movie/{id}")
    fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): Call<Movie>

    @GET("movie/now_playing?language=en-US&page=1&region=IN")
    fun nowPlaying(
        @Query("api_key") api_key: String
    ): Call<Movie>

}