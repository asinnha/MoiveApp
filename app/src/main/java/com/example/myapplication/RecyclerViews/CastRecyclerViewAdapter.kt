package com.example.myapplication.RecyclerViews

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.MainActivity.Companion.IMAGE_BASE_URL
import com.example.myapplication.databinding.CreditsActorsCardModalBinding
import com.example.myapplication.dataclasses.Cast

class CastRecyclerViewAdapter (val castList: ArrayList<Cast>,val context: Context): RecyclerView.Adapter<CastRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(private val binding: CreditsActorsCardModalBinding): RecyclerView.ViewHolder(binding.root) {
        val creditsImageView = binding.creditImageView
        val creditName = binding.creditsTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CreditsActorsCardModalBinding.inflate(LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.creditName.text = castList[position].name +"\n"+castList[position].character
        val uri = "${IMAGE_BASE_URL}${castList[position].profilePath}"
        Glide.with(context)
            .load(uri)
            .centerCrop()
            .into(holder.creditsImageView)
    }
}