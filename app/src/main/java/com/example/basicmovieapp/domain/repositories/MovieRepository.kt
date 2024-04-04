package com.example.basicmovieapp.domain.repositories

import com.example.basicmovieapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MovieRepository {
    val movies: StateFlow<List<Movie>>

    val staffPickedMovies: List<Movie>

    val favoriteMovies: List<Movie>
    fun getMovieForId(id: Int): Movie?
    fun toggleMovieFavorite(id: Int)
}