package com.example.basicmovieapp.presentation.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.basicmovieapp.R
import com.example.basicmovieapp.databinding.FragmentMovieSearchBinding
import com.example.basicmovieapp.presentation.IOnMovieClickListener
import com.example.basicmovieapp.presentation.home.MoviesViewModel
import com.example.basicmovieapp.presentation.MovieListAdapter
import kotlinx.coroutines.launch

class MovieSearchFragment: Fragment(), IOnMovieClickListener {

    private val viewModel: MoviesViewModel by activityViewModels()
    private var _binding: FragmentMovieSearchBinding? = null
    private val binding: FragmentMovieSearchBinding
        get() = _binding!!

    private lateinit var moviesAdapter: MovieListAdapter
    private val movieSearchQueryListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let { viewModel.onFilterMovies(newText) }
            return true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter = MovieListAdapter(this)
        binding.recyclerViewMovies.adapter = moviesAdapter
        binding.searchViewMovies.setOnQueryTextListener(movieSearchQueryListener)
        binding.imageButtonBack.setOnClickListener { findNavController().popBackStack() }
        connectViewModel()
    }

    private fun connectViewModel() {
        lifecycleScope.launch {
            viewModel.searchMovies.collect {
                moviesAdapter.refreshData(it)
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