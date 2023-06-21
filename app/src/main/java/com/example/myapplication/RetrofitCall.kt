package com.example.myapplication

import com.example.myapplication.dataclasses.CreateRequestToken
import com.example.myapplication.dataclasses.Credits
import com.example.myapplication.dataclasses.Movie
import com.example.myapplication.dataclasses.MovieReview
import com.example.myapplication.dataclasses.SessionRequestBody
import com.example.myapplication.dataclasses.SessionResponse
import com.example.myapplication.dataclasses.SimilarMovies
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitCall {

    @GET("authentication/token/new")
    fun getRequestToken(
        @Query("api_key") api_key: String
    ): Call<CreateRequestToken>

    @POST("authentication/session/new")
    fun createSessionId(
        @Query("api_key") apiKey: String,
        @Body body: SessionRequestBody
    ): Call<SessionResponse>

    @GET("movie/now_playing?language=en-US&page=1&region=IN")
    fun nowPlaying(
        @Query("api_key") api_key: String
    ): Call<Movie>

    @GET("movie/upcoming?language=en-US&page=1&region=in")
    fun upcomingMovies(
    @Query("api_key") api_key: String
    ): Call<Movie>

    @GET("movie/{movie_id}/reviews?language=en-US&page=1")
    fun reviewsByMovieId(
        @Path("movie_id") movie_id:Int,
        @Query("api_key") api_key: String
    ):Call<MovieReview>

    @GET("movie/{movie_id}/credits?language=en-US")
    fun creditsByMovieId(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ):Call<Credits>

    @GET("movie/{movie_id}/similar?language=en-US&page=1")
    fun similarMoviesByMovieId(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ):Call<SimilarMovies>
}