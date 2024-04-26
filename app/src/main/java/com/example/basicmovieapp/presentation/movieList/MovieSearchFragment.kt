package com.example.basicmovieapp.presentation.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.basicmovieapp.R
import com.example.basicmovieapp.databinding.FragmentMovieSearchBinding
import com.example.basicmovieapp.presentation.core.ErrorDialogFragment
import com.example.basicmovieapp.presentation.core.IOnMovieClickListener
import com.example.basicmovieapp.presentation.core.MovieListAdapter
import com.example.basicmovieapp.presentation.core.MoviesViewModel
import kotlinx.coroutines.launch

class MovieSearchFragment : Fragment(), IOnMovieClickListener {
    private val viewModel: MoviesViewModel by activityViewModels()
    private var _binding: FragmentMovieSearchBinding? = null
    private val binding: FragmentMovieSearchBinding
        get() = _binding!!

    private lateinit var moviesAdapter: MovieListAdapter
    private val movieSearchQueryListener =
        object : SearchView.OnQueryTextListener {
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
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMovieSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
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
        lifecycleScope.launch {
            viewModel.onError.collect {
                if (it.isEmpty())
                    return@collect
                val dialog = ErrorDialogFragment.newInstance(it)
                dialog.show(childFragmentManager, "error_dialog")
            }
        }
    }

    override fun onMovieClicked(id: Int) {
        val data = bundleOf(Pair("movieId", id))
        findNavController().navigate(R.id.navigateToMovieDetails, data)
    }

    override fun onToggleFavorites(id: Int) {
        viewModel.toggleFavorite(id)
    }
}
