package com.example.myapplication.RecyclerViews

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.MainActivity.Companion.IMAGE_BASE_URL
import com.example.myapplication.MovieDetailFragmentDirections
import com.example.myapplication.R
import com.example.myapplication.databinding.ModalMovieCardMainPageBinding
import com.example.myapplication.dataclasses.Results
import com.example.myapplication.dataclasses.SimilarMovieResults

class SimilarMoviesRecyclerViewAdapter(
    val similarMovieList: ArrayList<Results>,
    val context: Context,
    val navController: NavController
)
    : RecyclerView.Adapter<SimilarMoviesRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(private val binding:ModalMovieCardMainPageBinding): RecyclerView.ViewHolder(binding.root) {
        val poster = binding.posterImageView
        val title = binding.titleMainPage
        val releaseDate = binding.releaseTxt
        val bookBtn = binding.bookBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ModalMovieCardMainPageBinding.inflate(LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun getItemCount(): Int {
        return similarMovieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = similarMovieList[position].originalTitle
        holder.title.apply {
            this.isSelected = true
            this.ellipsize = TextUtils.TruncateAt.MARQUEE
            this.isSingleLine = true
            this.marqueeRepeatLimit = -1
            this.isFocusable = true
            this.isFocusableInTouchMode = true
        }
        holder.releaseDate.text = "Release Date: ${ similarMovieList[position].releaseDate }"

        val urlForPoster = "${IMAGE_BASE_URL}${similarMovieList[position].posterPath}"
        Glide.with(context)
            .load(urlForPoster)
            .centerInside()
            .into(holder.poster)

        holder.bookBtn.visibility = View.GONE

        holder.poster.setOnClickListener {
            val movie = similarMovieList[position]
            val action:NavDirections = MovieDetailFragmentDirections.actionMovieDetailsFragmentSelf(movie)
            navController.navigate(action)
        }
    }
}