package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.RecyclerViews.CastRecyclerViewAdapter
import com.example.myapplication.RecyclerViews.CrewRecyclerViewAdapter
import com.example.myapplication.RecyclerViews.ReviewsRecyclerViewAdapter
import com.example.myapplication.RecyclerViews.SimilarMoviesRecyclerViewAdapter
import com.example.myapplication.databinding.FragmentMovieDeatilBinding
import com.example.myapplication.dataclasses.AddFavourite
import com.example.myapplication.dataclasses.Cast
import com.example.myapplication.dataclasses.Crew
import com.example.myapplication.dataclasses.Results
import com.example.myapplication.dataclasses.ReviewResult
import com.example.myapplication.dataclasses.SimilarMovieResults
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment:Fragment() {

    lateinit var binding: FragmentMovieDeatilBinding
    private var movieResult: Results? = null
    var movieId: Int? = null
    val viewModel: MoviesViewModel by viewModel()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieDeatilBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieDetailList = MovieDetailFragmentArgs.fromBundle(requireArguments()).movie
        movieResult = movieDetailList
        movieId = movieResult?.id

        movieId?.let{
            //credits data
            viewModel.creditsByMovieId(it)
            // reviews data
            viewModel.reviewByMovieId(it)
            //similar movies data
            viewModel.similarMoviesByMovieId(it)
        }

        navController = findNavController()

        val urlForPoster = "${MainActivity.BACKDROP_BASE_URL}${movieResult?.backdropPath}"
        Glide.with(this)
            .load(urlForPoster)
            .centerCrop()
            .into(binding.backdropPoster)
        binding.mdTitle.text = movieResult?.originalTitle
        binding.rating.text = "Ratings: ${movieResult?.voteAverage.toString()}"
        binding.synopsisTextArea.text = movieResult?.overview

        //cast
        val cast = ArrayList<Cast>()
        viewModel.cast.observe(requireActivity()){
            cast.clear()
            cast.addAll(it)
            val castRecyclerView = binding.castRecyclerView
            castRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            val castAdapter = context?.let { it1 ->
                CastRecyclerViewAdapter(cast,
                    it1,navController)
            }
            castRecyclerView.adapter = castAdapter
        }
        //crew
        val crew = ArrayList<Crew>()
        viewModel.crew.observe(requireActivity()){crewList->
            crew.clear()
            crewList.forEach {
                if(it.job!! == "Director" ||
                    it.job!! == "Producer" ||
                    it.job!! == "Writer"){
                    crew.add(it)
                }
            }
            val crewRecyclerView = binding.crewRecyclerView
            crewRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            val crewAdapter = context?.let { CrewRecyclerViewAdapter(crew, it,navController) }
            crewRecyclerView.adapter = crewAdapter
        }

        val reviewsResult= ArrayList<ReviewResult>()
        viewModel.movieReviewResult.observe(requireActivity()){
            reviewsResult.clear()
            reviewsResult.addAll(it)
            val reviewsRV = binding.reviewsRecyclerView
            reviewsRV.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            val reviewsAdapter = ReviewsRecyclerViewAdapter(reviewsResult)
            reviewsRV.adapter = reviewsAdapter
        }

        val similarMovies = ArrayList<Results>()
        viewModel.similarMovies.observe(requireActivity()){
            similarMovies.clear()
            similarMovies.addAll(it)
            val similarMovieRecyclerView = binding.similarMoviesRecyclerview
            similarMovieRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            val similarMovieAdapter = context?.let { it1 ->
                SimilarMoviesRecyclerViewAdapter(similarMovies,
                    it1,navController)
            }
            similarMovieRecyclerView.adapter = similarMovieAdapter
        }

        binding.favoriteButton.setOnCheckedChangeListener{ checkBox, isChecked ->

            context?.let{
                if (isChecked) {
                    checkBox.buttonDrawable =
                        ContextCompat.getDrawable(it, R.drawable.filled_favorite_48px)
                    val addFavourite = AddFavourite("movie", movieId, true)
                    viewModel.addFavoriteMovie(addFavourite)
                } else {
                    checkBox.buttonDrawable =
                        ContextCompat.getDrawable(it, R.drawable.favorite_48px)
                    val addFavourite = AddFavourite("movie", movieId, false)
                    viewModel.addFavoriteMovie(addFavourite)
                }
            }

        }
        viewModel.addFavStatus.observe(requireActivity()){
            if(it!=null){ viewModel.getFavMovie()}
        }

    }

}