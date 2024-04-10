package com.example.basicmovieapp.domain.repositories

import android.util.Log
import com.example.basicmovieapp.data.DataClient
import com.example.basicmovieapp.domain.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val client: DataClient,
    private val coroutineScope: CoroutineScope
) : MovieRepository {

    private val _movies = MutableStateFlow<List<Movie>>(listOf())
    override val movies = _movies.asStateFlow()

    override fun getMovieForId(id: Int): Movie? {
        return movies.value.firstOrNull { it.id == id }
    }

    override fun toggleMovieFavorite(id: Int) {
        val values = _movies.value.map { it.copy() }
        values.firstOrNull { it.id == id }?.let {
            it.isFavourite = !it.isFavourite
        }
        _movies.update { values }
    }

    init {
        fetchInitialMovies()
    }

    private fun fetchInitialMovies() {
        if (_movies.value.isEmpty())
            fetchMovies()
    }

    private fun fetchMovies() {
        coroutineScope.launch {
            client.getMovies().zip(client.getStaffPicks()) { movieListReponse, staffPicksResponse ->
                movieListReponse.map { movieResponse ->
                    movieResponse.isStaffPick = staffPicksResponse.firstOrNull { it.id == movieResponse.id } != null
                    movieResponse
                }
            }.catch {
                Log.i("Repository Error: ", it.message.toString())
            }.collect { movies ->
                _movies.update { movies }
            }
        }
    }
}