package com.example.basicmovieapp.domain.repositories

import com.example.basicmovieapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MovieRepository {
    val movies: StateFlow<List<Movie>>

    val error: StateFlow<String>

    fun getMovieForId(id: Int): Flow<Movie?>

    fun toggleMovieFavorite(id: Int)
}
