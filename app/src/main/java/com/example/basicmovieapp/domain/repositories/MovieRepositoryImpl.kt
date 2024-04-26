package com.example.basicmovieapp.domain.repositories

import com.example.basicmovieapp.data.DataClient
import com.example.basicmovieapp.domain.models.Movie
import com.example.basicmovieapp.domain.util.ErrorUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
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
            movies.map { it.firstOrNull { movie -> movie.id == id } }

        override fun toggleMovieFavorite(id: Int) {
            _movies.update { movies ->
                movies.map { if (it.id == id) it.copy(isFavourite = it.isFavourite.not()) else it }
            }
        }

        init {
            fetchMovies()
        }

        private fun fetchMovies() {
            coroutineScope.launch {
                client.getMovies().zip(client.getStaffPickedMovies()) { movieListReponse, staffPicksResponse ->
                    movieListReponse.map { movieResponse ->
                        movieResponse.copy(isStaffPick = staffPicksResponse.firstOrNull { it.id == movieResponse.id } != null)
                    }
                }
                    .catch { throwable ->
                        _error.update { ErrorUtil.getParsedError(throwable) }
                    }
                    .collect { movies ->
                        _movies.update { movies }
                    }
            }
        }
    }
