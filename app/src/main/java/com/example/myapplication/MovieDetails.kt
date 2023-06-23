package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMovieDetailsPageBinding
import com.example.myapplication.dataclasses.Results
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetails : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailsPageBinding
    private var movieResult: Results? = null
    var movieId: Int? = null
    val viewModel: MoviesViewModel by viewModel()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        //data setting with Results object
//        val movieDetailList = intent.getSerializableExtra("Movie Detail List") as Results
//        movieResult = movieDetailList
//        movieId = movieResult!!.id
//
//        supportActionBar?.title = "${movieResult!!.originalTitle}"
//
//        //credits data
//        viewModel.creditsByMovieId(movieId!!)
//        // reviews data
//        viewModel.reviewByMovieId(movieId!!)
//        //similar movies data
//        viewModel.similarMoviesByMovieId(movieId!!)
//
//        val urlForPoster = "${MainActivity.BACKDROP_BASE_URL}${movieResult?.backdropPath}"
//        Glide.with(this)
//            .load(urlForPoster)
//            .centerCrop()
//            .into(binding.backdropPoster)
//        binding.mdTitle.text = movieResult?.originalTitle
//        binding.rating.text = "Ratings: ${movieResult?.voteAverage.toString()}"
//        binding.synopsisTextArea.text = movieResult?.overview
//
//        //cast
//        val cast = ArrayList<Cast>()
//        viewModel.cast.observe(this){
//            cast.clear()
//            cast.addAll(it)
//            val castRecyclerView = binding.castRecyclerView
//            castRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
//            val castAdapter = CastRecyclerViewAdapter(cast,this)
//            castRecyclerView.adapter = castAdapter
//        }
//        //crew
//        val crew = ArrayList<Crew>()
//        viewModel.crew.observe(this){
//            crew.clear()
//            it.forEach {
//                if(it.job!! == "Director" ||
//                    it.job!! == "Producer" ||
//                    it.job!! == "Writer"){
//                    crew.add(it)
//                }
//            }
//            val crewRecyclerView = binding.crewRecyclerView
//            crewRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
//            val crewAdapter = CrewRecyclerViewAdapter(crew,this)
//            crewRecyclerView.adapter = crewAdapter
//        }
//
//        val reviewsResult= ArrayList<ReviewResult>()
//        viewModel.movieReviewResult.observe(this){
//            reviewsResult.clear()
//            reviewsResult.addAll(it)
//            val reviewsRV = binding.reviewsRecyclerView
//            reviewsRV.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
//            val reviewsAdapter = ReviewsRecyclerViewAdapter(reviewsResult)
//            reviewsRV.adapter = reviewsAdapter
//        }
//
//        val similarMovies = ArrayList<SimilarMovieResults>()
//        viewModel.similarMovies.observe(this){
//            similarMovies.clear()
//            similarMovies.addAll(it)
//            val similarMovieRecyclerView = binding.similarMoviesRecyclerview
//            similarMovieRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
//            val similarMovieAdapter = SimilarMoviesRecyclerViewAdapter(similarMovies,this)
//            similarMovieRecyclerView.adapter = similarMovieAdapter
//        }
//
//        binding.favoriteButton.setOnCheckedChangeListener{ checkBox, isChecked ->
//
//            if(isChecked){
//                checkBox.buttonDrawable = ContextCompat.getDrawable(this,R.drawable.filled_favorite_48px)
//                val addFavourite = AddFavourite("movie",movieId!!,true)
//                viewModel.addFavoriteMovie(addFavourite)
//            }else{
//                checkBox.buttonDrawable = ContextCompat.getDrawable(this,R.drawable.favorite_48px)
//                val addFavourite = AddFavourite("movie",movieId!!,false)
//                viewModel.addFavoriteMovie(addFavourite)
//            }
//
//        }
//        viewModel.addFavStatus.observe(this){
//            if(it!=null){ viewModel.getFavMovie()}
//        }

    }

}