package com.example.basicmovieapp.presentation.core

interface IOnMovieClickListener {
    fun onMovieClicked(id: Int)

    fun onToggleFavorites(id: Int)
}
