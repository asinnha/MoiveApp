package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.MainActivity.Companion.IMAGE_BASE_URL
import com.example.myapplication.databinding.ModalMovieCardMainPageBinding
import com.example.myapplication.dataclasses.Results

class RecyclerViewAdapter(val movieDetailsList:ArrayList<Results>,private val context: Context,val navController: NavController)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ModalMovieCardMainPageBinding)
        :RecyclerView.ViewHolder(binding.root) {

        val title = binding.titleMainPage
        val poster = binding.posterImageView
        val releaseText = binding.releaseTxt

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ModalMovieCardMainPageBinding.inflate(LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun getItemCount(): Int {
        return movieDetailsList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = movieDetailsList[position].title
        holder.title.apply {
            this.isSelected = true
            this.ellipsize = TextUtils.TruncateAt.MARQUEE
            this.isSingleLine = true
            this.marqueeRepeatLimit = -1
            this.isFocusable = true
            this.isFocusableInTouchMode = true
        }
        holder.releaseText.text = "Release Date: ${ movieDetailsList[position].releaseDate }"

        val urlForPoster = "$IMAGE_BASE_URL${movieDetailsList[position].posterPath}"
        Glide.with(context)
            .load(urlForPoster)
            .centerInside()
            .into(holder.poster)

        holder.poster.setOnClickListener {
            val movie = movieDetailsList[position]
            val action: NavDirections = MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(movie)
            navController.navigate(action)

        }
    }
}