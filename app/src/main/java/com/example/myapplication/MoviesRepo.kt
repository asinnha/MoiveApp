package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.MainActivity.Companion.ACCESS_TOKEN_AUTH
import com.example.myapplication.MainActivity.Companion.API_KEY
import com.example.myapplication.dataclasses.AddFavourite
import com.example.myapplication.dataclasses.Cast
import com.example.myapplication.dataclasses.CreateRequestToken
import com.example.myapplication.dataclasses.Credits
import com.example.myapplication.dataclasses.Crew
import com.example.myapplication.dataclasses.FavoriteResponse
import com.example.myapplication.dataclasses.Movie
import com.example.myapplication.dataclasses.MovieReview
import com.example.myapplication.dataclasses.Results
import com.example.myapplication.dataclasses.ReviewResult
import com.example.myapplication.dataclasses.SessionRequestBody
import com.example.myapplication.dataclasses.SessionResponse
import com.example.myapplication.dataclasses.SimilarMovies
import com.example.myapplication.roomdatabase.NowShowingDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepo(private val retrofitCall: RetrofitCall, val context: Context,val nowShowingDatabase: NowShowingDatabase) {

    val token =MutableLiveData<String>()
    suspend fun requestToken() {

        val requestToken = retrofitCall.getRequestToken(API_KEY)
        requestToken.enqueue(object:Callback<CreateRequestToken>{
            override fun onResponse(
                call: Call<CreateRequestToken>,
                response: Response<CreateRequestToken>
            ) {
                if(response.isSuccessful){
                    val results = response.body()
                    token.value = results?.requestToken.toString()
                    println("repo ${token.value}")
                    Log.i("Request Token","Success")
                }
            }

            override fun onFailure(call: Call<CreateRequestToken>, t: Throwable) {
                Log.e("Request Token",t.message.toString())
            }

        })
    }

    private val sharedPref: SharedPreferences = context.getSharedPreferences("Session Id",Context.MODE_PRIVATE)
    fun saveSessionId(sessionId:String){
        sharedPref.edit()
            .putString("Session Id", sessionId)
            .apply()
    }

    fun getSessionId(): String? {
        return sharedPref.getString("Session Id","")
    }


    private val favoriteSharedPreferences: SharedPreferences = context.getSharedPreferences("Favorite List",Context.MODE_PRIVATE)
    val favoriteList: ArrayList<AddFavourite> = ArrayList()
    fun saveFavorites(){
        val arrayListToJson = Gson().toJson(favoriteList)
        favoriteSharedPreferences.edit()
            .putString("Save Favorite List Key",arrayListToJson)
            .apply()
    }
    fun getFavorites(): ArrayList<AddFavourite>? {
        val arrayToJson = favoriteSharedPreferences.getString("Save Favorite List Key", " ")
        val type = object : TypeToken<ArrayList<AddFavourite>>() {}.type
        return Gson().fromJson(arrayToJson, type)
    }


    var movieDetailsArrayList = MutableLiveData<ArrayList<Results>>()
    private val movieResultsDao = nowShowingDatabase.movieResultDao()
    suspend fun insertNowPlaying(results:ArrayList<Results>){
        movieResultsDao.deleteAll()
        movieResultsDao.insertAll(results)
    }
    suspend fun getNowPlaying(): ArrayList<Results> {
        return movieResultsDao.getAll() as ArrayList<Results>

    }

    suspend fun orderOldest(): ArrayList<Results>{
        return movieResultsDao.orderOldest() as ArrayList<Results>
    }

    suspend fun orderLatest(): ArrayList<Results>{
        return movieResultsDao.orderLatest() as ArrayList<Results>
    }

    private val nowPlaying = retrofitCall.nowPlaying(API_KEY)


    suspend fun nowPlayingCall(){

        nowPlaying.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if(response.isSuccessful){
                    val movie: Movie? = response.body()
                    movie?.let {
//                        CoroutineScope(Dispatchers.IO).launch {
//                            insertNowPlaying(it.results)
//                            movieDetailsArrayList.value = getNowPlaying() as ArrayList<Results>
//                        }
                        movieDetailsArrayList.value = it.results
                        Log.i("Now Playing","SUCCESS")
                    }
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e("Now Playing", t.message.toString())
            }
        })

    }

    val upcomingMovieList = MutableLiveData<ArrayList<Results>>()
    suspend fun upcomingMovies(){
        val upcomingMovies = retrofitCall.upcomingMovies(API_KEY)
        upcomingMovies.enqueue(object :Callback<Movie>{
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if(response.isSuccessful){
                    val movie: Movie? = response.body()
                    movie?.let {
                        upcomingMovieList.value = it.results
                        Log.i("Upcoming Movies","SUCCESS")
                    }
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e("Upcoming Movies",t.message.toString())
            }

        })
    }

    var reviewByMovieId = MutableLiveData<ArrayList<ReviewResult>>()
    suspend fun reviewsByMovieId(movieId: Int) {

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
    suspend fun creditsByMovieId(movieId: Int){

        val credits = retrofitCall.creditsByMovieId(
            movieId, API_KEY
        )
        credits.enqueue(object :Callback<Credits>{
            override fun onResponse(call: Call<Credits>, response: Response<Credits>) {
                if(response.isSuccessful){
                    val mCredits = response.body()
                    mCredits?.let {
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

    val similarMovies = MutableLiveData<ArrayList<Results>>()
    suspend fun similarMoviesByMovieId(movieId: Int){

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

    val sessionId = MutableLiveData<String>()
    fun createSessionId(requestToken: String) {
        val createSessionID = retrofitCall.createSessionId(API_KEY, SessionRequestBody(requestToken = requestToken))
        createSessionID.enqueue(object : Callback<SessionResponse>{
            override fun onResponse(
                call: Call<SessionResponse>,
                response: Response<SessionResponse>
            ) {
                if(response.isSuccessful){
                    val id = response.body()
                    id?.let {
                        sessionId.value = it.sessionId
                        saveSessionId(it.sessionId)
                        Log.i("Session Id","SUCCESS")
                    }
                }
            }

            override fun onFailure(call: Call<SessionResponse>, t: Throwable) {
                Log.i("Session Id",t.message.toString())
            }

        })
    }

    val addFavStatusMessage= MutableLiveData<String?>()
    suspend fun addFavoriteMovie(addFav: AddFavourite) {
        val addFavPost = retrofitCall.addFavoriteMovie(ACCESS_TOKEN_AUTH,addFav)
        addFavPost.enqueue(object : Callback<FavoriteResponse> {
            override fun onResponse(call: Call<FavoriteResponse>, response: Response<FavoriteResponse>) {
                if (response.isSuccessful) {
                    val statusCheck = response.body()
                    if (statusCheck?.success == true) {
                        addFavStatusMessage.value = statusCheck.statusMessage
                        println("repo ${addFavStatusMessage.value}")
                        Log.i("Add Fav", "SUCCESS")
                    }
                } else {
                    println("Response is not successful. Code: ${response.code()}, Message: ${response.message()}")
                    // Handle the error response accordingly
                }
            }

            override fun onFailure(call: Call<FavoriteResponse>, t: Throwable) {
                Log.e("Add Fav", "Error: ${t.message}")
                // Handle failure accordingly
            }
        })

    }

    val getFavMovie = MutableLiveData<ArrayList<Results>>()
    fun getFavoriteMovie(sessionId: String){
        val getFavoriteMovie = retrofitCall.getFavoriteMovie(sessionId, API_KEY)
        getFavoriteMovie.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if(response.isSuccessful){
                    val movie: Movie? = response.body()
                    movie?.let {
                        getFavMovie.value = it.results
                        Log.i("Fav Movie","SUCCESS")
                    }
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                Log.e("Fav Movie", t.message.toString())
            }
        })
    }


}