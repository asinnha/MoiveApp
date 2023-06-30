package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.dataclasses.Results
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    val viewModel: MoviesViewModel by viewModel()
    var nowPlayingMovieList =  ArrayList<Results>()
    var upcomingMoviesList =  ArrayList<Results>()
    var favoriteMoviesList =  ArrayList<Results>()

    lateinit var recyclerViewAdapter:RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        context?.let{ context -> //recycler view init
            val recyclerView = binding.recyclerView
            recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
            recyclerViewAdapter =
                RecyclerViewAdapter(nowPlayingMovieList, context, navController)
            recyclerView.adapter = recyclerViewAdapter

            val upcomingMoviesRecyclerView = binding.upcomingRecyclerView
            upcomingMoviesRecyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
            val upcomingRecyclerViewAdapter =
                RecyclerViewAdapter(upcomingMoviesList,context, navController)
            upcomingMoviesRecyclerView.adapter = upcomingRecyclerViewAdapter

            val favoriteMovieRecyclerView = binding.favoriteMoviesRecyclerView
            favoriteMovieRecyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL, false
            )
            val favoriteMovieRecyclerViewAdapter =
                RecyclerViewAdapter(favoriteMoviesList, context, navController)
            favoriteMovieRecyclerView.adapter = favoriteMovieRecyclerViewAdapter

            viewModel.nowPlayingList.observe(requireActivity()){
                if(it == null){
                    Toast.makeText(context,"loading", Toast.LENGTH_LONG).show()
                }else{
                    nowPlayingMovieList.clear()
                    nowPlayingMovieList.addAll(it)
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }
            viewModel.upcomingMovies.observe(requireActivity()){
                upcomingMoviesList.clear()
                upcomingMoviesList.addAll(it)
                upcomingRecyclerViewAdapter.notifyDataSetChanged()
            }

            viewModel.getFavMovieList.observe(requireActivity()){
                favoriteMoviesList.clear()
                favoriteMoviesList.addAll(it)
                favoriteMovieRecyclerViewAdapter.notifyDataSetChanged()
            }
        }

        binding.sortingBtnNowPlaying.setOnClickListener {
            inflateMenu(context,it)
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun inflateMenu(mainFragment: Context?, view: View) {
        val popupMenu = mainFragment?.let { PopupMenu(it,view) }
        val menuInflater = popupMenu?.menuInflater
        menuInflater?.inflate(R.menu.menu_sorting,popupMenu.menu)
        popupMenu?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.release_date_asc -> {
                    nowPlayingMovieList.clear()
                    viewModel.orderOldest()
                    viewModel.sortingNowPlaying.observe(requireActivity()){results->
                        nowPlayingMovieList.addAll(results)
                        recyclerViewAdapter.notifyDataSetChanged()
                    }

                    true
                }

                R.id.release_date_desc -> {
                    nowPlayingMovieList.clear()
                    viewModel.orderLatest()
                    viewModel.sortingNowPlaying.observe(requireActivity()){results->
                        nowPlayingMovieList.addAll(results)
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                    true
                }

                else -> false
            }
        }
        popupMenu?.show()
    }

}