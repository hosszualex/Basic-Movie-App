package com.example.basicmovieapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.basicmovieapp.R
import com.example.basicmovieapp.databinding.FragmentHomeBinding
import com.example.basicmovieapp.presentation.MovieListAdapter
import com.example.basicmovieapp.presentation.IOnMovieClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment: Fragment(), IOnMovieClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val viewModel: MoviesViewModel by activityViewModels()
    private lateinit var favoritesAdapter: MovieFavoritesAdapter
    private lateinit var horizontalMoviesAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesAdapter = MovieFavoritesAdapter(this)
        horizontalMoviesAdapter = MovieListAdapter(this)
        binding.recyclerViewFavorites.adapter = favoritesAdapter
        binding.recyclerViewStaffPicks.adapter = horizontalMoviesAdapter
        binding.imageButtonSearch.setOnClickListener {
            findNavController().navigate(R.id.navigateToSearch)
        }
        connectViewModel()
    }

    private fun connectViewModel() {
        lifecycleScope.launch {
            viewModel.favoriteMovies.collect {
                favoritesAdapter.refreshData(it)
            }
        }
        lifecycleScope.launch {
            viewModel.staffPickedMovies.collect {
                horizontalMoviesAdapter.refreshData(it)
            }
        }
    }

    override fun onMovieClicked(id: Int) {
        findNavController().navigate(R.id.navigateToMovieDetails)
    }

    override fun onToggleFavorites(id: Int) {
        viewModel.toggleFavorite(id)
    }
}