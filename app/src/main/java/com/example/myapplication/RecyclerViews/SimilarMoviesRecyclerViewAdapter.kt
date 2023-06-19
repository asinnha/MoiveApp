package com.example.myapplication.RecyclerViews

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.MainActivity
import com.example.myapplication.databinding.ModalMovieCardMainPageBinding
import com.example.myapplication.dataclasses.SimilarMovieResults

class SimilarMoviesRecyclerViewAdapter(val similarMovieList: ArrayList<SimilarMovieResults>,val context: Context)
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
        holder.releaseDate.text = "Releasing on: ${ similarMovieList[position].releaseDate }"

        val urlForPoster = "${MainActivity.IMAGE_BASE_URL}${similarMovieList[position].posterPath}"
        Glide.with(context)
            .load(urlForPoster)
            .centerInside()
            .into(holder.poster)

        holder.bookBtn.visibility = View.GONE

//        holder.poster.setOnClickListener {
//            val intent = Intent(holder.poster.context, MovieDetails::class.java)
//            val results: SimilarMovieResults = similarMovieList[position]
//            intent.putExtra("Movie Detail List",results)
//            holder.poster.context.startActivity(intent)
//        }
    }
}