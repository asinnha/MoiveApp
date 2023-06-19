package com.example.myapplication.RecyclerViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ReviewCardModalBinding
import com.example.myapplication.dataclasses.ReviewResult

class ReviewsRecyclerViewAdapter(val reviewResults: ArrayList<ReviewResult>) :RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ReviewCardModalBinding):RecyclerView.ViewHolder(binding.root) {
        val authorName = binding.reviewsAuthor
        val review = binding.reviewsTextArea
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(ReviewCardModalBinding.inflate(LayoutInflater.from(parent.context),
        parent,false))
    }

    override fun getItemCount(): Int {
        return reviewResults.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.authorName.text = reviewResults[position].author
        holder.review.text = reviewResults[position].content
    }

}