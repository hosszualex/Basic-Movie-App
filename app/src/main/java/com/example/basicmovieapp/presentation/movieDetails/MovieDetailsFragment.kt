package com.example.basicmovieapp.presentation.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.basicmovieapp.databinding.FragmentMovieDetailsBinding
import com.example.basicmovieapp.domain.models.Movie
import com.example.basicmovieapp.domain.util.TextFormatterUtil
import com.example.basicmovieapp.domain.util.roundToHalf
import com.example.basicmovieapp.presentation.home.MoviesViewModel
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.coroutines.launch

class MovieDetailsFragment: Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding: FragmentMovieDetailsBinding
        get() = _binding!!
    private val viewModel: MoviesViewModel by activityViewModels()
    private var currentMovieId: Int = 0
    private val genreAdapter = MovieGenreAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("movieId")?.let { currentMovieId = it }
        binding.imageButtonBack.setOnClickListener { findNavController().popBackStack() }
        binding.recyclerViewGenres.layoutManager = FlexboxLayoutManager(this.requireContext())
        binding.recyclerViewGenres.adapter = genreAdapter
        connectViewModel()
    }

    private fun connectViewModel() {
        lifecycleScope.launch {
            viewModel.getMovieForId(currentMovieId).collect { movie ->
                movie?.let { unwrappedMovie ->
                    setupMovieUi(unwrappedMovie)
                }
            }
        }
    }

    private fun setupMovieUi(movie: Movie) {
        with (binding) {
            imageViewPoster.load(movie.posterUrl) {
                crossfade(true)
            }
            imageButtonFavoriteIcon.isSelected = movie.isFavourite
            imageButtonFavoriteIcon.setOnClickListener {
                viewModel.toggleFavorite(movie.id)
            }
            ratingBarMovie.rating = movie.rating.roundToHalf().toFloat()
            textViewMovieTitle.text = "${movie.title} (${TextFormatterUtil.getYearFromString(movie.releaseDate)})"
            textViewReleaseDate.text = movie.releaseDate
            textViewRuntime.text = "${movie.runtime/60}h ${movie.runtime%60}m"
            genreAdapter.submitGenres(movie.genres)

            textViewOverviewContent.text = movie.overview

            binding.layoutKeyFactBudget.textViewTitle.text = "Budget"
            binding.layoutKeyFactBudget.textViewFact.text = "$ ${TextFormatterUtil.formatMoneyAmount(movie.budget.toInt())}"

            binding.layoutKeyFactRevenue.textViewTitle.text = "Revenue"
            binding.layoutKeyFactRevenue.textViewFact.text = "$ ${TextFormatterUtil.formatMoneyAmount(movie.revenue.toInt())}"

            binding.layoutKeyFactLanguage.textViewTitle.text = "Original Language"
            binding.layoutKeyFactLanguage.textViewFact.text = movie.language

            binding.layoutKeyFactRating.textViewTitle.text = "Rating"
            binding.layoutKeyFactRating.textViewFact.text = String.format("%.2f", movie.rating) + " / 5"
        }
    }
}