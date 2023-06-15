package com.example.myapplication.RecyclerViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CreditsActorsCardModalBinding

class CreditsRecyclerViewAdapter (): RecyclerView.Adapter<CreditsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(private val binding: CreditsActorsCardModalBinding): RecyclerView.ViewHolder(binding.root) {
        val creditsImageView = binding.creditImageView
        val creditName = binding.creditsTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CreditsActorsCardModalBinding.inflate(LayoutInflater.from(parent.context),
            parent,false))
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.creditName.text = "Akshat Sinha"
    }
}