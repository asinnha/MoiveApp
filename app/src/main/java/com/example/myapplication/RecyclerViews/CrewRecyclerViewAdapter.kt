package com.example.myapplication.RecyclerViews

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.MainActivity.Companion.IMAGE_BASE_URL
import com.example.myapplication.databinding.CreditsActorsCardModalBinding
import com.example.myapplication.dataclasses.Crew

class CrewRecyclerViewAdapter( private val crewList: ArrayList<Crew>, val context: Context):
    RecyclerView.Adapter<CrewRecyclerViewAdapter.ViewHolder>() {

    val filteredCrewList = ArrayList<Crew>()

    class ViewHolder(private val binding: CreditsActorsCardModalBinding): RecyclerView.ViewHolder(binding.root) {
        val creditsImageView = binding.creditImageView
        val creditName = binding.creditsTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CreditsActorsCardModalBinding.inflate(
                LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun getItemCount(): Int {

        return crewList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.creditName.text = crewList[position].name + "\n" + crewList[position].job
        val uri = "${IMAGE_BASE_URL}${crewList[position].profilePath}"
        Glide.with(context)
            .load(uri)
            .centerCrop()
            .into(holder.creditsImageView)
    }
}