package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityMovieDetailsPageBinding
import com.example.myapplication.dataclasses.Results
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetails : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailsPageBinding
    private var movieResult: Results? = null
    val viewModel: MoviesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moviePosition = intent.getIntExtra("Movie Position", Int.MAX_VALUE)

        viewModel.nowPlayingList.observe(this){
            movieResult = it[moviePosition]
            val urlForPoster = "${MainActivity.IMAGE_BASE_URL}${movieResult?.backdropPath}"
            Glide.with(this)
                .load(urlForPoster)
                .centerInside()
                .into(binding.backdropPoster)
            binding.mdTitle.text = movieResult?.originalTitle
            binding.rating.text = movieResult?.popularity.toString()
            binding.synopsisTextArea.text = movieResult?.overview

        }

    }
}