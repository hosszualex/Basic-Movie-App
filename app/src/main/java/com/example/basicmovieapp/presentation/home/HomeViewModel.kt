package com.example.basicmovieapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicmovieapp.domain.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    init {
        observerMovies()
    }

    private fun observerMovies() {
        viewModelScope.launch {
            repository.movies
                .collect {
                    val asd = it
                }
        }
    }

    fun toggleFavorite(id: Int) {
       repository.toggleMovieFavorite(id)
    }
}