package com.example.myapplication

import com.example.myapplication.dataclasses.Credits
import com.example.myapplication.dataclasses.Movie
import com.example.myapplication.dataclasses.MovieReview
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitCall {

    @GET("movie/now_playing?language=en-US&page=1&region=IN")
    fun nowPlaying(
        @Query("api_key") api_key: String
    ): Call<Movie>

    @GET("movie/{movie_id}/reviews?language=en-US&page=1")
    fun reviewsByMovieId(
        @Path("movie_id") movie_id:Int
    ):Call<MovieReview>

    @GET("movie/{movie_id}/credits?language=en-US")
    fun getCredits(
        @Path("movie_id") movie_id: Int
    ):Call<Credits>
}