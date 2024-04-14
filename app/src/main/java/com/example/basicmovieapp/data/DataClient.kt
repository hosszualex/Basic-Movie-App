package com.example.basicmovieapp.data

import com.example.basicmovieapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface DataClient {
    fun getMovies(): Flow<List<Movie>>

    fun getStaffPickedMovies(): Flow<List<Movie>>
}
