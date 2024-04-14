package com.example.basicmovieapp.domain.repositories

import android.util.Log
import com.example.basicmovieapp.data.DataClient
import com.example.basicmovieapp.domain.models.Movie
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieRepositoryImpl
    @Inject
    constructor(
        private val client: DataClient,
        private val coroutineScope: CoroutineScope,
    ) : MovieRepository {
        private val _movies = MutableStateFlow<List<Movie>>(listOf())
        override val movies = _movies.asStateFlow()

        private val _error = MutableStateFlow<String>("")
        override val error: StateFlow<String> = _error.asStateFlow()

        override fun getMovieForId(id: Int) =
            flow {
                movies.collect { movies ->
                    emit(movies.firstOrNull { it.id == id }) // not sending updated
                }
            }

        override fun toggleMovieFavorite(id: Int) {
            val values = _movies.value.map { it.copy() }
            values.firstOrNull { it.id == id }?.let {
                it.isFavourite = !it.isFavourite
            }
            _movies.update { values }
        }

        init {
            fetchMovies()
        }

        private fun fetchMovies() {
            coroutineScope.launch {
                client.getMovies().zip(client.getStaffPicks()) { movieListReponse, staffPicksResponse ->
                    movieListReponse.map { movieResponse ->
                        movieResponse.apply {
                            isStaffPick =
                                staffPicksResponse.firstOrNull { it.id == movieResponse.id } != null
                        }
                    }
                }
                    .catch { throwable ->
                        Log.i("Repository Error: ", throwable.message.toString())
                        _error.update { getParsedError(throwable) }
                    }
                    .collect { movies ->
                        _movies.update { movies }
                    }
            }
        }

        private fun getParsedError(exception: Throwable): String {
            return when (exception) {
                is JsonSyntaxException -> "The JSON file(s) are in the wrong format."
                else -> "An unknown error has occured. Please "
            }
        }
    }
