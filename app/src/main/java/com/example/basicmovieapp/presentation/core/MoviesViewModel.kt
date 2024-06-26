package com.example.basicmovieapp.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicmovieapp.domain.models.Movie
import com.example.basicmovieapp.domain.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel
    @Inject
    constructor(
        private val repository: MovieRepository,
    ) : ViewModel() {
        val favoriteMovies: Flow<List<Movie>> =
            repository.movies.map { movies -> movies.filter { it.isFavourite } }

        val staffPickedMovies: Flow<List<Movie>> =
            repository.movies.map { movies -> movies.filter { it.isStaffPick } }

        private val querySearch = MutableStateFlow("")
        val searchMovies: StateFlow<List<Movie>> =
            repository.movies.combine(querySearch) { movies, query ->
                if (query.isEmpty()) {
                    movies
                } else {
                    movies.filter { it.title.lowercase().contains(query.lowercase()) }
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = listOf(),
            )

        val onError: Flow<String> =
            repository.error.map { it }

        fun getMovieForId(id: Int) = repository.getMovieForId(id)

        fun onFilterMovies(query: String) {
            querySearch.update { query }
        }

        fun toggleFavorite(id: Int) {
            repository.toggleMovieFavorite(id)
        }
    }
